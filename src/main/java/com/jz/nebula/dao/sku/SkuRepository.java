package com.jz.nebula.dao.sku;

import com.jz.nebula.entity.sku.Sku;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface SkuRepository extends PagingAndSortingRepository<Sku, Long> {
    Optional<Sku> findBySkuCode(String skuCode);

}
