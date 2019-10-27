package com.jz.nebula.controller.cms;

import com.jz.nebula.entity.product.Product;
import com.jz.nebula.entity.sku.Sku;
import com.jz.nebula.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cms/product")
public class CmsProductController extends CmsBaseController {

    @Autowired
    ProductService productService;

    @GetMapping("")
    public String index(Pageable pageable, Model model) {
        model.addAttribute("data", productService.findAll(pageable));
        return "product/index";
    }

    @GetMapping("/{id}/show")
    public String show(@PathVariable("id") long id, Model model) {
        Product product = productService.findById(id);
        logger.debug("show:: sku size [{}]", product.getSkus().size());
        model.addAttribute("product", product);
        return "product/show";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, @ModelAttribute Product product, @RequestParam Map<String,String> allParams) {
        logger.debug("edit:: product id [{}]", product.getId());
        productService.save(product);
        return "redirect:/cms/product/" + id + "/show";
    }
}
