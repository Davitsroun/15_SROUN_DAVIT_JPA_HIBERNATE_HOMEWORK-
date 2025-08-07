package com.example._15_sroun_davit_jpa_hibernate_homework.entity.respone;

import com.example._15_sroun_davit_jpa_hibernate_homework.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Item {
    private List<Product>  items;
    private Pagination pagination;
}
