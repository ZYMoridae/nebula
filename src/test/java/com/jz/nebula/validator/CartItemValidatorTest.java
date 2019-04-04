package com.jz.nebula.validator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.jz.nebula.util.Security;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.yml")
public class CartItemValidatorTest {
	
	@Autowired
	CartItemValidator cartItemValidator;
	
	@Test
	public void getRandomHashTest() {
		assertTrue(true);
	}
}
