package com.jz.nebula.controller.api;

import com.jz.nebula.amqp.MessageProducer;
import com.jz.nebula.entity.order.Order;
import com.jz.nebula.entity.order.OrderLogisticsInfo;
import com.jz.nebula.entity.Role;
import com.jz.nebula.exception.SkuOutOfStockException;
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
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageProducer messageProducer;

    /**
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     * @return
     */
    @GetMapping
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    PagedResources<Resource<Order>> all(Pageable pageable, final UriComponentsBuilder uriBuilder,
                                        final HttpServletResponse response, PagedResourcesAssembler<Order> assembler) {
        return orderService.findAll(pageable, assembler);
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Order findById(@PathVariable("id") long id) {
        return orderService.findById(id);
    }

    /**
     * @return
     */
    @GetMapping("/my")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Order getCurrentActivatedOrder() {
        return orderService.getCurrentActivatedOrder();
    }

    /**
     * @param id
     * @param pageable
     * @param uriBuilder
     * @param response
     * @param assembler
     * @return
     */
    @GetMapping("/users/{id}")
    @RolesAllowed({Role.ROLE_ADMIN})
    public @ResponseBody
    PagedResources<Resource<Order>> all(@PathVariable("id") long id, Pageable pageable, final UriComponentsBuilder uriBuilder,
                                        final HttpServletResponse response, PagedResourcesAssembler<Order> assembler) {
        return orderService.findByUserId(id, pageable, assembler);
    }

    /**
     * @param order
     * @return
     */
    @PostMapping("")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Order create(@RequestBody Order order) throws SkuOutOfStockException {
        Order savedOrder =  orderService.save(order);
        return orderService.findById(savedOrder.getId());
    }

    /**
     * @param id
     * @param order
     * @return
     */
    @PutMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Order update(@PathVariable("id") long id, @RequestBody Order order) throws SkuOutOfStockException {
        order.setId(id);
        return orderService.save(order);
    }

    /**
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    ResponseEntity<?> delete(@PathVariable("id") long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * @param orderLogisticsInfo
     * @return
     */
    @PostMapping("/{id}/logistics")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    OrderLogisticsInfo createOrderLogisticsInfo(@PathVariable("id") long id, @RequestBody OrderLogisticsInfo orderLogisticsInfo) {
        orderLogisticsInfo.setOrdersId(id);
        return orderService.saveLogisticsInfo(orderLogisticsInfo);
    }


    /**
     * @param message
     */
    @PostMapping(value = "/messages")
    public void sendMessage(@RequestBody String message) {
        messageProducer.sendMessage(message);
    }

}
