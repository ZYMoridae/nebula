package com.jz.nebula.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jz.nebula.auth.IAuthenticationFacade;
import com.jz.nebula.controller.WishListItemController;
import com.jz.nebula.dao.UserRepository;
import com.jz.nebula.dao.WishListItemRepository;
import com.jz.nebula.dao.WishListRepository;
import com.jz.nebula.entity.CartItem;
import com.jz.nebula.entity.User;
import com.jz.nebula.entity.WishList;
import com.jz.nebula.entity.WishListItem;
import com.jz.nebula.validator.WishListItemValidator;

@Service
@Component("wishListItemService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
public class WishListItemService {
	private final Logger logger = LogManager.getLogger(WishListItemService.class);

	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@Autowired
	private WishListRepository wishListRepository;

	@Autowired
	private WishListItemRepository wishListItemRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WishListItemValidator wishListItemValidator;

	@Autowired
	private CartItemService cartItemService;

	private WishList getWishList() {
		User user = userRepository.findByUsername(authenticationFacade.getAuthentication().getName()).get();
		WishList wishList = null;
		if (user != null) {
			wishList = wishListRepository.findByUserId(user.getId());
		}
		return wishList;
	}

	private WishListItem getItemInWishList(WishListItem wishListItem) {
		Optional<WishListItem> optional = wishListItemRepository
				.findByWishListIdAndProductId(wishListItem.getWishListId(), wishListItem.getProductId());
		return optional.isPresent() ? optional.get() : null;
	}

	/**
	 * Find cart items by cart id
	 * 
	 * @param cartId
	 * @param pageable
	 * @param assembler
	 * @return
	 */
	public PagedResources<Resource<WishListItem>> findByWishListId(long wishListId, Pageable pageable,
			PagedResourcesAssembler<WishListItem> assembler) {
		Page<WishListItem> page = wishListItemRepository.findByWishListId(wishListId, pageable);
		PagedResources<Resource<WishListItem>> resources = assembler.toResource(page,
				linkTo(WishListItemController.class).slash("/wishlist-items").withSelfRel());
		;
		return resources;
	}

	@Transactional(rollbackFor = { Exception.class })
	public synchronized WishListItem save(WishListItem wishListItem) throws Exception {
		boolean isValid = wishListItemValidator.validate(wishListItem);
		WishListItem updatedWishListItem = null;
		if (!isValid) {
			return null;
		}
		WishList wishList = getWishList();
		if (wishList == null) {
			wishList = new WishList(authenticationFacade.getUser().getId());
			wishList = wishListRepository.save(wishList);
		}

		wishListItem.setWishListId(wishList.getId());

		WishListItem existingWishListItem = getItemInWishList(wishListItem);
		if (existingWishListItem != null) {
			existingWishListItem.setQuantity(existingWishListItem.getQuantity() + wishListItem.getQuantity());
			updatedWishListItem = wishListItemRepository.save(existingWishListItem);
		} else {
			updatedWishListItem = wishListItemRepository.save(wishListItem);
		}

//		try {
//			this.updateStock(cartItem);
//		} catch (ProductStockException e) {
//			logger.error("product stock update error...");
//			e.printStackTrace();
//			return null;
//		}
		logger.info("Product with id:[{}] has been added", wishListItem.getProductId());

		return findById(updatedWishListItem.getId());
	}

	public WishListItem findById(long id) {
		return wishListItemRepository.findById(id).get();
	}

	public void delete(long id) {
		wishListItemRepository.deleteById(id);
	}

	public CartItem toCartItem(WishListItem wishListItem) throws Exception {
		CartItem cartItem = wishListItem.toCartItem();
		cartItemService.save(cartItem);
		delete(wishListItem.getId());

		return cartItemService.findById(cartItem.getId());
	}
}
