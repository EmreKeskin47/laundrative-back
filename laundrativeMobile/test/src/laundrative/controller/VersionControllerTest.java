package laundrative.controller;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import laundrative.HibernateConf;
import laundrative.util.ITestScenarios;
import laundrative.util.Security;
import laundrative.util.Util;

@ExtendWith(MockitoExtension.class)
public class VersionControllerTest {
	@InjectMocks
	private VersionController versionController;
	@Mock
	private Util util;

	@Mock
	private ITestScenarios testScenarios;

	@Mock
	private HttpSession session;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private HibernateConf hibernateConf;
	@Mock
	private Security security;

	@Test
	public void versionTest() {
		Mockito.when(util.getParametreValue(ArgumentMatchers.eq(Util.ParameterConsts.MOBILE_APP_VERSION))).thenReturn("2");
		String s = versionController.testSocket();
		System.out.println(s);
	}
}
