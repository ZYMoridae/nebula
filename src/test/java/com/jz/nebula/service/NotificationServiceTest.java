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
import com.jz.nebula.dao.NotificationRepository;
import com.jz.nebula.entity.Notification;
import com.jz.nebula.entity.NotificationStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class NotificationServiceTest {
	@Mock
	private NotificationRepository notificationRepository;
	
	@InjectMocks
	private NotificationService notificationService;
	
	private Notification expectedNotification;
	
	@Before
	public void beforeTests() throws Exception {
		MockitoAnnotations.initMocks(this);
		Notification returnedNotification = new Notification();
		NotificationStatus notificationStatus = new NotificationStatus();
		notificationStatus.setId((long) 1);
		notificationStatus.setCode("UNREAD");
		
		returnedNotification.setUserId((long) 1);
		returnedNotification.setId((long) 1);
		returnedNotification.setNotificationStatus(notificationStatus);
		returnedNotification.setStatusId(notificationStatus.getId());
		returnedNotification.setBody("test");
		this.expectedNotification = returnedNotification;
	}
	
	@Test
	public void createNotificationTest() {
		Notification parameterNotification = new Notification();
		parameterNotification.setBody("test");
		parameterNotification.setUserId((long) 1);
		parameterNotification.setStatusId((long) 1);
		
		Optional<Notification> optionalNotification = Optional.of(expectedNotification);
		
		when(notificationRepository.findById((long) 1)).thenReturn(optionalNotification);

		when(notificationRepository.save(parameterNotification)).thenReturn(expectedNotification);
		Notification savedNotification = notificationService.save(parameterNotification);
		assertEquals(expectedNotification.getBody(), savedNotification.getBody());
		assertEquals(expectedNotification.getUserId(), savedNotification.getUserId());
		assertEquals(expectedNotification.getStatusId(), savedNotification.getStatusId());
		assertEquals(expectedNotification.getId(), savedNotification.getId());
	}
}
