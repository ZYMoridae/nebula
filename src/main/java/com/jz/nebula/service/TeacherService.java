package com.jz.nebula.service;

import com.jz.nebula.auth.IAuthenticationFacade;
import com.jz.nebula.dao.TeacherMetaRepository;
import com.jz.nebula.dao.TeacherRepository;
import com.jz.nebula.dao.TeacherSubscriptionRepository;
import com.jz.nebula.entity.teacher.Teacher;
import com.jz.nebula.entity.teacher.TeacherMeta;
import com.jz.nebula.entity.teacher.TeacherSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
public class TeacherService {
    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherMetaRepository teacherMetaRepository;

    @Autowired
    private TeacherSubscriptionRepository teacherSubscriptionRepository;

    /**
     * Find teacher by id
     *
     * @param id
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
            resultSet.put("introduction", teacherMeta.getIntro());
            resultSet.put("countryCode", teacherMeta.getCountryCode());
            resultSet.put("avatar", teacherMeta.getAvatar());
        }

        return resultSet;
    }

    /**
     * Find meta by teacher id
     *
     * @param id
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
