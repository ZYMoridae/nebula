package com.jz.nebula.service;

import com.jz.nebula.controller.api.NotificationController;
import com.jz.nebula.dao.HomeBannerRepository;
import com.jz.nebula.entity.HomeBanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class HomeBannerService {
    private final Logger logger = LogManager.getLogger(HomeBanner.class);

    @Autowired
    private HomeBannerRepository homeBannerRepository;

    public PagedModel<EntityModel<HomeBanner>> findAll(Pageable pageable, PagedResourcesAssembler<HomeBanner> assembler) {
        Page<HomeBanner> page = homeBannerRepository.findAll(pageable);
        PagedModel<EntityModel<HomeBanner>> resources = assembler.toModel(page,
                linkTo(NotificationController.class).slash("/home-banners").withSelfRel());
        return resources;
    }

    public List<HomeBanner> findAllActive() {
        return homeBannerRepository.findByActive(true);
    }

    public HomeBanner save(HomeBanner homeBanner) {
        return homeBannerRepository.save(homeBanner);
    }

    public HomeBanner findById(long id) {
        return homeBannerRepository.findById(id).get();
    }

    public void delete(long id) {
        homeBannerRepository.deleteById(id);
    }

}
