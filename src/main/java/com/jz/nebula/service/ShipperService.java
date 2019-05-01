package com.jz.nebula.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jz.nebula.controller.ShipperController;
import com.jz.nebula.dao.ShipperRepository;
import com.jz.nebula.entity.Shipper;

@Service
public class ShipperService {

    @Autowired
    private ShipperRepository shipperRepository;

    public PagedResources<Resource<Shipper>> findAll(Pageable pageable, PagedResourcesAssembler<Shipper> assembler) {
        Page<Shipper> page = shipperRepository.findAll(pageable);
        PagedResources<Resource<Shipper>> resources = assembler.toResource(page,
                linkTo(ShipperController.class).slash("/products").withSelfRel());
        ;
        return resources;
    }

    public Shipper save(Shipper shipper) {
        return shipperRepository.save(shipper);
    }

    public Shipper findById(long id) {
        return shipperRepository.findById(id).get();
    }

    public void delete(long id) {
        shipperRepository.deleteById(id);
    }

}
