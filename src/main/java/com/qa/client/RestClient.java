package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


public class RestClient {

	
	// 1. GET Method without Headers
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); //http get request
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget);  //hit the GET url
		
		return closeableHttpResponse;
	}
	
	// 1. GET Method with Headers
	public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); //http get request
		
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {	
			httpget.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget);  //hit the GET url
		
		return closeableHttpResponse;
	}
	
	// 1. POST Method with Headers
		public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
			
			// initialize http client
			CloseableHttpClient httpClient = HttpClients.createDefault();
			
			//http post request
			HttpPost httppost = new HttpPost(url);
			
			//setup payload
			httppost.setEntity(new StringEntity(entityString));
			
			//setup header info
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {	
				httppost.addHeader(entry.getKey(), entry.getValue());
			}
			
			//hit the post url
			CloseableHttpResponse closeableHttpResponse = httpClient.execute(httppost); 
			
			return closeableHttpResponse;
		}
		
		
		// 1. PUT Method with Headers
		public CloseableHttpResponse put(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
			
			// initialize http client
			CloseableHttpClient httpClient = HttpClients.createDefault();
			
			//http put request
			HttpPut httpput = new HttpPut(url);
			
			//setup payload
			httpput.setEntity(new StringEntity(entityString));
			
			//setup header info
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {	
				httpput.addHeader(entry.getKey(), entry.getValue());
			}
			
			//hit the put url
			CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpput);
			
			return closeableHttpResponse;
		}
		
		// 1. Delete Method with Headers
		public CloseableHttpResponse delete(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpDelete httpdelete = new HttpDelete(url); //http delete request
			
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {	
				httpdelete.addHeader(entry.getKey(), entry.getValue());
			}
			CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpdelete);  //hit the Delete url
			
			return closeableHttpResponse;
		}
}
