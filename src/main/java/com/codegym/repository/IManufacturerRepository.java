package com.codegym.repository;

import com.codegym.model.dto.ICountCar;
import com.codegym.model.entity.Manufacturer;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    Page<Manufacturer> findAll(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT manufacturers.name, count(cars.id) as number FROM manufacturers LEFT JOIN cars on manufacturers.id = manufacturer_id GROUP BY manufacturers.name;")
    Iterable<ICountCar> getCountCars();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "call dsp_delete_manufacturer(:id) ")
    void deleteManufacturerById(@Param("id") Long id);
}
