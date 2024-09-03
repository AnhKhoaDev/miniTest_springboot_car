package com.codegym.service;

import com.codegym.model.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICarService extends IGenerateService<Car> {
    Page<Car> findAllByNameContaining(Pageable pageable, String name);

    Page<Car> findAll(Pageable pageable);

    Page<Car> findByManufacturerId(Long manufacturerId, Pageable pageable);

    Car findCarById(Long id);
}
