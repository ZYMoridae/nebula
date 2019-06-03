package com.jz.nebula.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.jz.nebula.auth.AuthenticationFacade;
import com.jz.nebula.entity.UserShippingPreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;

import com.jz.nebula.controller.UserPreferenceController;
import com.jz.nebula.dao.UserShippingPreferenceRepository;

import java.util.List;

public class UserShippingPreferenceService {

    @Autowired
    private UserShippingPreferenceRepository userPreferenceRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    public PagedResources<Resource<UserShippingPreference>> findAll(Pageable pageable,
                                                                               PagedResourcesAssembler<UserShippingPreference> assembler) {
        Page<UserShippingPreference> page = userPreferenceRepository.findAll(pageable);

        PagedResources<Resource<UserShippingPreference>> resources = assembler.toResource(page,
                linkTo(UserPreferenceController.class).slash("/user-preferences").withSelfRel());
        ;
        return resources;
    }

    public UserShippingPreference save(UserShippingPreference userPreference) {
        UserShippingPreference updatedUserPreference = userPreferenceRepository.save(userPreference);

        return findById(updatedUserPreference.getId());
    }

    public UserShippingPreference findById(long id) {
        return userPreferenceRepository.findById(id).get();
    }

    public void delete(long id) {
        userPreferenceRepository.deleteById(id);
    }

    /**
     * Find by user id
     *
     * @return
     */
    public List<UserShippingPreference> findByUserId() {
        return userPreferenceRepository.findByUserId(authenticationFacade.getUserId());
    }

}
