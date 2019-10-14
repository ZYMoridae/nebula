package com.jz.nebula.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.jz.nebula.dao.sku.SkuRepository;
import com.jz.nebula.entity.sku.Sku;
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

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SkuServiceTest {

    @Mock
    private SkuRepository skuRepository;

    @InjectMocks
    private SkuService skuService;

    private Sku expectedSku;

    @Before
    public void beforeTests() throws Exception {
        Sku returnedSku = new Sku();
        returnedSku.setId((long) 1);
        returnedSku.setPrice(33.56);
        returnedSku.setStock(99);
        returnedSku.setProductId((long) 5);
        returnedSku.setCreatedUserId((long) 3);
        this.expectedSku = returnedSku;
    }

    @Test
    public void createSkuTest() {
        Sku param = new Sku();
        param.setId((long) 1);
        param.setPrice(33.56);
        param.setStock(99);
        param.setProductId((long) 5);
        param.setCreatedUserId((long) 3);

        Optional<Sku> optionalSku = Optional.of(expectedSku);
        when(skuRepository.findById((long) 1)).thenReturn(optionalSku);
        when(skuRepository.save(param)).thenReturn(expectedSku);

        Sku savedSku = skuService.create(param);
        assertEquals(expectedSku.getId(), savedSku.getId());
        assertEquals(expectedSku.getPrice(), savedSku.getPrice());
        assertEquals(expectedSku.getProductId(), savedSku.getProductId());
        assertEquals(expectedSku.getStock(), savedSku.getStock());
        assertEquals(expectedSku.getCreatedUserId(), savedSku.getCreatedUserId());
    }

    @Test
    public void deleteByIdTest() {
        Mockito.doAnswer((Answer<?>) invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals((long) 1, arg0);
            return null;
        }).when(skuRepository).deleteById(Mockito.any(long.class));
        skuService.delete(1);
    }
}
