package com.codegym.formatter;

import com.codegym.model.entity.Manufacturer;
import com.codegym.service.IManufacturerService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

@Component
public class ManufacturerFormatter implements Formatter<Manufacturer> {
    private final IManufacturerService manufacturerService;

    public ManufacturerFormatter(IManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @Override
    public Manufacturer parse(String text, Locale locale) throws ParseException {
        try {
            Long id = Long.parseLong(text);
            Optional<Manufacturer> typeOptional = manufacturerService.findById(id);
            return typeOptional.orElseThrow(() -> new ParseException("Manufacturer not found for id: " + id, 0));
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid id format: " + text, 0);
        }
    }

    @Override
    public String print(Manufacturer object, Locale locale) {
        if (object == null) {
            return "";
        }
        return String.format("[%d, %s]", object.getId(), object.getName());
    }
}
