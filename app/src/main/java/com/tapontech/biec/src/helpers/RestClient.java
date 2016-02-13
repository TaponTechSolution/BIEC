package com.tapontech.biec.src.helpers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.os.StrictMode;

public class RestClient {
	public enum RequestMethod
	{
		GET,
		POST
	}
	 private ArrayList <NameValuePair> params;
	    private ArrayList <NameValuePair> headers;

	    private String url;

	    private int responseCode;
	    private String message;

	    private String response;

	    public String getResponse() {
	        return response;
	    }

	    public String getErrorMessage() {
	        return message;
	    }

	    public int getResponseCode() {
	        return responseCode;
	    }

	    public RestClient(String url)
	    {
	        this.url = url;
	        params = new ArrayList<NameValuePair>();
	        headers = new ArrayList<NameValuePair>();
	    }

	    public void AddParam(String name, String value)
	    {
	        params.add(new BasicNameValuePair(name, value));
	    }

	    public void AddHeader(String name, String value)
	    {
	        headers.add(new BasicNameValuePair(name, value));
	    }

	    public void Execute(RequestMethod method) throws Exception
	    {
	        switch(method) {    
	            case GET:
	            {
	                //add parameters
	                String combinedParams = "";
	                if(!params.isEmpty()){
	                    combinedParams += "?"; // TODO for GWT: replace / to ?.  
	                    for(NameValuePair p : params)
	                    {
	                    	String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(),"UTF-8");	// TODO pre append p.getName() + "=" +                         
	                    	if(combinedParams.length() > 1)
	                        {
	                            combinedParams  +=  "&" + paramString;  // TODO for GWT: replace / to &. and also pre append combined params to paramString
	                        }
	                        else
	                        {   
	                            combinedParams += paramString;
	                        }
	                    }
	                }

	                HttpGet request = new HttpGet(url + combinedParams);

	                //add headers
	                for(NameValuePair h : headers)
	                {
	                    request.addHeader(h.getName(), h.getValue());
	                }

	                executeRequest(request, url);
	                break;
	            }
	            case POST:
	            {
	                HttpPost request = new HttpPost(url);

	                //add headers
	                for(NameValuePair h : headers)
	                {
	                    request.addHeader(h.getName(), h.getValue());
	                }

	                if(!params.isEmpty()){
	                    request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
	                }

	                executeRequest(request, url);
	                break;
	            }
	        }
	    }

	    private void executeRequest(HttpUriRequest request, String url)
	    {
	        HttpResponse httpResponse = null;
			HttpParams myParams = new BasicHttpParams();	    	  
			HttpConnectionParams.setConnectionTimeout(myParams, Consts.CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(myParams, Consts.CONNECTION_TIMEOUT);
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
	        HttpClient client = new DefaultHttpClient(myParams);

	        try {
	            httpResponse = client.execute(request);
	            responseCode = httpResponse.getStatusLine().getStatusCode();
	            message = httpResponse.getStatusLine().getReasonPhrase();
	            
	            HttpEntity entity = httpResponse.getEntity();

	            if (entity != null) {

	                InputStream instream = entity.getContent();
	                response = convertStreamToString(instream);

	                // Closing the input stream will trigger connection release
	                instream.close();
	            }

	        } catch (ClientProtocolException e)  {
	            client.getConnectionManager().shutdown();
	            e.printStackTrace();
	        } catch (IOException e) {
	            client.getConnectionManager().shutdown();
	            e.printStackTrace();
	        }
	        catch (Exception e) {
	            client.getConnectionManager().shutdown();
	            e.printStackTrace();
	        }
	        finally{
	            responseCode = httpResponse.getStatusLine().getStatusCode();
	            message = httpResponse.getStatusLine().getReasonPhrase();	        	
	        }
	    }

	    private static String convertStreamToString(InputStream is) {

	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        StringBuilder sb = new StringBuilder();

	        String line = null;
	        try {
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                is.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return sb.toString();
	    }
	    
	    public static InputStream OpenHttpConnection(String strURL) throws IOException{
	    	 InputStream inputStream = null;
	    	 URL url = new URL(strURL);
	    	 URLConnection conn = url.openConnection();

	    	 try{
				HttpURLConnection httpConn = (HttpURLConnection)conn;
				httpConn.setRequestMethod("GET");
				httpConn.connect();
				
				if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					inputStream = httpConn.getInputStream();
				}
	    	 }
	    	 catch (Exception ex)
	    	 {
	    	 }
	    	 return inputStream;
	    }	    
}
