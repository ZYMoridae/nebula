package com.jz.nebula.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.jz.nebula.dao.LogisticsProviderRepository;
import com.jz.nebula.entity.LogisticsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.jz.nebula.controller.api.LogisticsProviderController;

@Service
public class LogisticsProviderService {

    @Autowired
    private LogisticsProviderRepository logisticsProviderRepository;

    public PagedModel<EntityModel<LogisticsProvider>> findAll(Pageable pageable, PagedResourcesAssembler<LogisticsProvider> assembler) {
        Page<LogisticsProvider> page = logisticsProviderRepository.findAll(pageable);
        PagedModel<EntityModel<LogisticsProvider>> resources = assembler.toModel(page,
                linkTo(LogisticsProviderController.class).slash("/logistic-providers").withSelfRel());
        ;
        return resources;
    }

    public LogisticsProvider save(LogisticsProvider logisticsProvider) {
        return logisticsProviderRepository.save(logisticsProvider);
    }

    public LogisticsProvider findById(long id) {
        return logisticsProviderRepository.findById(id).get();
    }

    public void delete(long id) {
        logisticsProviderRepository.deleteById(id);
    }

}
