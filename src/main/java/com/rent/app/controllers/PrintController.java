package com.rent.app.controllers;

import com.rent.app.models.House;
import com.rent.app.models.Rent;
import com.rent.app.repository.HouseRepository;
import com.rent.app.repository.RentRepository;
import com.rent.app.security.services.export.ExportPdfService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class PrintController {

    @Autowired
    RentRepository rentRepository;
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    private ExportPdfService exportPdfService;

    @GetMapping("rent/downloadReceipt/{id}")
    public void downloadReceipt(@PathVariable Long id, HttpServletResponse response) throws IOException {

        log.info(String.valueOf(rentRepository.findById(id).get()));
        Map<String, Object> data = createReportData(id);
        ByteArrayInputStream exportedData = exportPdfService.exportReceiptPdf("receipt", data);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=receipt.pdf");
        IOUtils.copy(exportedData, response.getOutputStream());
    }

    public Map<String, Object> createReportData(Long id) {
        Optional<Rent> rent =rentRepository.findById(id);
        Optional<House> house = houseRepository.findById(rent.get().getHouseNumber());
        Map<String, Object> data = new HashMap<>();
        data.put("rent", rent.get());
        data.put("house", house.get());
        return data;
    }
}
