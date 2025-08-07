package com.example._15_sroun_davit_jpa_hibernate_homework.entity.respone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagination {
    private long totalElements;
    private int currentPage;
    private int pageSize;
    private int totalPages;
}
