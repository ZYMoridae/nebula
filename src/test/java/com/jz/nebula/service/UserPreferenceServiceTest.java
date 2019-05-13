package com.jz.nebula.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.jz.nebula.dao.UserPreferenceRepository;
import com.jz.nebula.entity.UserPreference;
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
public class UserPreferenceServiceTest {
    @Mock
    private UserPreferenceRepository userPreferenceRepository;

    @InjectMocks
    private UserPreferenceService userPreferenceService;

    @Before
    public void beforeTests() throws Exception {
        MockitoAnnotations.initMocks(this);
        UserPreference returnedUserPreference = new UserPreference();
        returnedUserPreference.setAddress1("address1");
        returnedUserPreference.setAddress2("address2");
        returnedUserPreference.setPostCode("3006");
        returnedUserPreference.setUserPreferenceTypeId((long) 1);
        returnedUserPreference.setUserId((long) 1);
        returnedUserPreference.setId((long) 1);
        this.expectedUserPreference = returnedUserPreference;
    }

    private UserPreference expectedUserPreference;

    @Test
    public void saveTest() {
        UserPreference parameterUserPreference = new UserPreference();

        parameterUserPreference.setAddress1("address1");
        parameterUserPreference.setAddress2("address2");
        parameterUserPreference.setPostCode("3006");
        parameterUserPreference.setUserId((long) 1);

        Optional<UserPreference> optionalUserPreference = Optional.of(expectedUserPreference);
        when(userPreferenceRepository.save(parameterUserPreference)).thenReturn(expectedUserPreference);
        when(userPreferenceRepository.findById(expectedUserPreference.getId())).thenReturn(optionalUserPreference);
        UserPreference savedUserPreference = userPreferenceService.save(parameterUserPreference);
        assertEquals(expectedUserPreference.getAddress1(), savedUserPreference.getAddress1());
        assertEquals(expectedUserPreference.getAddress2(), savedUserPreference.getAddress2());
        assertEquals(expectedUserPreference.getPostCode(), savedUserPreference.getPostCode());
        assertEquals(expectedUserPreference.getUserId(), savedUserPreference.getUserId());
    }

    @Test
    public void findByIdTest() {
        Optional<UserPreference> optionalUserPreference = Optional.of(expectedUserPreference);
        when(userPreferenceRepository.findById((long) 1)).thenReturn(optionalUserPreference);
        UserPreference resUserPreference = userPreferenceService.findById(expectedUserPreference.getId());
        assertEquals(expectedUserPreference.getAddress1(), resUserPreference.getAddress1());
        assertEquals(expectedUserPreference.getAddress2(), resUserPreference.getAddress2());
        assertEquals(expectedUserPreference.getPostCode(), resUserPreference.getPostCode());
        assertEquals(expectedUserPreference.getUserId(), resUserPreference.getUserId());
    }

    @Test
    public void deleteByIdTest() {
        Mockito.doAnswer((Answer<?>) invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals((long) 1, arg0);
            return null;
        }).when(userPreferenceRepository).deleteById(Mockito.any(long.class));
        userPreferenceService.delete((long) 1);
    }
}
