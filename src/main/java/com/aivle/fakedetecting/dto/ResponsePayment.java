package com.aivle.fakedetecting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePayment {
    private String status;
    private String id;
    private String orderName;
    private Map<String, Integer> amount;
}
