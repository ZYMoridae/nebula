package com.jz.nebula.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.javafaker.Faker;
import com.jz.nebula.Application;
import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.User;
import com.jz.nebula.util.MockDataManager;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@Before
	public void beforeTests() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void loadUserByUsernameTest() {
		MockDataManager.getEntityWithFakeData(User.class);
		Faker faker = new Faker();
		User user = new User();
		user.setUsername(faker.name().name());
		List<String> roles = new ArrayList<String>();
		roles.add(Role.ROLE_USER);
		user.setRoles(roles);
		Role role = new Role();
		role.setCode("USER");
		user.setRole(role);
		user.setPassword("$2a$10$rHx0naos8Q1SEfIRekAkreeedzadMrECNOBaD/Qj3eaj0U5lkZrQS");
		Optional<User> userOptional = Optional.of(user);
		when(userRepository.findByUsername("test")).thenReturn(userOptional);

		UserDetails userDetails = userService.loadUserByUsername("test");
		assertTrue(userDetails != null);
		assertEquals("test", userDetails.getUsername());
	}
}
