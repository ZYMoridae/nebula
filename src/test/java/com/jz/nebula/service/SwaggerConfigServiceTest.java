package com.jz.nebula.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.jz.nebula.dao.SwaggerConfigRepository;
import com.jz.nebula.entity.api.SwaggerConfig;
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
public class SwaggerConfigServiceTest {
    @Mock
    private SwaggerConfigRepository swaggerConfigRepository;

    @InjectMocks
    private SwaggerConfigService swaggerConfigService;

    @Before
    public void beforeTests() throws Exception {
        MockitoAnnotations.initMocks(this);
        SwaggerConfig returnedswaggerConfig = new SwaggerConfig();
        returnedswaggerConfig.setName("url");
        returnedswaggerConfig.setValue("value");
        returnedswaggerConfig.setId((long) 1);
        this.expectedSwaggerConfig = returnedswaggerConfig;
    }

    private SwaggerConfig expectedSwaggerConfig;

    @Test
    public void saveTest() {
        SwaggerConfig parameterSwaggerConfig = new SwaggerConfig();
        parameterSwaggerConfig.setName("test");
        parameterSwaggerConfig.setValue("value");

        when(swaggerConfigRepository.save(parameterSwaggerConfig)).thenReturn(expectedSwaggerConfig);
        SwaggerConfig savedSwaggerConfig = swaggerConfigService.save(parameterSwaggerConfig);
        assertEquals(expectedSwaggerConfig.getName(), savedSwaggerConfig.getName());
        assertEquals(expectedSwaggerConfig.getValue(), savedSwaggerConfig.getValue());
    }

    @Test
    public void findByIdTest() {
        Optional<SwaggerConfig> optionalSwaggerConfig = Optional.of(expectedSwaggerConfig);
        when(swaggerConfigRepository.findById((long) 1)).thenReturn(optionalSwaggerConfig);
        SwaggerConfig resSwaggerConfig = swaggerConfigService.findById(expectedSwaggerConfig.getId());
        assertEquals(expectedSwaggerConfig.getName(), resSwaggerConfig.getName());
        assertEquals(expectedSwaggerConfig.getValue(), resSwaggerConfig.getValue());
        assertEquals(expectedSwaggerConfig.getId(), resSwaggerConfig.getId());
    }

    @Test
    public void deleteByIdTest() {
        Mockito.doAnswer((Answer<?>) invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals((long) 1, arg0);
            return null;
        }).when(swaggerConfigRepository).deleteById(Mockito.any(long.class));
        swaggerConfigService.delete((long) 1);
    }
}
