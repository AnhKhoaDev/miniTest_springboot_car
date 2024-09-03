package com.codegym.controller;

import com.codegym.model.dto.ICountCar;
import com.codegym.model.entity.Manufacturer;
import com.codegym.service.ICarService;
import com.codegym.service.IManufacturerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {
    private final IManufacturerService manufacturerService;
    private final ICarService carService;

    public ManufacturerController(IManufacturerService manufacturerService, ICarService carService) {
        this.manufacturerService = manufacturerService;
        this.carService = carService;
    }

    @GetMapping
    private ModelAndView listManufacturer() {
        ModelAndView modelAndView = new ModelAndView("views/manufacturer/index");
        Iterable<Manufacturer> manufacturers = manufacturerService.findAll();
        modelAndView.addObject("manufacturers", manufacturers);
        Iterable<ICountCar> manufacturer = manufacturerService.getCountCars();
        modelAndView.addObject("manufacturer", manufacturer);
        return modelAndView;
    }


    @GetMapping("/create")
    public ModelAndView getCreateForm() {
        ModelAndView modelAndView = new ModelAndView("views/manufacturer/create");
        modelAndView.addObject("manufacturer", new Manufacturer());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createManufacturer(@ModelAttribute("manufacturer") Manufacturer manufacturer) {
        manufacturerService.save(manufacturer);
        ModelAndView modelAndView = new ModelAndView("views/manufacturer/create");
        modelAndView.addObject("manufacturer", new Manufacturer());
        modelAndView.addObject("message", "New manufacturer created successfully");
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView getUpdateForm(@PathVariable Long id) {
        Optional<Manufacturer> manufacturer = manufacturerService.findById(id);
        if (manufacturer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("views/manufacturer/update");
            modelAndView.addObject("manufacturer", manufacturer.get());
            return modelAndView;
        } else {
            return new ModelAndView("views/error_404");
        }
    }

    @PostMapping("/update")
    public ModelAndView updateManufacturer(@ModelAttribute("manufacturer") Manufacturer manufacturer) {
        manufacturerService.save(manufacturer);
        ModelAndView modelAndView = new ModelAndView("views/manufacturer/update");
        modelAndView.addObject("manufacturer", manufacturer);
        modelAndView.addObject("message", "Manufacturer updated successful");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView getDeleteForm(@PathVariable Long id) {
        Optional<Manufacturer> manufacturer = manufacturerService.findById(id);
        if (manufacturer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("views/manufacturer/delete");
            modelAndView.addObject("manufacturer", manufacturer.get());
            return modelAndView;
        } else {
            return new ModelAndView("views/error_404");
        }
    }

    @PostMapping("/delete")
    public String deleteType(@ModelAttribute("Manufacturer") Manufacturer manufacturer) {
        manufacturerService.remove(manufacturer.getId());
        return "redirect:/manufacturers";
    }

    @GetMapping("/demo")
    public ModelAndView getDemo() {
        ModelAndView modelAndView = new ModelAndView("views/manufacturer/index");
        Iterable<ICountCar> manufacturers = manufacturerService.getCountCars();
        modelAndView.addObject("manufacturers", manufacturers);
        return modelAndView;
    }
}
