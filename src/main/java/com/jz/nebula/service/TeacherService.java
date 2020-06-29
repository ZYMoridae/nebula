package com.jz.nebula.service;

import com.google.common.base.Strings;
import com.jz.nebula.util.auth.AuthenticationFacade;
import com.jz.nebula.controller.api.UserController;
import com.jz.nebula.dao.TeacherMetaRepository;
import com.jz.nebula.dao.TeacherRepository;
import com.jz.nebula.dao.TeacherSubscriptionRepository;
import com.jz.nebula.entity.teacher.Teacher;
import com.jz.nebula.entity.teacher.TeacherMeta;
import com.jz.nebula.entity.teacher.TeacherSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class TeacherService {
    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherMetaRepository teacherMetaRepository;

    @Autowired
    private TeacherSubscriptionRepository teacherSubscriptionRepository;

    /**
     * Find all teachers
     *
     * @param keyword
     * @param pageable
     * @param assembler
     *
     * @return
     */
    public PagedModel<EntityModel<Teacher>> findAll(String keyword, Pageable pageable,
                                                    PagedResourcesAssembler<Teacher> assembler) {
        Page<Teacher> page;
        if (Strings.isNullOrEmpty(keyword)) {
            page = teacherRepository.findAllByOrderByIdAsc(pageable);
        } else {
            page = teacherRepository.findByNameContaining(keyword, pageable);
        }

        // Loop each teacher item and find the teacher meta
        Iterator<Teacher> iterator = page.iterator();
        while (iterator.hasNext()) {
            Teacher teacher = iterator.next();

            Optional<TeacherMeta> teacherMeta = teacherMetaRepository.findByUserId(teacher.getId());

            logger.debug("findAll:: teacher meta id [{}]", teacherMeta.isPresent() ? teacherMeta.get().getId() : -1);
            teacher.setTeacherMeta(teacherMeta.isPresent() ? teacherMeta.get() : null);
        }

        PagedModel<EntityModel<Teacher>> resources = assembler.toModel(page,
                linkTo(UserController.class).slash("/users").withSelfRel());

        return resources;
    }

    /**
     * Find teacher by id
     *
     * @param id
     *
     * @return
     */
    public HashMap<String, Object> findById(long id) {
        Teacher teacher = teacherRepository.findById(id).get();
        TeacherMeta teacherMeta = teacherMetaRepository.findByUserId(teacher.getId()).get();
        HashMap<String, Object> resultSet = new HashMap<>();

        if (!Objects.isNull(teacher) && !Objects.isNull(teacherMeta)) {
            resultSet.put("username", teacher.getUsername());
            resultSet.put("email", teacher.getEmail());
            resultSet.put("telephone", teacher.getTelephone());
            resultSet.put("firstname", teacher.getFirstname());
            resultSet.put("lastname", teacher.getLastname());
            resultSet.put("gender", teacher.getGender());
            resultSet.put("teacherMeta", teacherMeta);
        }

        return resultSet;
    }

    /**
     * Find meta by teacher id
     *
     * @param id
     *
     * @return
     */
    public TeacherMeta findMetaByTeacherId(long id) {
        Optional<TeacherMeta> teacherMeta = teacherMetaRepository.findByUserId(id);
        return teacherMeta.isPresent() ? teacherMeta.get() : null;
    }

    /**
     * Create teacher meta
     *
     * @param teacherMeta
     *
     * @return
     */
    public TeacherMeta saveTeacherMeta(long id, TeacherMeta teacherMeta) throws Exception {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        TeacherMeta savedTeacherMeta;
        if (teacher.isPresent()) {
            teacherMeta.setUserId(id);
            savedTeacherMeta = teacherMetaRepository.save(teacherMeta);
        } else {
            logger.debug("saveTeacherMeta::teacher with user id [{}] was not found", id);
            throw new Exception("Teacher was not found!");
        }


        return savedTeacherMeta;
    }

    /**
     * Delete by user id
     *
     * @param id
     */
    public void deleteTeacherMeta(long id) {

        TeacherMeta teacherMeta = this.findMetaByTeacherId(id);

        if (!Objects.isNull(teacherMeta)) {
            teacherMetaRepository.delete(teacherMeta);
        } else {
            logger.debug("deleteTeacherMeta::teacher with user id [{}] was not found", id);
        }
    }

    /**
     * TODO: Subscribe teacher, user id and teacher id validation. Notification email should be sent to user.
     *
     * @param teacherSubscription
     */
    public TeacherSubscription subscribeTeacher(TeacherSubscription teacherSubscription) {
        teacherSubscription.setUserId(authenticationFacade.getUserId());
        return teacherSubscriptionRepository.save(teacherSubscription);
    }

    /**
     * TODO: Unsubscribe teacher, user id and teacher id validation. Notification email should be sent to user.
     *
     * @param teacherId
     */
    public void unsubscribeTeacher(long teacherId) {
        TeacherSubscription teacherSubscription = teacherSubscriptionRepository.findByTeacherIdAndUserId(teacherId, authenticationFacade.getUserId()).get();
        teacherSubscriptionRepository.delete(teacherSubscription);
    }
}
