package com.cts.benchmark.exception;

/**
 * This class tests the GlobalExceptionHandler class
 */
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;

import com.cts.benchmark.exception.FeignProxyException;
import com.cts.benchmark.exception.GlobalExceptionHandler;

import lombok.extern.slf4j.Slf4j;

//@RunWith(SpringRunner.class)
@ContextConfiguration @Slf4j
@SpringBootTest
public class GlobalExceptionHandlerTest {

	@InjectMocks
	GlobalExceptionHandler handler;

	@Mock
	Environment env;
	@Test
	public void contextLoads() {
		assertNotNull(handler);
	}
	
	@Test
	public void testhandelWrongDateFormateException() {
		assertNotNull(handler.handelFeignProxyException(new FeignProxyException()));
	}
	
		
}
