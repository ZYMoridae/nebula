package com.jz.nebula.service;

import com.jz.nebula.dao.sku.SkuAttributeCategoryRepository;
import com.jz.nebula.dao.sku.SkuAttributeRepository;
import com.jz.nebula.dao.sku.SkuRepository;
import com.jz.nebula.entity.sku.Sku;
import com.jz.nebula.entity.sku.SkuAttribute;
import com.jz.nebula.entity.sku.SkuAttributeCategory;
import com.jz.nebula.util.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkuService {

    String skuCodePrefix = "SKU";

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private SkuAttributeCategoryRepository skuAttributeCategoryRepository;

    @Autowired
    private SkuAttributeRepository skuAttributeRepository;

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
     * @param id
     */
    public void delete(long id) {
        skuRepository.deleteById(id);
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

    /***** Sku Attribute Category *****/
    public SkuAttributeCategory createSkuAttributeCategory(SkuAttributeCategory skuAttributeCategory) {
        return skuAttributeCategoryRepository.save(skuAttributeCategory);
    }

    public SkuAttributeCategory findSkuAttributeCategoryById(long id) {
        return skuAttributeCategoryRepository.findById(id).get();
    }

    public SkuAttributeCategory updateSkuAttributeCategory(SkuAttributeCategory skuAttributeCategory) {
        skuAttributeCategoryRepository.save(skuAttributeCategory);
        return findSkuAttributeCategoryById(skuAttributeCategory.getId());
    }

    public void deleteSkuAttributeCategory(long id) {
        skuAttributeCategoryRepository.deleteById(id);
    }

    /*****  Sku Attribute *****/
    public SkuAttribute createSkuAttribute(SkuAttribute skuAttribute) {
        return skuAttributeRepository.save(skuAttribute);
    }

    public SkuAttribute findSkuAttributeById(long id) {
        return skuAttributeRepository.findById(id).get();
    }

    public SkuAttribute updateSkuAttributeCategory(SkuAttribute skuAttribute) {
        skuAttributeRepository.save(skuAttribute);
        return findSkuAttributeById(skuAttribute.getId());
    }

    public void deleteSkuAttribute(long id) {
        skuAttributeRepository.deleteById(id);
    }
}
