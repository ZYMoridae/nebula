package com.jz.nebula.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

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
@SpringBootTest(classes=Application.class)
public class ShipperServiceTest {
	@Mock
	private ShipperRepository shipperRepository;
	
	@InjectMocks
	private ShipperService shipperService;
	
    @Before
    public void beforeTests() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void saveTest() {
    	Shipper parameterShipper = new Shipper();
    	parameterShipper.setName("test");
    	parameterShipper.setContact("123456");
    	
    	Shipper returnedShipper = new Shipper();
    	returnedShipper.setName("test");
    	returnedShipper.setContact("123456");
    	returnedShipper.setId((long) 1);
    	when(shipperRepository.save(parameterShipper)).thenReturn(returnedShipper);
    	Shipper savedShipper = shipperService.save(parameterShipper);
    	assertEquals(returnedShipper.getName(), savedShipper.getName());
    	assertEquals(returnedShipper.getContact(), savedShipper.getContact());
    	assertEquals(returnedShipper.getId(), savedShipper.getId());
    }
}
