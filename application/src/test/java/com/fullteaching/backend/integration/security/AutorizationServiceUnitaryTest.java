package com.fullteaching.backend.integration.security;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fullteaching.backend.AbstractUnitTest;
import com.fullteaching.backend.security.AuthorizationService;
import com.fullteaching.backend.user.User;
import com.fullteaching.backend.user.UserComponent;

public class AutorizationServiceUnitaryTest extends AbstractUnitTest {

	@Autowired
	private UserComponent user;

	@Autowired
	private AuthorizationService service;
	
	@BeforeEach
	public void setUp() throws Exception {
		if(user.getLoggedUser()==null) {
			String[] roles = {"STUDENT"};
			User u = new User("TestUser", "Mock6666", "mock", null,roles);
			user.setLoggedUser(u);
		}	
	}

	@Test
	public void checkBackendLoggedTest() {
		ResponseEntity<Object> r = service.checkBackendLogged();
		
		
		assertEquals(null, r,"Expeceted null");
		
		
		user.setLoggedUser(null);
		ResponseEntity<Object> r2 = service.checkBackendLogged();
		
		int status2 = r2.getStatusCodeValue();
		int expected2 = HttpStatus.UNAUTHORIZED.value();
		
		assertTrue(status2==expected2,
				"failure login - expected HTTP status "+
				expected2 +
				" but was: "+status2);
		
		user.setLoggedUser(null);
	}

	@Test
	public void checkAuthorizationTest() {
		String o ="Example object";
		
		String[] roles = {"STUDENT"};
		User u = new User("FailUser", "Mock6666", "mock", null,roles);
		
		ResponseEntity <Object> r = service.checkAuthorization(null, u);
		int status1 = r.getStatusCodeValue();
		int expected1 = HttpStatus.NOT_MODIFIED.value();
		
		assertEquals( expected1, status1,"failure - expected HTTP status "+expected1);
		
		
		ResponseEntity <Object> r2 = service.checkAuthorization(o, u);
		int status2 = r2.getStatusCodeValue();
		int expected2 = HttpStatus.UNAUTHORIZED.value();
		
		assertTrue(status2==expected2,
				"failure login - expected HTTP status "+
						expected2 +
						" but was: "+status2);
		
		ResponseEntity <Object> r3 = service.checkAuthorization(o, user.getLoggedUser());
		
		assertEquals(null, r3, "Expeceted null");

		
	}

	@Test
	public void checkAuthorizationUsersTest() {
		String o ="Example object";
		
		String[] roles = {"STUDENT"};
		Collection<User> u = new ArrayList<User>();
		
		u.add(new User("user3", "Mock6666", "mock", null,roles));
		u.add(new User("user1", "Mock6666", "mock", null,roles));
		u.add(new User("user2", "Mock6666", "mock", null,roles));
				
		ResponseEntity <Object> r = service.checkAuthorizationUsers(null, u);
		int status1 = r.getStatusCodeValue();
		int expected1 = HttpStatus.BAD_REQUEST.value();
		
		assertEquals(expected1, status1, "failure - expected HTTP status "+expected1);
		
		
		ResponseEntity <Object> r2 = service.checkAuthorizationUsers(o, u);
		int status2 = r2.getStatusCodeValue();
		int expected2 = HttpStatus.UNAUTHORIZED.value();
		
		assertTrue(status2==expected2,
				"failure login - expected HTTP status "+
						expected2 +
						" but was: "+status2);
		
		u.add(user.getLoggedUser());
		ResponseEntity <Object> r3 = service.checkAuthorizationUsers(o, u);
		
		assertEquals(null, r3,"Expeceted null");
		
	}

}