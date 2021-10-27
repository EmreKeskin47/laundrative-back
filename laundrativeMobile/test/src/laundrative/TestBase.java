package laundrative;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import laundrative.util.Util;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;

@Log4j2
public class TestBase {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String jwtToken;

	public TestRestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(TestRestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getBaseURL() {
		return "http://monster:" + port;
	}

	public String getTestResponse(String serviceURL, String body, HttpMethod type, boolean login) {
		HttpHeaders headers = new HttpHeaders();
		if (login) {
			headers.set("Authorization", "Bearer " + getToken());
		}
		return getTestResponse(serviceURL, body, type, headers);
	}

	public String getTestResponse(String serviceURL, String body, HttpMethod type, HttpHeaders headers) {
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		if (type == HttpMethod.GET && body != null) {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getBaseURL() + serviceURL);
			JSONObject bodyObj = Util.jsonParse(body);
			bodyObj.entrySet().forEach(p -> {
				builder.queryParam(p.getKey(), p.getValue());
			});
			HttpEntity<String> requestEntity = new HttpEntity<>(headers);
			HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, String.class);
			return response.getBody();
		}
		return getTestResponse(serviceURL, body, type, headers, new HashMap<>());
	}

	private String getTestResponse(String serviceURL, String body, HttpMethod type, HttpHeaders headers, Map<String, ?> urlVariables) {
		HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(getBaseURL() + serviceURL, type, requestEntity, String.class, urlVariables);
		return responseEntity.getBody();
	}

	public String getToken() {
		if (jwtToken == null) {
			JSONObject params = new JSONObject();
			params.put("email", TestConstants.email);
			params.put("sifre", TestConstants.password);
			String resp = getTestResponse("/kullanici/giris", params.toString(), HttpMethod.GET, false);
			JSONObject jsonData = Util.jsonParse(resp);
			assertEquals(jsonData.get("status"), HttpStatus.OK.name());
			JSONObject result = (JSONObject) jsonData.get("result");
			assertNotNull(result);
			jwtToken = (String) result.get("token");
			assertNotNull(jwtToken);
			log.info("logged in. token: " + jwtToken);
		}
		return jwtToken;
	}

}
