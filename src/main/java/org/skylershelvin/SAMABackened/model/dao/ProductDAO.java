package org.skylershelvin.SAMABackened.model.dao;

import org.skylershelvin.SAMABackened.model.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductDAO extends ListCrudRepository<Product, Long> {
}
