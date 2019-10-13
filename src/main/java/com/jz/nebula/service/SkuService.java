package com.jz.nebula.service;

import com.jz.nebula.dao.sku.SkuRepository;
import com.jz.nebula.entity.sku.Sku;
import com.jz.nebula.util.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkuService {

    String skuCodePrefix = "SKU";

    @Autowired
    private SkuRepository skuRepository;

    /**
     * Insert stock keeping unit
     *
     * @param sku
     * @return
     */
    public Sku create(Sku sku) {
        sku.setSkuCode(skuCodeGenerator(sku));
        return skuRepository.save(sku);
    }

    /**
     * @param sku
     * @return
     */
    public Sku update(Sku sku) {
        skuRepository.save(sku);
        return find(sku.getId());
    }

    /**
     * @param skuId
     * @return
     */
    public Sku find(long skuId) {
        return skuRepository.findById(skuId).get();
    }

    /**
     * @param skuId
     */
    public void delete(long skuId) {
        skuRepository.delete(find(skuId));
    }

    /**
     * Generate sku code
     *
     * @param sku
     * @return
     */
    private String skuCodeGenerator(Sku sku) {
        String skuCode = "";

        Long currentTime = System.currentTimeMillis();
        skuCode = skuCodePrefix + Security.generateHash(currentTime + String.valueOf(sku.getProductId()) + Security.getRandomHash());

        return skuCode;
    }
}
