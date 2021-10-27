package laundrative;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import laundrative.controller.AdresController;
import lombok.experimental.ExtensionMethod;

@Ex
@SpringBootTest
public class LaundrativeMobileApplicationTest {
	@Autowired
	private AdresController adresController;

	@Test
	public void test1() {
		assertThat(adresController).isNotNull();
		// assertThat(restTemplate).isNotNull();
	}

	@Test
	public void test2() {
		// String resp = this.restTemplate.getForObject("https://monster:" + port + "/version", String.class);
		// System.out.println(resp);
		// assertThat(resp).contains("version");
	}
}
