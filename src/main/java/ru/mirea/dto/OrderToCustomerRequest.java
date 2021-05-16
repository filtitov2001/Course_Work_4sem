package ru.mirea.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@AllArgsConstructor
public class OrderToCustomerRequest {
    @NotBlank
    private final String orderCakeName;
    @NotBlank
    private final String orderCreamName;
    @NotBlank
    private final String orderFillerName;
    @NotBlank
    private final String orderDate;
    private final String orderComment;
    private final String orderImage;
    @NotBlank
    private final String customerName;
    @NotBlank
    private final String customerEmail;
    @NotBlank
    private final String customerNumber;



}
