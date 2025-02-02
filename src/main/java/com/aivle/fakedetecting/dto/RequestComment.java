package com.aivle.fakedetecting.dto;


import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestComment {
    private Long board_id;
    private String content;
}
