package com.rent.app.controllers;

import com.rent.app.models.Rent;
import com.rent.app.repository.RentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class RentController {

    @Autowired
    RentRepository rentRepository;

    @GetMapping(value = "/rent", produces = "application/json")
    public List<Rent> getHouse() {
      return  rentRepository.findAll();
    }

    @GetMapping("/rent/{id}")
    public Rent getHouse(@PathVariable Long id) {
        Optional<Rent> rent= rentRepository.findById(id);
        if(rent.isPresent()){
            return rent.get();
        }
        return null;
    }

    @PostMapping("/rent")
    public Rent createHouse(@Valid @RequestBody Rent house) {
        return rentRepository.save(house);
    }
}
