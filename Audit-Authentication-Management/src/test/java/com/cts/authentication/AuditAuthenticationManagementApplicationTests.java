package com.cts.authentication;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@RunWith(SpringRunner.class)
class AuditAuthenticationManagementApplicationTests {

	@Mock
	AuditAuthenticationManagementApplication application;

	@Test
	public void contextLoads() {
		assertNotNull(application);
	}

}
