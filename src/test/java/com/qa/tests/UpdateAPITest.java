package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Employees;

public class UpdateAPITest extends TestBase{
	
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closebaleHttpResponse;
	
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException{
		testBase = new TestBase();
		serviceUrl = prop.getProperty("PutURL");
		apiUrl = prop.getProperty("putserviceURL");
		//http://dummy.restapiexample.com/api/v1/update/1
		
		url = serviceUrl + apiUrl;
		
	}
	
	
	@Test
	public void updateAPITest() throws JsonGenerationException, JsonMappingException, IOException {
		
		restClient = new RestClient();
		
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		
		//jackson API:
		ObjectMapper mapper = new ObjectMapper();
		Employees employees = new Employees("Umair3"); //expected employee object
		
		mapper.writeValue(new File("C:\\Users\\anisu\\eclipse-workspace\\restapi\\src\\main\\java\\com\\qa\\data\\employees.json"), employees);
		
		//java object to json in String:
		String employeesJsonString = mapper.writeValueAsString(employees);
		System.out.println(employeesJsonString);
		
		//call the API
		closebaleHttpResponse = restClient.put(url, employeesJsonString, headerMap);
		
		//validate response from API:
		
		//1. status code:
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_200);
		
		//2. JsonString:
		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
		
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("The response from API is:"+ responseJson);
		
		//json to java object:
		Employees employeesResObj = mapper.readValue(responseString, Employees.class); //actual employees object
		System.out.println(employeesResObj);
				
		Assert.assertTrue(employees.getName().equals(employeesResObj.getName()));
				
		
				
		System.out.println(employeesResObj.getName());
		
	}
	

}
