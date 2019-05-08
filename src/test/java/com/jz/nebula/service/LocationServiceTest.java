//package com.jz.nebula.service;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.util.Optional;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.stubbing.Answer;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.jz.nebula.Application;
//import com.jz.nebula.dao.LocationRepository;
//import com.jz.nebula.entity.Location;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
//public class LocationServiceTest {
//	@Mock
//	private LocationRepository locationRepository;
//
//	@InjectMocks
//	private LocationService locationService;
//
//	private Location expectedLocation;
//
//	@Before
//	public void beforeTests() throws Exception {
//		MockitoAnnotations.initMocks(this);
//		Location returnedLocation = new Location();
//		returnedLocation.setId((long) 1);
//		returnedLocation.setCountryCode("AU");
//		returnedLocation.setName("Melbourne");
//		returnedLocation.setPostCode("3000");
//		returnedLocation.setStateCode("VIC");
//		this.expectedLocation = returnedLocation;
//	}
//
//	@Test
//	public void createLocationTest() {
//		Location paramLocation = new Location();
//		paramLocation.setCountryCode("AU");
//		paramLocation.setName("Melbourne");
//		paramLocation.setPostCode("3000");
//		paramLocation.setStateCode("VIC");
//
//		Optional<Location> optionalLocation = Optional.of(expectedLocation);
//		when(locationRepository.findById((long) 1)).thenReturn(optionalLocation);
//		when(locationRepository.save(paramLocation)).thenReturn(expectedLocation);
//
//		Location savedLocation = locationService.save(paramLocation);
//		assertEquals(expectedLocation.getCountryCode(), savedLocation.getCountryCode());
//		assertEquals(expectedLocation.getName(), savedLocation.getName());
//		assertEquals(expectedLocation.getPostCode(), savedLocation.getPostCode());
//		assertEquals(expectedLocation.getStateCode(), savedLocation.getStateCode());
//	}
//
//	@Test
//	public void deleteByIdTest() {
//		Mockito.doAnswer((Answer<?>) invocation -> {
//			Object arg0 = invocation.getArgument(0);
//			assertEquals((long) 1, arg0);
//			return null;
//		}).when(locationRepository).deleteById(Mockito.any(long.class));
//		locationService.delete((long) 1);
//	}
//}
