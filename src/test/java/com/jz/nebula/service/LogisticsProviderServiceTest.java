package com.jz.nebula.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.jz.nebula.dao.LogisticsProviderRepository;
import com.jz.nebula.entity.LogisticsProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jz.nebula.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class LogisticsProviderServiceTest {
	@Mock
	private LogisticsProviderRepository logisticsProviderRepository;

	@InjectMocks
	private LogisticsProviderService logisticsProviderService;

	@Before
	public void beforeTests() throws Exception {
		MockitoAnnotations.initMocks(this);
		LogisticsProvider returnedLogisticsProvider = new LogisticsProvider();
		returnedLogisticsProvider.setName("test");
		returnedLogisticsProvider.setContact("123456");
		returnedLogisticsProvider.setId((long) 1);
		this.expectedLogisticsProvider = returnedLogisticsProvider;
	}

	private LogisticsProvider expectedLogisticsProvider;

	@Test
	public void saveTest() {
		LogisticsProvider parameterLogisticsProvider = new LogisticsProvider();
		parameterLogisticsProvider.setName("test");
		parameterLogisticsProvider.setContact("123456");

		when(logisticsProviderRepository.save(parameterLogisticsProvider)).thenReturn(expectedLogisticsProvider);
		LogisticsProvider savedLogisticsProvider = logisticsProviderService.save(parameterLogisticsProvider);
		assertEquals(expectedLogisticsProvider.getName(), savedLogisticsProvider.getName());
		assertEquals(expectedLogisticsProvider.getContact(), savedLogisticsProvider.getContact());
		assertEquals(expectedLogisticsProvider.getId(), savedLogisticsProvider.getId());
	}

	@Test
	public void findByIdTest() {
		Optional<LogisticsProvider> optionalShipper = Optional.of(expectedLogisticsProvider);
		when(logisticsProviderRepository.findById((long) 1)).thenReturn(optionalShipper);
		LogisticsProvider resLogisticsProvider = logisticsProviderService.findById(expectedLogisticsProvider.getId());
		assertEquals(expectedLogisticsProvider.getName(), resLogisticsProvider.getName());
		assertEquals(expectedLogisticsProvider.getContact(), resLogisticsProvider.getContact());
		assertEquals(expectedLogisticsProvider.getId(), resLogisticsProvider.getId());
	}

	@Test
	public void deleteByIdTest() {
		Mockito.doAnswer((Answer<?>) invocation -> {
			Object arg0 = invocation.getArgument(0);
			assertEquals((long) 1, arg0);
			return null;
		}).when(logisticsProviderRepository).deleteById(Mockito.any(long.class));
		logisticsProviderService.delete((long) 1);
	}
}
