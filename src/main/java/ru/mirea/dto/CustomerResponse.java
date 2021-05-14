package ru.mirea.dto;

import com.sun.istack.NotNull;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.awt.*;
import java.util.List;

@Setter
@ToString
public class CustomerResponse {
    @NotNull
    private Long id;
    @NotBlank
    private String customerName;
    @NotBlank
    private String customerEmail;
    @NotBlank
    private String customerNumber;
    private List<OrderResponse> orders;


}
