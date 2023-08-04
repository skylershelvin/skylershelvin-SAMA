package org.skylershelvin.SAMABackened.API.controller.product;

import org.skylershelvin.SAMABackened.model.Product;
import org.skylershelvin.SAMABackened.service.ProductService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Component
@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // gets a list of all products available returns list of products
    @GetMapping
    public List<Product> getProduct(){
        return productService.getProducts();
    }
}
