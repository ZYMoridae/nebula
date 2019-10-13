package com.jz.nebula.dao.sku;

import com.jz.nebula.entity.sku.Sku;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SkuRepository extends PagingAndSortingRepository<Sku, Long> {
}
