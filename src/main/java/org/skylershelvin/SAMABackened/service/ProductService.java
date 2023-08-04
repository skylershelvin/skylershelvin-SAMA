package org.skylershelvin.SAMABackened.service;

import org.skylershelvin.SAMABackened.model.Product;
import org.skylershelvin.SAMABackened.model.dao.ProductDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getProducts(){
        return productDAO.findAll();
    }
}
