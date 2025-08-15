package com.example._15_sroun_davit_jpa_hibernate_homework.controller;

import com.example._15_sroun_davit_jpa_hibernate_homework.entity.request.ProductRequest;
import com.example._15_sroun_davit_jpa_hibernate_homework.entity.respone.ApiResponse;
import com.example._15_sroun_davit_jpa_hibernate_homework.model.Product;
import com.example._15_sroun_davit_jpa_hibernate_homework.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.doNothing;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    // ------------------------------
    // 1️⃣ Success scenario
    // ------------------------------
    @Test
    void createProduct_success() throws Exception {
        ProductRequest request = ProductRequest.builder()
                .name("Laptop")
                .price(new BigDecimal("1200.00"))
                .quantity(5)
                .build();

        Product mockProduct = Product.builder()
                .id(1L)
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        when(productService.create(request)).thenReturn(mockProduct);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Product created successfully"))
                .andExpect(jsonPath("$.payload.id").value(1))
                .andExpect(jsonPath("$.payload.name").value("Laptop"))
                .andExpect(jsonPath("$.payload.price").value(1200.00))
                .andExpect(jsonPath("$.payload.quantity").value(5))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    // ------------------------------
    // 2️⃣ Validation error scenario
    // ------------------------------
    @Test
    void createProduct_validationError() throws Exception {
        ProductRequest request = ProductRequest.builder()
                .name("") // invalid (NotBlank + Size)
                .price(new BigDecimal("-100.00")) // invalid (PositiveOrZero)
                .quantity(-5) // invalid (PositiveOrZero)
                .build();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())           // just check errors exist
                .andExpect(jsonPath("$.errors.name").exists())      // check specific field error
                .andExpect(jsonPath("$.errors.price").exists())
                .andExpect(jsonPath("$.errors.quantity").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }


    // ------------------------------
    // 3️⃣ Service exception scenario
    // ------------------------------
    @Test
    void createProduct_serviceException() throws Exception {
        ProductRequest request = ProductRequest.builder()
                .name("Laptop")
                .price(new BigDecimal("1200.00"))
                .quantity(5)
                .build();

        when(productService.create(request)).thenThrow(new RuntimeException("DB failure"));

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Fail: Failed to process request now. Try again later"));
    }


    // ------------------------------
    // Successful delete
    // ------------------------------
    @Test
    void deleteProduct_success() throws Exception {
        int productId = 1;

        // Mock service to do nothing
        doNothing().when(productService).deleteProductById(productId);

        mockMvc.perform(delete("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Product deleted successfully"))
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.instant").exists());
    }
    @Test
    void getProductById_success() throws Exception {
        int productId = 1;

        Product mockProduct = Product.builder()
                .id((long) productId)
                .name("bby")
                .price(BigDecimal.ZERO)
                .quantity(10)
                .build();
        when(productService.getProductById(productId)).thenReturn(mockProduct);

        mockMvc.perform(get("/api/v1/products/{id}", productId)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Product get by id successfully"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.payload.id").value(mockProduct.getId()))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    //not use json
    @Test
    void create_product_not_usejson()throws  Exception {

        ProductRequest request = ProductRequest.builder()
                .name("Laptop")
                .price(new BigDecimal("1200.00"))
                .quantity(5)
                .build();

        Product mockProduct = Product.builder()
                .id(Long.valueOf(1))
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        when(productService.create(request)).thenReturn(mockProduct);

      String value=  mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockProduct)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ApiResponse<Product> response= objectMapper.readValue(value,
                objectMapper.getTypeFactory().constructParametricType(ApiResponse.class,Product.class));

            assertTrue(response.isSuccess());
            assertEquals("Product created successfully", response.getMessage());
            assertEquals(Long.valueOf(1),response.getPayload().getId()  );
            assertEquals("Laptop", response.getPayload().getName());
    }

}
