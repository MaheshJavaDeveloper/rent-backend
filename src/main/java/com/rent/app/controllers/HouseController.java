package com.rent.app.controllers;

import com.rent.app.models.House;
import com.rent.app.repository.HouseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class HouseController {

    @Autowired
    HouseRepository houseRepository;

    @GetMapping(value = "/house", produces = "application/json")
    public List<House> getHouse() {
        return houseRepository.findAll();
    }

    @GetMapping(value = "/house/owner/{id}", produces = "application/json")
    public List<House> getHouseByOwner(@PathVariable Long id) {
        Optional<List<House>> houses = houseRepository.findByHouseOwnerId(id);
        if (houses.isPresent()) {
            return houses.get();
        }
        return new ArrayList<>();
    }

    @GetMapping("/house/{id}")
    public House getHouse(@PathVariable Long id) {
        Optional<House> house = houseRepository.findById(id);
        if (house.isPresent()) {
            return house.get();
        }
        return null;
    }

    @PostMapping("/house")
    public House createHouse(@Valid @RequestBody House house) {
        return houseRepository.save(house);
    }

    @PatchMapping("/house")
    public House updateMapping(@Valid @RequestBody House house) {
        return houseRepository.save(house);
    }
}
