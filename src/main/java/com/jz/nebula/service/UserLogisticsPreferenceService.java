package com.jz.nebula.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.jz.nebula.util.auth.AuthenticationFacadeImpl;
import com.jz.nebula.entity.UserLogisticsPreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import com.jz.nebula.controller.api.UserPreferenceController;
import com.jz.nebula.dao.UserLogisticsPreferenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLogisticsPreferenceService {

    @Autowired
    private UserLogisticsPreferenceRepository userPreferenceRepository;

    @Autowired
    private AuthenticationFacadeImpl authenticationFacadeImpl;

    public PagedModel<EntityModel<UserLogisticsPreference>> findAll(Pageable pageable,
                                                                    PagedResourcesAssembler<UserLogisticsPreference> assembler) {
        Page<UserLogisticsPreference> page = userPreferenceRepository.findAll(pageable);

        PagedModel<EntityModel<UserLogisticsPreference>> resources = assembler.toModel(page,
                linkTo(UserPreferenceController.class).slash("/user-preferences").withSelfRel());
        ;
        return resources;
    }

    public UserLogisticsPreference save(UserLogisticsPreference userPreference) {
        UserLogisticsPreference updatedUserPreference = userPreferenceRepository.save(userPreference);

        return findById(updatedUserPreference.getId());
    }

    public UserLogisticsPreference findById(long id) {
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
    public List<UserLogisticsPreference> findByUserId() {
        return userPreferenceRepository.findByUserId(authenticationFacadeImpl.getUserId());
    }

}
