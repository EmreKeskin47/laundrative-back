package laundrative;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import laundrative.controller.AdresController;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LaundrativeMobileApplicationTest {
	@Autowired
	private AdresController adresController;
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Test
	public void test1() {
		assertThat(adresController).isNotNull();
		// assertThat(restTemplate).isNotNull();
	}

	@Test
	public void test2() {
		System.out.println("Port:" + port);
		String resp = this.restTemplate.getForObject("http://monster:" + port + "/version", String.class);
		System.out.println(resp);
		assertThat(resp).contains("version");
	}
}
