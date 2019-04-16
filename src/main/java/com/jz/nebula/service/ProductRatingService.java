package com.jz.nebula.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jz.nebula.auth.AuthenticationFacade;
import com.jz.nebula.dao.ProductRatingRepository;
import com.jz.nebula.entity.ProductRating;
import com.jz.nebula.entity.User;

@Service
@Component("productRatingService")
public class ProductRatingService {
	@Autowired
	private ProductRatingRepository productRatingRepository;
	
	@Autowired
	private AuthenticationFacade authenticationFacade;
	
	public ProductRating save(ProductRating productRating) {
		User user = authenticationFacade.getUser();
		if(!user.isAdmin()) {
			productRating.setUsertId(user.getId());
		}
		return productRatingRepository.save(productRating);
	}

	public ProductRating findById(long id) {
		return productRatingRepository.findById(id).get();
	}

	public List<ProductRating> findByProductId(long productId) {
		List<ProductRating> productRatings = productRatingRepository.findByProductId(productId);
		return productRatings;
	}

	public Object getRating(long productId) {
		Map<Object, Object> ratingObject = new ConcurrentHashMap<>();
		List<ProductRating> productRatings = findByProductId(productId);
		double sumRatings = productRatings.stream().map(ele -> ele.getRating()).reduce(0,
				(ele1, ele2) -> ele1 + ele2);
		ratingObject.put("rating", String.valueOf(sumRatings / productRatings.size()));
		ratingObject.put("totalRatingCount", productRatings.size());
		
		return ratingObject;
	}

	public void delete(long id) {
		productRatingRepository.deleteById(id);
	}
}
