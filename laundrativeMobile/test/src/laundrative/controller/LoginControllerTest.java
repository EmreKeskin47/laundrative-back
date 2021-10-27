package laundrative.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import laundrative.TestBase;
import laundrative.TestConstants;
import laundrative.beans.db.Musteri;
import laundrative.dao.MusteriDAO;
import laundrative.util.Security;
import laundrative.util.Util;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Log4j2
public class LoginControllerTest extends TestBase {
	@Autowired
	private MusteriDAO musteriDAO;

	@BeforeAll
	@Transactional
	public void setup() {
		Musteri musteri = musteriDAO.getMusteriByEmail(TestConstants.email);
		assertNotNull(musteri);
		if (musteri.getSifre().equals("")) {
			musteri.setSifre(Security.encode(TestConstants.password));
			musteriDAO.kaydet(musteri);
		}
	}

	@Test
	public void loginTest() {
		JSONObject params = new JSONObject();
		params.put("email", TestConstants.email);
		params.put("sifre", TestConstants.password);
		String resp = getTestResponse("/kullanici/giris", params.toString(), HttpMethod.GET, false);
		log.info("login response:" + resp);
		JSONObject jsonData = Util.jsonParse(resp);
		assertEquals(jsonData.get("status"), HttpStatus.OK.name());
		JSONObject result = (JSONObject) jsonData.get("result");
		assertNotNull(result);
		assertNotNull(result.get("token"));
	}
}
