package com.jz.nebula.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;


import com.jz.nebula.dao.WishListItemRepository;
import com.jz.nebula.entity.User;
import com.jz.nebula.entity.WishListItem;
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
public class WishListItemServiceTest {
    @Mock
    private WishListItemRepository wishListItemRepository;

    @InjectMocks
    private WishListItemService wishListItemService;


    private User expectedUser;


    private WishListItem expectedWishListItem;

    @Before
    public void beforeTests() {
        MockitoAnnotations.initMocks(this);

        com.jz.nebula.entity.User expectedUser = new com.jz.nebula.entity.User();
        expectedUser.setUsername("test");
        expectedUser.setPassword("test");
        this.expectedUser = expectedUser;


        WishListItem wishListItem = new WishListItem();
        wishListItem.setId(Long.valueOf(1));
        wishListItem.setQuantity(2);
        this.expectedWishListItem = wishListItem;
    }

    @Test
    public void findByIdTest() {
        Optional<WishListItem> optionalWishListItem = Optional.of(this.expectedWishListItem);

        when(wishListItemRepository.findById(this.expectedWishListItem.getId())).thenReturn(optionalWishListItem);
        WishListItem targetWishListItem = wishListItemService.findById(this.expectedWishListItem.getId());

        assertEquals(targetWishListItem.getId(), this.expectedWishListItem.getId());
    }

    @Test
    public void deleteByIdTest() {
        Mockito.doAnswer((Answer<?>) invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals((long) 1, arg0);
            return null;
        }).when(wishListItemRepository).deleteById(Mockito.any(long.class));
        wishListItemService.delete(1);
    }

}
