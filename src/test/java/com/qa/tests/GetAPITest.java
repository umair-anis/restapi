package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase{
	
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	
	
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException{
		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		//https://reqres.in/api/users
		
		url = serviceUrl + apiUrl;
		
	}
	
	@Test(priority=1)
	public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException {
		
		restClient = new RestClient();
		closeableHttpResponse = restClient.get(url);
		
		
		//a. status code:
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code ----->"+ statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");
		
		// JSON string
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API ----->"+ responseJson);
		
		// single value assertion
		// per page:
		String perPage = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of per page----->"+perPage);
		Assert.assertEquals(Integer.parseInt(perPage), 6);
		
		// total page
		String total = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("total value----->"+total);
		Assert.assertEquals(Integer.parseInt(total), 12);
		
		//get the value from JSON ARRAY:
		String lastName = TestUtil.getValueByJPath(responseJson, "/data[1]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[1]/id");
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[1]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJson, "/data[1]/first_name");

		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);
		
		// All Headers
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for(Header header : headerArray) {
			allHeaders.put(header.getName(), header.getValue());
				
		}
		
		System.out.println("Headers Array--->"+allHeaders);
		
		
	}
	
	
	
	@Test(priority=2)
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException {
		
		restClient = new RestClient();
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
//		headerMap.put("username", "test@amazon.com");
//		headerMap.put("password", "test213");
//		headerMap.put("Auth Token", "12345");
		
		closeableHttpResponse = restClient.get(url, headerMap);	
		
		
		//a. status code:
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code ----->"+ statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");
		
		// JSON string
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API ----->"+ responseJson);
		
		// single value assertion
		// per page:
		String perPage = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of per page----->"+perPage);
		Assert.assertEquals(Integer.parseInt(perPage), 6);
		
		// total page
		String total = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("total value----->"+total);
		Assert.assertEquals(Integer.parseInt(total), 12);
		
		//get the value from JSON ARRAY:
		String lastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");

		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);
		
		// All Headers
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for(Header header : headerArray) {
			allHeaders.put(header.getName(), header.getValue());				
		}
		
		System.out.println("Headers Array--->"+allHeaders);		
	}
}

