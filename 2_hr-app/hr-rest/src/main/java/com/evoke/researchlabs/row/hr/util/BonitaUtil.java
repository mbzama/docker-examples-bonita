package com.evoke.researchlabs.row.hr.util;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.evoke.researchlabs.row.hr.domain.User;

/**
 * 
 * @author Zama
 *
 */
public class BonitaUtil {
	private static Logger logger = Logger.getLogger(BonitaUtil.class);
	public static final String SERVER_URI = "http://bonita:8080/bonita";
	public static final String BPM_USER = "walter.bates"; 
	public static final String BPM_PWD = "bpm"; 

	public static final String LOGIN_URL = SERVER_URI+"/loginservice?username=walter.bates&redirect=false&password=bpm";
	public static final String CREATE_CASE = SERVER_URI+"/API/bpm/process/";
	public static final String GET_PROCESS = SERVER_URI+"/API/bpm/process?n=SimpleProcess";


	public static void main(String[] args) throws ClientProtocolException, IOException {
		createCase(new User("code_"+new Date().getTime(), "hyd", "c7@gmail.com"));
	}

	public static String getValue(String input) {
		return input.substring(input.indexOf("=")+1, input.indexOf(";"));
	}
	
	public static HttpHeaders login() {
		RestTemplate restTemplate = new RestTemplate();
		logger.info(LOGIN_URL);
		HttpHeaders loginHeaders = new HttpHeaders();
		loginHeaders.add("Content-Type", "application/x-www-form-urlencoded");
		loginHeaders.add("username", BPM_USER);
		loginHeaders.add("password", BPM_PWD);
		loginHeaders.add("redirect", "false");

		ResponseEntity<String> loginResponse = restTemplate.exchange(LOGIN_URL, HttpMethod.POST, new HttpEntity(null, loginHeaders), String.class);
		
		logger.info("loginResponse: "+loginResponse.toString());

		List<String> items = loginResponse.getHeaders().get("Set-Cookie");

		String jsessionId = getValue(items.get(1));
		String bonitaAPIToken = getValue(items.get(2));

		logger.info(jsessionId+"\n"+bonitaAPIToken);

		return getHttpHeaders(jsessionId, bonitaAPIToken);
	}

	private static String getProcessId() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders loginHeaders = login();
		String processId = "";

		ResponseEntity<String> getProcessRes = null;
		try {
			HttpEntity httpEntity = new HttpEntity(null, loginHeaders);
			logger.info("httpEntity: "+httpEntity);
			getProcessRes = restTemplate.exchange(GET_PROCESS, HttpMethod.GET, httpEntity, String.class);
			String responseString = getProcessRes.getBody();
			logger.info("responseString: "+responseString);
			JSONObject t = (JSONObject) new JSONArray(responseString).get(0);
			processId = t.getString("id");

			logger.info("getProcessRes: "+getProcessRes.getBody()+"\n");
		} catch (Exception e) {
			logger.error("getProcessId: ", e);
		} 

		return processId;
	}

	public static String createCase(User user){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders loginHeaders = login();
		
		String processId = getProcessId();
		logger.info(CREATE_CASE);
		String createRequest = "{\"registation_refInput\":{\"userName\": \""+user.getUsername()+"\",\"city\" : \""+user.getAddress()+"\",\"email\": \""+user.getEmail()+"\"}}";

		ResponseEntity<String> createCaseRes = null;
		try {
			HttpEntity httpEntity = new HttpEntity(createRequest, loginHeaders);
			logger.info("httpEntity: "+httpEntity);
			createCaseRes = restTemplate.exchange(CREATE_CASE+processId+"/instantiation", HttpMethod.POST, httpEntity, String.class);

			logger.info("createCaseRes: "+createCaseRes.getBody()+"\n");
		} catch (Exception e) {
			logger.error("createCase: ", e);
		} 
		return createCaseRes.getBody();
	}

	private static HttpHeaders getHttpHeaders(String jsessionId, String bonitaAPIToken) {
		HttpHeaders loginHeaders = new HttpHeaders();
		loginHeaders.add("X-Bonita-API-Token", bonitaAPIToken);
		loginHeaders.add("JSESSIONID", jsessionId);
		loginHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");
		loginHeaders.set(HttpHeaders.COOKIE, "X-Bonita-API-Token="+bonitaAPIToken+"; JSESSIONID="+jsessionId);
		loginHeaders.set(HttpHeaders.HOST, "localhost");
		return loginHeaders;
	}
}
