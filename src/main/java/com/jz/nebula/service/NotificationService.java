package com.jz.nebula.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jz.nebula.controller.NotificationController;
import com.jz.nebula.dao.NotificationRepository;
import com.jz.nebula.entity.Notification;

@Service
public class NotificationService {
    private final Logger logger = LogManager.getLogger(NotificationService.class);

    @Autowired
    private NotificationRepository notificationRepository;

    public PagedResources<Resource<Notification>> findAll(Pageable pageable, PagedResourcesAssembler<Notification> assembler) {
        Page<Notification> page = notificationRepository.findAll(pageable);
        PagedResources<Resource<Notification>> resources = assembler.toResource(page,
                linkTo(NotificationController.class).slash("/notifications").withSelfRel());
        return resources;
    }

    public Notification save(Notification notification) {
        Notification existingNotification = null;
        if (notification.getId() != null) {
            existingNotification = findById(notification.getId());
        }
        if (existingNotification != null) {
            existingNotification.setBody(notification.getBody());
        }
        Notification updatedNotification;
        if (existingNotification != null) {
            updatedNotification = notificationRepository.save(existingNotification);
        } else {
            updatedNotification = notificationRepository.save(notification);
        }
        logger.info("saveNotification::notification has been saved");
        return findById(updatedNotification.getId());
    }

    public Notification findById(long id) {
        return notificationRepository.findById(id).get();
    }

    public void delete(long id) {
        notificationRepository.deleteById(id);
    }
}
