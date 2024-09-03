package com.codegym.service;

import com.codegym.model.dto.ICountCar;
import com.codegym.model.entity.Manufacturer;

public interface IManufacturerService extends IGenerateService<Manufacturer> {
    Iterable<ICountCar> getCountCars();
}
