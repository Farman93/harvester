package ru.umeta.harvesting.geonetwork;


public class HttpRequest implements IRequest {
     private String Url;
     private String Arguments;
     private String requestName;
     
     public HttpRequest(String url, String arguments, String requestName)
     {
    	 Url = url;
    	 Arguments = arguments;
    	 this.requestName = requestName;
     }
     public String getRequestName() {
		return requestName;
	}
	public HttpRequest(String url, String arguments)
     {
    	 Url = url;
    	 Arguments = arguments;
     }
     
     public String getUrl()
     {
    	return Url; 
     }
     
     /*public Type getType()
     {
    	 return Type.HttpRequest;
     }*/
     
     public String getArguments()
     {
    	 return Arguments;
     }
     
}
