package com.example._15_sroun_davit_jpa_hibernate_homework.repository;

import com.example._15_sroun_davit_jpa_hibernate_homework.entity.request.ProductRequest;
import com.example._15_sroun_davit_jpa_hibernate_homework.entity.respone.Item;
import com.example._15_sroun_davit_jpa_hibernate_homework.entity.respone.Pagination;
import com.example._15_sroun_davit_jpa_hibernate_homework.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class ProductRepository  {


    private final EntityManager em;

    public Product creteProduct(ProductRequest request){
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
        em.persist(product);
    return product;
    }

    public Product getProductById(Integer id) {

      return  em.find(Product.class, id);
    }

    public Item getAllProducts(int page, int pageSize) {
        int offset = (page - 1) * pageSize;

        List<Product> products = em
                .createQuery("SELECT p FROM Product p", Product.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();

        long totalElements = em
                .createQuery("SELECT COUNT(p) FROM Product p", Long.class)
                .getSingleResult();

        Pagination pagination = Pagination.builder()
                .totalElements(totalElements)
                .currentPage(page)
                .pageSize(pageSize)
                .totalPages((int) Math.ceil((double) totalElements / pageSize))
                .build();
       return Item.builder().items(products).pagination(pagination).build();
    }

    public Product UpdateProductById(Integer id, ProductRequest product) {
        Product product1 = em.find(Product.class, id);
        product1.setName(product.getName());
        product1.setPrice(product.getPrice());
        product1.setQuantity(product.getQuantity());
         em.detach(product1);
         em.merge(product1);
        return product1;
    }

    public Void deleteProductById(Integer id) {
        Product product = em.find(Product.class, id);
        em.remove(product);
        return null;
    }

    public List<Product> searchProductsByName(String name) {
        List<Product> query= em.createQuery("SELECT s FROM Product s WHERE s.name LIKE :name",Product.class)
                .setParameter("name","%"+name+"%")
                .getResultList();
      return  query;

    }

    public List<Product> getLowProductsByQuantity(int quantity) {
        List<Product> list= em.createQuery("SELECT  s FROM Product s WHERE s.quantity < :quantity", Product.class)
                .setParameter("quantity",quantity)
                .getResultList();
        return list;
    }
}
