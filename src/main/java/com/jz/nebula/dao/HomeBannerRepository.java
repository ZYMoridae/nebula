package com.jz.nebula.dao;

import com.jz.nebula.entity.HomeBanner;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface HomeBannerRepository extends PagingAndSortingRepository<HomeBanner, Long> {
    List<HomeBanner> findByActive(boolean active);
}
