package com.cts.severity.pojo;

//import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * 	Test class to test AuditType
 *
 */
//@RunWith(SpringRunner.class)
@ContextConfiguration @Slf4j
@SpringBootTest
public class AuditTypeTest {

	AuditType auditType = new AuditType();
	
	@Mock
	Environment env;
	/**
	 * to test the all param constructor of AuditType
	 */
	@Test
	public void testAuditTypeAllConstructor() {
		log.info(env.getProperty("string.start"));
		AuditType type = new AuditType("abc");
		assertEquals(type.getAuditType(), "abc");
		log.info(env.getProperty("string.end"));
	}
	/**
	 * to test the getter setter of AuditType
	 */
	@Test
	public void testGetAuditType() {
		log.info(env.getProperty("string.start"));
		auditType.setAuditType("abc");
		assertEquals(auditType.getAuditType(), "abc");
		log.info(env.getProperty("string.end"));
	}
	
	
	/**
	 * to test the getter setter of toString()
	 */
	@Test
	public void testoString() {
		log.info(env.getProperty("string.start"));
		String string = auditType.toString();
		assertEquals(auditType.toString(), string);
		log.info(env.getProperty("string.end"));
	}

}
