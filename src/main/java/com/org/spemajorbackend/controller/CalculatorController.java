package com.org.spemajorbackend.controller;

import com.org.spemajorbackend.dro.ConversionRequest;
import com.org.spemajorbackend.dro.BMI;
import com.org.spemajorbackend.service.CalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
@RestController
@RequestMapping("/calculate")
@Slf4j
public class CalculatorController {
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }
    private static final Logger logger = LogManager.getLogger(CustomerController.class);

    @GetMapping("/bmi")
    public ResponseEntity<?> calculateBMI(@RequestBody BMI bmi) {
        try {
            if (bmi.getHeight() <= 0 || bmi.getWeight() <= 0) {
                return ResponseEntity.badRequest().body("Please pass valid height and weight");
            }

            Double myBMI = calculatorService.calculateBMI(bmi.getHeight(), bmi.getWeight());
            return ResponseEntity.ok(myBMI);
        } catch (Exception e) {
//            System.out.println("Exception caught:" + e.getMessage());
            logger.info("Exception caught: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
