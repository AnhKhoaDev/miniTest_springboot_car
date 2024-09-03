package com.codegym.model.dto;

import lombok.Data;

@Data
public class ManufacturerDTO {
    private Long id;
    private String manufacturerName;
    private Long bookCount;
}
