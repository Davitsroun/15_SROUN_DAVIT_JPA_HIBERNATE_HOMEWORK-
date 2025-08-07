package com.example._15_sroun_davit_jpa_hibernate_homework.service;

import com.example._15_sroun_davit_jpa_hibernate_homework.entity.request.ProductRequest;
import com.example._15_sroun_davit_jpa_hibernate_homework.entity.respone.Item;
import com.example._15_sroun_davit_jpa_hibernate_homework.model.Product;
import jakarta.validation.Valid;

import java.util.List;


public interface ProductService {
    Product create(ProductRequest product);

    Product getProductById(Integer id);

    Item getAllProducts(int page, int pageSize);

    Product UpdateProductById(Integer id,  ProductRequest product);

    Void deleteProductById(Integer id);

    List<Product> searchProductsByName(String name);

    List<Product> getLowProductsByQuantity(int quantity);
}
