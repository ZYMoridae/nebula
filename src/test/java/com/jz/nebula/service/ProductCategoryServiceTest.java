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
//import com.jz.nebula.dao.ProductCategoryRepository;
//import com.jz.nebula.entity.ProductCategory;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
//public class ProductCategoryServiceTest {
//	@Mock
//	private ProductCategoryRepository productCategoryRepository;
//
//	@InjectMocks
//	private ProductCategoryService productCategoryService;
//
//	private ProductCategory expectedProdctCategory;
//
//	@Before
//	public void beforeTests() throws Exception {
//		MockitoAnnotations.initMocks(this);
//		ProductCategory returnedProductCategory = new ProductCategory();
//		returnedProductCategory.setName("test");
//		returnedProductCategory.setId((long) 1);
//		this.expectedProdctCategory = returnedProductCategory;
//	}
//
//	@Test
//	public void saveTest() {
//		ProductCategory parameterProductCategory = new ProductCategory();
//		parameterProductCategory.setName("test");
//
//		when(productCategoryRepository.save(parameterProductCategory)).thenReturn(expectedProdctCategory);
//		ProductCategory savedProductCategory = productCategoryService.save(parameterProductCategory);
//		assertEquals(expectedProdctCategory.getName(), savedProductCategory.getName());
//		assertEquals(expectedProdctCategory.getId(), savedProductCategory.getId());
//	}
//
//	@Test
//	public void findByIdTest() {
//		Optional<ProductCategory> optionalProductCategory = Optional.of(expectedProdctCategory);
//		when(productCategoryRepository.findById((long) 1)).thenReturn(optionalProductCategory);
//		ProductCategory resProductCategory = productCategoryService.findById(expectedProdctCategory.getId());
//		assertEquals(expectedProdctCategory.getName(), resProductCategory.getName());
//		assertEquals(expectedProdctCategory.getId(), resProductCategory.getId());
//	}
//
//	@Test
//	public void deleteByIdTest() {
//		Mockito.doAnswer((Answer<?>) invocation -> {
//			Object arg0 = invocation.getArgument(0);
//			assertEquals((long) 1, arg0);
//			return null;
//		}).when(productCategoryRepository).deleteById(Mockito.any(long.class));
//		productCategoryService.delete((long) 1);
//	}
//}
