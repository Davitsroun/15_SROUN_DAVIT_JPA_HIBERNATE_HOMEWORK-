package com.example._15_sroun_davit_jpa_hibernate_homework.service.serviceImpl;

import com.example._15_sroun_davit_jpa_hibernate_homework.entity.request.ProductRequest;
import com.example._15_sroun_davit_jpa_hibernate_homework.entity.respone.Item;
import com.example._15_sroun_davit_jpa_hibernate_homework.exception.NotFoundException;
import com.example._15_sroun_davit_jpa_hibernate_homework.model.Product;
import com.example._15_sroun_davit_jpa_hibernate_homework.repository.ProductRepository;
import com.example._15_sroun_davit_jpa_hibernate_homework.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product create(ProductRequest product) {
        return productRepository.creteProduct(product);
    }

    @Override
    public Product getProductById(Integer id) {
        Product product= productRepository.getProductById(id);
        if(product==null){
            throw new NotFoundException("Product with id "+id+" not found");
        }
        return product  ;
    }

    @Override
    public Item getAllProducts(int page, int pageSize) {
        Item product= productRepository.getAllProducts(page,pageSize);

        return product;
    }

    @Override
    public Product UpdateProductById(Integer id, ProductRequest product) {
        getProductById(id);
        return productRepository.UpdateProductById(id,product);
    }

    @Override
    public Void deleteProductById(Integer id) {
        getProductById(id);
        return productRepository.deleteProductById(id);
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        List<Product> product= productRepository.searchProductsByName(name);

        if(product.isEmpty()){
            throw new NotFoundException("Product with name "+ name +" not found");
        }
        return product;
    }

    @Override
    public List<Product> getLowProductsByQuantity(int quantity) {
        List<Product> product= productRepository.getLowProductsByQuantity(quantity);
        return product;
    }
}
