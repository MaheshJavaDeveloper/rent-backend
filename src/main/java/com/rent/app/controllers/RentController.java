package com.rent.app.controllers;

import com.rent.app.Handler.Messagehandler;
import com.rent.app.models.*;
import com.rent.app.repository.HouseRepository;
import com.rent.app.repository.PaymentRepository;
import com.rent.app.repository.RentRepository;
import com.rent.app.security.services.export.ExportPdfService;
import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class RentController {

    @Autowired
    RentRepository rentRepository;

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    private ExportPdfService exportPdfService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    Messagehandler messagehandler;

    @Value("${backend-api}")
    private String backendApi;

    @GetMapping(value = "/rent", produces = "application/json")
    public List<Rent> getRents() {
        return rentRepository.findAll();
    }

    @GetMapping("/rent/{id}")
    public Rent getRent(@PathVariable Long id) {
        Optional<Rent> rent = rentRepository.findById(id);
        if (rent.isPresent()) {
            return rent.get();
        }
        return null;
    }

    @GetMapping("/rent/house/{id}")
    public List<Rent> getRentByHouse(@PathVariable Long id) {
        Optional<List<Rent>> rent = rentRepository.findByHouseNumber(id);
        if (rent.isPresent()) {
            return rent.get();
        }
        return null;
    }

    @PostMapping("/rent")
    public Rent createRent(@Valid @RequestBody Rent rent) {
        rent.setRentStatus(RentStatus.GENERATED);
        rent.setInvoiceNumber("RI"+RandomStringUtils.randomAlphabetic(3).toUpperCase()+RandomStringUtils.randomNumeric(5));
        Rent savedRent = rentRepository.save(rent);
        Optional<House> house = houseRepository.findById(rent.getHouseNumber());
        if (house.isPresent()) {
            house.get().getRents().add(savedRent);
            houseRepository.save(house.get());
        }
        return rent;
    }

    @PatchMapping("/rent")
    public Rent updateRent(@Valid @RequestBody Rent rent) {
        if (RentStatus.PAID == rent.getRentStatus() || RentStatus.UNPAID == rent.getRentStatus()) {
            Optional<House> house = houseRepository.findById(rent.getHouseNumber());
            if (house.isPresent()) {
                if (RentStatus.PAID == rent.getRentStatus()) {
                    Payment payment=new Payment();
                    payment.setAmount(rent.getTotalRent());
                    payment.setPaymentDate(new Date());
                    payment.setType(PaymentType.FULL);
                    payment.setMode(PaymentMode.CASH);
                    paymentRepository.save(payment);
                    rent.getPayments().add(payment);
                    house.get().setOverallMeterReading(rent.getCurrentMeterReading());
                }
                if (RentStatus.UNPAID == rent.getRentStatus()) {
                    house.get().setOverallMeterReading(rent.getPreviousMeterReading());
                }
                houseRepository.save(house.get());
            }
        }
        return rentRepository.save(rent);
    }

    @DeleteMapping("/rent/{id}")
    public String createHouse(@PathVariable Long id) {
        rentRepository.deleteById(id);
        return "Successfully Deleted";
    }

    @GetMapping("rent/downloadReceipt/{id}")
    public void downloadReceipt(@PathVariable Long id, HttpServletResponse response) throws IOException {

        log.info(String.valueOf(rentRepository.findById(id).get()));
        Map<String, Object> data = createReportData(id);
        ByteArrayInputStream exportedData = exportPdfService.exportReceiptPdf("receipt", data);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=receipt.pdf");
        IOUtils.copy(exportedData, response.getOutputStream());
    }

    @GetMapping("rent/sendReceipt/{id}")
    public void sendReceipt(@PathVariable Long id) throws Exception {
        Optional<Rent> rent =rentRepository.findById(id);
        Optional<House> house = houseRepository.findById(rent.get().getHouseNumber());
        messagehandler.sendMessage("",backendApi+id,house.get().getTenant().getPhone());
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
