package com.jz.nebula.controller.cms;

import com.jz.nebula.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class CmsProductController extends CmsBaseController {

    @Autowired
    ProductService productService;

    @GetMapping("")
    public String index(Pageable pageable, Model model) {
        model.addAttribute("data", productService.findAll(pageable));
        return "product/index";
    }
}
