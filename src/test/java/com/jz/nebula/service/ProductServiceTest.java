package com.jz.nebula.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
import com.jz.nebula.dao.ProductRepository;
import com.jz.nebula.entity.product.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product expectedProduct;

    @Before
    public void beforeTests() throws Exception {
        MockitoAnnotations.initMocks(this);
        Product returnedProduct = new Product();
        returnedProduct.setId((long) 1);
        returnedProduct.setName("test");
        returnedProduct.setPrice(12.3);
        returnedProduct.setUnitsInStock(6);
        this.expectedProduct = returnedProduct;
    }

    /**
     * FIXME: Change the SKU
     */
//    @Test
//    public void saveTest() {
//        Product parameterProduct = new Product();
//        parameterProduct.setName("test");
//        parameterProduct.setPrice(12.3);
//        parameterProduct.setUnitsInStock(6);
//
//        Optional<Product> optionalProduct = Optional.of(expectedProduct);
//
//        when(productRepository.save(parameterProduct)).thenReturn(expectedProduct);
//        when(productRepository.findById((long) 1)).thenReturn(optionalProduct);
//        Product savedProduct = productService.save(parameterProduct);
//        assertEquals(expectedProduct.getName(), savedProduct.getName());
//        assertEquals(expectedProduct.getId(), savedProduct.getId());
//        assertEquals(expectedProduct.getPrice(), savedProduct.getPrice(), 0);
//        assertEquals(expectedProduct.getUnitsInStock(), savedProduct.getUnitsInStock());
//    }

    @Test
    public void findByIdTest() {
        Optional<Product> optionalProduct = Optional.of(expectedProduct);
        when(productRepository.findById((long) 1)).thenReturn(optionalProduct);
        Product resProduct = productService.findById(expectedProduct.getId());
        assertEquals(expectedProduct.getName(), resProduct.getName());
        assertEquals(expectedProduct.getId(), resProduct.getId());
        assertEquals(expectedProduct.getPrice(), resProduct.getPrice(), 0);
        assertEquals(expectedProduct.getUnitsInStock(), resProduct.getUnitsInStock());
    }

    @Test
    public void deleteByIdTest() {
        Mockito.doAnswer((Answer<?>) invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals((long) 1, arg0);
            return null;
        }).when(productRepository).deleteById(Mockito.any(long.class));
        productService.delete((long) 1);
    }
}
