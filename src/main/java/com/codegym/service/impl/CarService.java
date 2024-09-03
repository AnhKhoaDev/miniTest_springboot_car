package com.codegym.service.impl;

import com.codegym.model.entity.Car;
import com.codegym.repository.ICarRepository;
import com.codegym.service.ICarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarService implements ICarService {

    private final ICarRepository carRepository;

    public CarService(ICarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Page<Car> findAllByNameContaining(Pageable pageable, String name) {
        return carRepository.findAllByNameContaining(pageable, name);
    }

    @Override
    public Page<Car> findAll(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    @Override
    public Iterable<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public void save(Car car) {
        carRepository.save(car);
    }

    @Override
    public void remove(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public Page<Car> findByManufacturerId(Long manufacturerId, Pageable pageable) {
        return carRepository.findByManufacturerId(manufacturerId, pageable);
    }

    @Override
    public Car findCarById(Long id) {
        return carRepository.findById(id).get();
    }
}
