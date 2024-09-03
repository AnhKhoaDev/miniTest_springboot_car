package com.codegym.model.dto;

import com.codegym.model.entity.Manufacturer;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CarDTO {
    private Long id;
    private String code;
    private String name;
    private String producer;
    private double price;
    private String description;
    private MultipartFile img;

    private Manufacturer manufacturer;
}
