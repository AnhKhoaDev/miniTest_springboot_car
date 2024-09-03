package com.codegym.service.impl;

import com.codegym.model.dto.ICountCar;
import com.codegym.model.entity.Manufacturer;
import com.codegym.repository.IManufacturerRepository;
import com.codegym.service.IManufacturerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManufacturerService implements IManufacturerService {

    private final IManufacturerRepository manufacturerRepository;

    public ManufacturerService(IManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public Iterable<ICountCar> getCountCars() {
        return manufacturerRepository.getCountCars();
    }

    @Override
    public Page<Manufacturer> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Iterable<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    @Override
    public Optional<Manufacturer> findById(Long id) {
        return manufacturerRepository.findById(id);
    }

    @Override
    public void save(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
    }

    @Override
    public void remove(Long id) {
        manufacturerRepository.deleteById(id);
    }
}
