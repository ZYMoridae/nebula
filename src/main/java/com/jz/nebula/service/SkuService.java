package com.jz.nebula.service;

import com.google.common.base.Strings;
import com.jz.nebula.controller.api.SKUController;
import com.jz.nebula.dao.sku.SkuAttributeCategoryRepository;
import com.jz.nebula.dao.sku.SkuAttributeRepository;
import com.jz.nebula.dao.sku.SkuRepository;
import com.jz.nebula.entity.sku.Sku;
import com.jz.nebula.entity.sku.SkuAttribute;
import com.jz.nebula.entity.sku.SkuAttributeCategory;
import com.jz.nebula.util.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Service
@Transactional
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
    @Transactional(rollbackFor = {Exception.class})
    public Sku create(Sku sku) {
        if(sku.getSkuCode() == null) {
            sku.setSkuCode(skuCodeGenerator(sku));
        }
        return skuRepository.save(sku);
    }

    /**
     * @param sku
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
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
    public String skuCodeGenerator(Sku sku) {
        String skuCode = "";

        Long currentTime = System.currentTimeMillis();
        skuCode = skuCodePrefix + Security.generateHash(currentTime + String.valueOf(sku.getProductId()) + Security.getRandomHash());

        return skuCode;
    }

    /***** Sku Attribute Category *****/
    public PagedResources<Resource<SkuAttributeCategory>> findAllSkuAttributeCategory(String keyword, Pageable pageable, PagedResourcesAssembler<SkuAttributeCategory> assembler) {
        Page<SkuAttributeCategory> page;
        if (Strings.isNullOrEmpty(keyword)) {
            page = skuAttributeCategoryRepository.findAll(pageable);
        } else {
            page = skuAttributeCategoryRepository.findByNameContaining(keyword, pageable);
        }

        PagedResources<Resource<SkuAttributeCategory>> resources = assembler.toResource(page,
                linkTo(SKUController.class).slash("/api/skus/attributes/categories").withSelfRel());

        return resources;
    }

    @Transactional(rollbackFor = {Exception.class})
    public SkuAttributeCategory createSkuAttributeCategory(SkuAttributeCategory skuAttributeCategory) {
        return skuAttributeCategoryRepository.save(skuAttributeCategory);
    }

    public SkuAttributeCategory findSkuAttributeCategoryById(long id) {
        return skuAttributeCategoryRepository.findById(id).get();
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized SkuAttributeCategory updateSkuAttributeCategory(SkuAttributeCategory skuAttributeCategory) {
        skuAttributeCategoryRepository.save(skuAttributeCategory);
        return findSkuAttributeCategoryById(skuAttributeCategory.getId());
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteSkuAttributeCategory(long id) {
        skuAttributeCategoryRepository.deleteById(id);
    }

    /*****  Sku Attribute *****/
    @Transactional(rollbackFor = {Exception.class})
    public SkuAttribute createSkuAttribute(SkuAttribute skuAttribute) {
        return skuAttributeRepository.save(skuAttribute);
    }

    public SkuAttribute findSkuAttributeById(long id) {
        return skuAttributeRepository.findById(id).get();
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized SkuAttribute updateSkuAttributeCategory(SkuAttribute skuAttribute) {
        skuAttributeRepository.save(skuAttribute);
        return findSkuAttributeById(skuAttribute.getId());
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteSkuAttribute(long id) {
        skuAttributeRepository.deleteById(id);
    }
}
