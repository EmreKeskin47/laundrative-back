package laundrative.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;

import laundrative.TestBase;
import laundrative.util.Util;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Log4j2
public class HizmetControllerTest extends TestBase {

	@Test
	public void hizmetAramaTest() {
		JSONObject params = new JSONObject();
		params.put("urunAdi", "a");
		String resp = getTestResponse("/hizmet/arama", params.toString(), HttpMethod.GET, true);
		log.info("login response:" + resp);
		JSONArray jsonData = Util.jsonParseArray(resp);
		jsonData.forEach(d -> {
			JSONObject o = (JSONObject) d;
			assertNotNull(o.get("resim"));
			assertNotNull(o.get("kategori"));
			assertNotNull(o.get("isim"));
		});
		log.info(jsonData.size() + " hizmet validated");
	}
}
