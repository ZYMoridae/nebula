package com.jz.nebula.controller;

import com.jz.nebula.amqp.MessageProducer;
import com.jz.nebula.entity.Order;
import com.jz.nebula.entity.Role;
import com.jz.nebula.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

//import com.jz.nebula.amqp.MessageProducer;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageProducer messageProducer;

    @GetMapping
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    PagedResources<Resource<Order>> all(Pageable pageable, final UriComponentsBuilder uriBuilder,
                                        final HttpServletResponse response, PagedResourcesAssembler<Order> assembler) {
        return orderService.findAll(pageable, assembler);
    }

    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Order findById(@PathVariable("id") long id) {
        return orderService.findById(id);
    }

    @GetMapping("/my")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Order getCurrentActivatedOrder() {
        return orderService.getCurrentActivatedOrder();
    }

    @GetMapping("/users/{id}")
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    PagedResources<Resource<Order>> all(@PathVariable("id") long id, Pageable pageable, final UriComponentsBuilder uriBuilder,
                                        final HttpServletResponse response, PagedResourcesAssembler<Order> assembler) {
        return orderService.findByUserId(id, pageable, assembler);
    }

    @PostMapping("")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Order create(@RequestBody Order order) {
        return orderService.save(order);
    }

    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Order update(@PathVariable("id") long id, @RequestBody Order order) {
        order.setId(id);
        return orderService.save(order);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("id") long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/messages")
    public void sendMessage(@RequestBody String message) {
        messageProducer.sendMessage(message);
    }

}
