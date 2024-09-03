package com.codegym.repository;

import com.codegym.model.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarRepository extends JpaRepository<Car, Long> {
    Page<Car> findAllByNameContaining(Pageable pageable, String name);

    Page<Car> findAll(Pageable pageable);

    Page<Car> findByManufacturerId(Long manufacturerId, Pageable pageable);
}
