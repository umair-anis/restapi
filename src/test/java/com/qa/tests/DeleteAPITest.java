package com.qa.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class DeleteAPITest extends TestBase{
	
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	String getAllEmployees;
	String getAllEmployeesURL;
	RestClient restClient;
	String id;
	CloseableHttpResponse closeableHttpResponse;
	
	
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException{
		testBase = new TestBase();
		serviceUrl = prop.getProperty("DeleteURL");
		apiUrl = prop.getProperty("deleteServiceURL");
		getAllEmployees = prop.getProperty("getallserviceURL");
	
		getAllEmployeesURL = serviceUrl + getAllEmployees; //http://dummy.restapiexample.com/api/v1/employees
		
		url = serviceUrl + apiUrl + id; //http://dummy.restapiexample.com/api/v1/delete/<id>
		
	}
	
	@Test(priority=1)
	public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException {
		
		restClient = new RestClient();
		closeableHttpResponse = restClient.get(getAllEmployeesURL);
		
		
		//a. status code:
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code ----->"+ statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");
		
		// JSON string
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		
		JSONArray jsonArray=new JSONArray(responseString);
		JSONObject identityObject = jsonArray.getJSONObject(1);
		id = identityObject.getString("id");
		System.out.println("Value of id----->"+id);
		//Assert.assertEquals(Integer.parseInt(id), 1);
		
		// All Headers
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for(Header header : headerArray) {
			allHeaders.put(header.getName(), header.getValue());
				
		}
		
		System.out.println("Headers Array--->"+allHeaders);
		
		
	}
	
	@Test(priority=2)
	public void deleteAPITestWithHeaders() throws ClientProtocolException, IOException {
		
		restClient = new RestClient();
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
//		headerMap.put("username", "test@amazon.com");
//		headerMap.put("password", "test213");
//		headerMap.put("Auth Token", "12345");
		
		closeableHttpResponse = restClient.delete(url, headerMap);	
		
		
		//a. status code:
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code ----->"+ statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");
		
		// JSON string
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API ----->"+ responseJson);
		
		// single value assertion
		// text:
		String text = TestUtil.getValueByJPath(responseJson, "/success");
		System.out.println("Value of success----->"+text);
		Assert.assertEquals(text, "{\"text\":\"successfully! deleted Records\"}");
		
		
		// All Headers
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for(Header header : headerArray) {
			allHeaders.put(header.getName(), header.getValue());				
		}
		
		System.out.println("Headers Array--->"+allHeaders);		
	}
}

