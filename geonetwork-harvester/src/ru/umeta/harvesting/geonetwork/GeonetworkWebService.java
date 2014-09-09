package ru.umeta.harvesting.geonetwork;

import common.HarvesterInfo;
import jeeves.utils.XmlRequest;
import org.fao.geonet.constants.Geonet;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;

public class GeonetworkWebService {
	public HarvesterFileMessage getFile(String endURL,String remoteUuid){
		int code = 1;
		String message = "successs";
		DataHandler fileDataHandler = null;
		PrintStream pr = null;
		try {
			FileOutputStream fout;
			fout = new FileOutputStream ("web_service_testing.txt");
		    pr = new PrintStream(fout);
		    pr.println("started");
		    pr.println("remoteUuid: " + remoteUuid);
		    
			
			
			HarvesterInfo hinfo = new HarvesterInfo();
			hinfo.setEndURL(endURL);
			URL eUrl = new URL(hinfo.endURL);
			XmlRequest fReq = new XmlRequest(eUrl.getHost(), (eUrl.getPort()==-1 ? 80 : eUrl.getPort()));
			fReq.addParam("uuid",   remoteUuid);
			fReq.addParam("format", "partial");
			fReq.setAddress("/geonetwork/srv/en/"+ Geonet.Service.MEF_EXPORT);
			File mefFile = File.createTempFile("temp-", ".dat"/*, tempMefDir */);
			fReq.executeLarge(mefFile);
			pr.println("executed large file");
			FileDataSource dataSource = new FileDataSource(mefFile);
			fileDataHandler = new DataHandler(dataSource);
			pr.println("ended");
		} catch (Exception e) {
			code = 0;
			message = e.getClass().getName();
		}
		finally {
			pr.close();
		}
		return new HarvesterFileMessage(code,message,fileDataHandler);
	}
}