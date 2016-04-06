package com.kerz.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Dave Syer
 *
 */
@SpringApplicationConfiguration(classes = Application.class)
public class ProtectedResourceTests extends AbstractIntegrationTests {

	@Test
	public void testHomePageIsProtected() throws Exception {
		ResponseEntity<String> response = serverRunning.getForString("/");
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertTrue("Wrong header: " + response.getHeaders(), response.getHeaders().getFirst("WWW-Authenticate")
				.startsWith("Bearer realm="));
	}

	@Test
	public void testBeansResourceIsProtected() throws Exception {
		ResponseEntity<String> response = serverRunning.getForString("/admin/beans");
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertTrue("Wrong header: " + response.getHeaders(), response.getHeaders().getFirst("WWW-Authenticate")
				.startsWith("Bearer realm="));
	}

	@Test
	public void testDumpResourceIsProtected() throws Exception {
		ResponseEntity<String> response = serverRunning.getForString("/admin/dump");
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertTrue("Wrong header: " + response.getHeaders(), response.getHeaders().getFirst("WWW-Authenticate")
				.startsWith("Basic realm="));
	}

	@Test
	public void testHealthResourceIsOpen() throws Exception {
		assertEquals(HttpStatus.OK, serverRunning.getStatusCode("/admin/health"));
	}


}
