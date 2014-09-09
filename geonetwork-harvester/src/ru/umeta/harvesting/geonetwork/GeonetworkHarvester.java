package ru.umeta.harvesting.geonetwork;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import jeeves.utils.XmlRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.fao.geonet.constants.Geonet;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import ru.umeta.harvesting.base.IHarvester;
import ru.umeta.harvesting.base.Query;


public class GeonetworkHarvester implements IHarvester {

	

	public int harvest() throws Exception  
	{
		int code = 1;
		HarvesterInfo hinfo = new HarvesterInfo();
		hinfo.setEndURL("http://www.paikkatietohakemisto.fi/geonetwork/");
		hinfo.setStartURL("http://localhost:8040/harvesting/");
		hinfo.SEARCHER.keywords = "project";
		
		String message = "success";
		String xmlString = null;
		//идем по заданному URL и производим XML_SEARCH с заданным параметрами поиска
		try {
			URL eUrl = new URL(hinfo.endURL);
			XmlRequest eReq = new XmlRequest(eUrl.getHost(), (eUrl.getPort()==-1 ? 80 : eUrl.getPort()));
			eReq.setAddress("/geonetwork/srv/en/"+ Geonet.Service.XML_SEARCH);
			Element elResponse = eReq.execute(hinfo.SEARCHER.createRequest());
			//формируем тело ответа в виде строки
			Document document = new Document();
			document.setContent(elResponse);
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
	        xmlString = outputter.outputString(document);
		}
		catch (Exception e) {
			e.printStackTrace();
			return 2;
		}
		return send(hinfo, code, message, xmlString);
	}
	
	public int harvest(Query query )
	{
		final HarvesterInfo hinfo = new HarvesterInfo(query);
        int code = 1;
		String message = "success";
		String xmlString = null;
		try {
			URL eUrl = new URL(hinfo.endURL);
			XmlRequest eReq = new XmlRequest(eUrl.getHost(), (eUrl.getPort()==-1 ? 80 : eUrl.getPort()));
			eReq.setAddress("/geonetwork/srv/en/"+ Geonet.Service.XML_SEARCH);
			
			Element response = eReq.execute(hinfo.SEARCHER.createRequest());
			//Этот response нужно пробросить на точку доступа(например на Геомету)
			Document document = new Document();
			document.setContent(response);
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
	        xmlString = outputter.outputString(document);
		} 
		catch (Exception e) {
			return 2;
		}
		
		return send(hinfo, code, message, xmlString);
		
	}
	
	private int send(HarvesterInfo hinfo, int code, String message, String data) {
		//Посылаем ответ на jsp-страницу
        HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(hinfo.startURL + "gnap.html");

		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		try {
			params.add(new BasicNameValuePair("query_id", hinfo.ID));
			params.add(new BasicNameValuePair("endURL", hinfo.endURL));
			params.add(new BasicNameValuePair("element", data));
			params.add(new BasicNameValuePair("code", String.valueOf(code)));
			params.add(new BasicNameValuePair("message", message));
		
			httppost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
			//Execute and get the response.
			HttpResponse response = httpclient.execute(httppost);
	
			if (response != null) {
			    InputStream instream = response.getEntity().getContent();
			    try {
			    	BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
			    } finally {
			        instream.close();
			    }
			}
		}
		catch (Exception e) {
			return 3;
		}
		return 1;
	}
	
	public static void main(String[] args) {
		try {
			GeonetworkHarvester harv = new GeonetworkHarvester();
			System.out.println(harv.harvest());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
