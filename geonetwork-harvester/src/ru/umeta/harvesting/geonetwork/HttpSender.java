package ru.umeta.harvesting.geonetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpSender implements ISender {

	
	public Response sendRequest(IRequest abstractRequest)
	{
		HttpRequest request = (HttpRequest) abstractRequest;
		
		String endpoint = request.getUrl();
		String requestParameters = request.getArguments();
	
		String result = null;
		if (endpoint.startsWith("http://")){
			// Send a GET request to the servlet
			try{
				// Construct data
      //  StringBuffer data = new StringBuffer();

       // Send data
        String urlStr = endpoint;
        if (requestParameters != null && requestParameters.length () > 0){
            urlStr += "?" + requestParameters;
        }

        URL url = new URL(urlStr);
        URLConnection conn = url.openConnection ();
        //conn.connect();
       

        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));


        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null){
            sb.append(line);
        }
        rd.close();
        result = sb.toString();
        //System.out.println("Result:" + result);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
  }
    
   //------------------DEBUG---
    //try {
   // result = readFileAsString("my_md.txt");
   // }
    //catch (IOException e){ }
    //--------------------

    Response resultDOM = new Response (result); 
//System.out.println("RESULT : " + result);
return resultDOM;
}


/*
private static String readFileAsString(String filePath)
throws java.io.IOException{
    StringBuffer fileData = new StringBuffer(1000);
    BufferedReader reader = new BufferedReader(
            new FileReader(filePath));
    char[] buf = new char[1024];
    int numRead=0;
    while((numRead=reader.read(buf)) != -1){
        fileData.append(buf, 0, numRead);
    }
    reader.close();
    return fileData.toString();
}*/

}









