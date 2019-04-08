package com.jz.nebula.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jz.nebula.Application;
import com.jz.nebula.dao.ShipperRepository;
import com.jz.nebula.entity.Shipper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ShipperServiceTest {
	@Mock
	private ShipperRepository shipperRepository;

	@InjectMocks
	private ShipperService shipperService;

	@Before
	public void beforeTests() throws Exception {
		MockitoAnnotations.initMocks(this);
		Shipper returnedShipper = new Shipper();
		returnedShipper.setName("test");
		returnedShipper.setContact("123456");
		returnedShipper.setId((long) 1);
		this.expectedShipper = returnedShipper;
	}

	private Shipper expectedShipper;

	@Test
	public void saveTest() {
		Shipper parameterShipper = new Shipper();
		parameterShipper.setName("test");
		parameterShipper.setContact("123456");

		when(shipperRepository.save(parameterShipper)).thenReturn(expectedShipper);
		Shipper savedShipper = shipperService.save(parameterShipper);
		assertEquals(expectedShipper.getName(), savedShipper.getName());
		assertEquals(expectedShipper.getContact(), savedShipper.getContact());
		assertEquals(expectedShipper.getId(), savedShipper.getId());
	}

	@Test
	public void findByIdTest() {
		Optional<Shipper> optionalShipper = Optional.of(expectedShipper);
		when(shipperRepository.findById((long) 1)).thenReturn(optionalShipper);
		Shipper resShipper = shipperService.findById(expectedShipper.getId());
		assertEquals(expectedShipper.getName(), resShipper.getName());
		assertEquals(expectedShipper.getContact(), resShipper.getContact());
		assertEquals(expectedShipper.getId(), resShipper.getId());
	}
}
