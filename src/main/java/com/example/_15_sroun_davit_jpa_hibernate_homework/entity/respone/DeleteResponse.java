package com.example._15_sroun_davit_jpa_hibernate_homework.entity.respone;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteResponse {


    private String message;
    private Boolean status;
    private Instant instant;
}
