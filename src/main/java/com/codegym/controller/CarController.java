package com.codegym.controller;

import com.codegym.model.dto.CarDTO;
import com.codegym.model.entity.Car;
import com.codegym.model.entity.Manufacturer;
import com.codegym.repository.ICarRepository;
import com.codegym.service.ICarService;
import com.codegym.service.IManufacturerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/cars")
public class CarController {
    private final ICarService carService;
    private final IManufacturerService manufacturerService;
    private final ICarRepository carRepository;

    public CarController(ICarService carService, IManufacturerService manufacturerService, ICarRepository carRepository) {
        this.carService = carService;
        this.manufacturerService = manufacturerService;
        this.carRepository = carRepository;
    }

    @Value("${file-upload}")
    private String upload;

    @ModelAttribute("manufacturers")
    public Iterable<Manufacturer> listManufacturers() {
        return manufacturerService.findAll();
    }

    @GetMapping
    public String listCars(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "") String search,
                           Model model) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Car> bookPage = (search != null && !search.isEmpty())
                ? carService.findAllByNameContaining(pageable, search)
                : carService.findAll(pageable);

        model.addAttribute("page", bookPage);
        model.addAttribute("cars", bookPage.getContent());
        model.addAttribute("search", search);

        return "views/car/index";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model) {
        model.addAttribute("car", new CarDTO());
        return "views/car/create";
    }

    @PostMapping("/create")
    public String createCar(@ModelAttribute("car") CarDTO carDTO,
                            RedirectAttributes redirectAttributes) {
        MultipartFile file = carDTO.getImg();
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(upload + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Car car = new Car();
        car.setId(carDTO.getId());
        car.setCode(carDTO.getCode());
        car.setName(carDTO.getName());
        car.setProducer(carDTO.getProducer());
        car.setPrice(carDTO.getPrice());
        car.setDescription(carDTO.getDescription());
        car.setImg(fileName);
        car.setManufacturer(carDTO.getManufacturer());
        carService.save(car);
        redirectAttributes.addFlashAttribute("message", "Car saved successfully");
        return "redirect:/cars";
    }

    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable Long id, Model model) {
        Optional<Car> car = carService.findById(id);
        if (car.isPresent()) {
            model.addAttribute("car", car.get());
            return "views/car/detail";
        } else {
            return "views/error_404";
        }
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable Long id, Model model) {
        Optional<Car> car = carService.findById(id);
        if (car.isPresent()) {
            model.addAttribute("car", car.get());
            return "views/car/update";
        } else {
            return "views/error_404";
        }
    }

    @PostMapping("/update")
    public String updateCar(@ModelAttribute("car") CarDTO carDTO,
                            RedirectAttributes redirectAttributes) {
        Car car = carService.findCarById(carDTO.getId());

        MultipartFile file = carDTO.getImg();
        String fileName = file.getOriginalFilename();
        if (!file.isEmpty()) {
            try {
                FileCopyUtils.copy(file.getBytes(), new File(upload + fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            fileName = car.getImg();
        }

        car.setId(carDTO.getId());
        car.setCode(carDTO.getCode());
        car.setName(carDTO.getName());
        car.setProducer(carDTO.getProducer());
        car.setPrice(carDTO.getPrice());
        car.setDescription(carDTO.getDescription());
        car.setImg(fileName);
        car.setManufacturer(carDTO.getManufacturer());
        carService.save(car);
        redirectAttributes.addFlashAttribute("message", "Car updated successfully");
        return "redirect:/cars";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteForm(@PathVariable Long id, Model model) {
        Optional<Car> car = carService.findById(id);
        if (car.isPresent()) {
            model.addAttribute("car", car.get());
            return "views/car/delete";
        } else {
            return "views/error_404";
        }
    }

    @PostMapping("/delete")
    public String deleteCar(@ModelAttribute("car") Car car) {
        carService.remove(car.getId());
        return "redirect:/cars";
    }

    @GetMapping("/manufacturers")
    public String searchByManufacturer(@RequestParam("manufacturerId") Long manufacturerId,
                                       @PageableDefault(size = 5) Pageable pageable,
                                       Model model) {
        Page<Car> cars = carService.findByManufacturerId(manufacturerId, pageable);
        model.addAttribute("cars", cars);
        return "views/car/index";
    }

    @PostMapping("/search")
    public String findManufacturerByName(@RequestParam("search") Optional<String> search,
                                         @PageableDefault(size = 5) Pageable pageable,
                                         Model model) {
        Page<Car> cars = search.map(s -> carService.findAllByNameContaining(pageable, s))
                .orElseGet(() -> carRepository.findAll(pageable));

        model.addAttribute("page", cars);
        model.addAttribute("cars", cars.getContent());
        model.addAttribute("search", search.orElse(""));
        return "views/car/index";
    }

}
