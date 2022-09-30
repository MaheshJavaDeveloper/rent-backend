package com.rent.app.controllers;

import com.rent.app.models.House;
import com.rent.app.models.Tenant;
import com.rent.app.repository.HouseRepository;
import com.rent.app.repository.TenantRepository;
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
public class TenantController {
    @Autowired
    TenantRepository tenantRepository;

    @GetMapping(value = "/tenant", produces = "application/json")
    public List<Tenant> getHouse() {
      return  tenantRepository.findAll();
    }

    @GetMapping("/tenant/{id}")
    public Tenant getHouse(@PathVariable Long id) {
        Optional<Tenant> tenant= tenantRepository.findById(id);
        if(tenant.isPresent()){
            return tenant.get();
        }
        return null;
    }

    @PostMapping("/tenant")
    public Tenant createHouse(@Valid @RequestBody Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    @PatchMapping("/tenant")
    public Tenant updateMapping(@Valid @RequestBody Tenant tenant) {
        return tenantRepository.save(tenant);
    }
}
