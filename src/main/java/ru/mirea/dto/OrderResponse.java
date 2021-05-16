package ru.mirea.dto;


import com.sun.istack.NotNull;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@ToString
public class OrderResponse {
    @NotNull
    private Long id;

    @NotBlank
    private String cakeName;
    @NotBlank
    private String creamName;

    @NotBlank
    private String fillerName;

    @NotBlank
    private String date;
    private String comment;
    private String image;

}
