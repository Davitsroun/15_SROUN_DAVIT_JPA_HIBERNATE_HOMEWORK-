package com.example._15_sroun_davit_jpa_hibernate_homework.controller;

import com.example._15_sroun_davit_jpa_hibernate_homework.entity.request.ProductRequest;
import com.example._15_sroun_davit_jpa_hibernate_homework.entity.respone.ApiResponse;
import com.example._15_sroun_davit_jpa_hibernate_homework.entity.respone.DeleteResponse;
import com.example._15_sroun_davit_jpa_hibernate_homework.entity.respone.Item;
import com.example._15_sroun_davit_jpa_hibernate_homework.model.Product;
import com.example._15_sroun_davit_jpa_hibernate_homework.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product",description = "Accepts a product request payload and creates a new product. Returns the created product.")
    public ResponseEntity<ApiResponse<Product>> create(@Valid @RequestBody ProductRequest product) {

        ApiResponse<Product> response = ApiResponse.<Product>builder()
                .message("Product created successfully")
                .success(true)
                .payload(productService.create(product))
                .status(HttpStatus.CREATED)
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID",description = "Fetches a product using its unique ID. Returns 404 if not found." )
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable("id") Integer id  ) {

        ApiResponse<Product> response = ApiResponse.<Product>builder()
                .message("Product get by id successfully")
                .success(true)
                .payload(productService.getProductById(id))
                .status(HttpStatus.OK)
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @Operation(summary = "Get all products (pagination)",description = "Returns a paginated list of all products. Accepts page and size as query parameters." )
    public ResponseEntity<ApiResponse<Item>> getAllProducts(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize ) {

        ApiResponse<Item> response = ApiResponse.<Item>builder()
                .message("Product all products successfully")
                .success(true)
                .payload(productService.getAllProducts(page,pageSize))
                .status(HttpStatus.OK)
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product by ID",description = "FUpdates an existing product with the given ID using the request body. Returns the updated product." )
    public ResponseEntity<ApiResponse<Product>> UpdateProductById(@PathVariable("id") Integer id,
                                                               @Valid  @RequestBody ProductRequest product  ) {

        ApiResponse<Product> response = ApiResponse.<Product>builder()
                .message("Product updated successfully")
                .success(true)
                .payload(productService.UpdateProductById(id,product))
                .status(HttpStatus.OK)
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by ID",description = "Deletes a product by its ID. Returns HTTP 200 if the product is successfully deleted." )
    public ResponseEntity<DeleteResponse> deleteProductById(@PathVariable("id") Integer id  ) {
        productService.deleteProductById(id);
        DeleteResponse deleteResponse= DeleteResponse.builder().message("Product deleted successfully").status(true).instant(Instant.now()).build();
        return ResponseEntity.status(HttpStatus.OK).body(deleteResponse);
    }


    @GetMapping("/search")
    @Operation(summary = "Search products by name",description = "Returns a list of products that contain the given name (case-insensitive partial match)." )
    public ResponseEntity<ApiResponse<List<Product>>> searchProductsByName(@RequestParam String name) {

        ApiResponse<List<Product>> response = ApiResponse.<List<Product>>builder()
                .message("Products matching name '"+name+"' fetched successfully")
                .success(true)
                .payload(productService.searchProductsByName(name))
                .status(HttpStatus.OK)
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Get low stock products ",description = "Returns a list of products with quantity less than the specified threshold." )
    public ResponseEntity<ApiResponse<List<Product>>> getLowProductsByQuantity(@RequestParam @Positive int quantity) {

        ApiResponse<List<Product>> response = ApiResponse.<List<Product>>builder()
                .message("Products with quantity less than "+ quantity+" fetched successfully")
                .success(true)
                .payload(productService.getLowProductsByQuantity(quantity))
                .status(HttpStatus.OK)
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
