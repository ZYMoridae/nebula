package com.jz.nebula.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;

import com.jz.nebula.controller.UserPreferenceController;
import com.jz.nebula.dao.UserPreferenceRepository;
import com.jz.nebula.entity.UserPreference;

public class UserPreferenceService {

    @Autowired
    private UserPreferenceRepository userPreferenceRepository;

    public PagedResources<Resource<UserPreference>> findAll(Pageable pageable,
                                                            PagedResourcesAssembler<UserPreference> assembler) {
        Page<UserPreference> page = userPreferenceRepository.findAll(pageable);

        PagedResources<Resource<UserPreference>> resources = assembler.toResource(page,
                linkTo(UserPreferenceController.class).slash("/user-preferences").withSelfRel());
        ;
        return resources;
    }

    public UserPreference save(UserPreference userPreference) {
        UserPreference updatedUserPreference = userPreferenceRepository.save(userPreference);

        return findById(updatedUserPreference.getId());
    }

    public UserPreference findById(long id) {
        return userPreferenceRepository.findById(id).get();
    }

    public void delete(long id) {
        userPreferenceRepository.deleteById(id);
    }

    /**
     * Get all user preferences under specific type. e.g. Get all shipping
     * addresses for user.
     *
     * @param userPreferenceTypeId
     * @return
     */
    public List<UserPreference> findByUserPreferenceTypeId(Long userPreferenceTypeId) {
        return userPreferenceRepository.findByUserPreferenceTypeId(userPreferenceTypeId);
    }
}
