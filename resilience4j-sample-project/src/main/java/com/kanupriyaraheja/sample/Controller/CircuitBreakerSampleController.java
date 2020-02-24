package com.kanupriyaraheja.sample.Controller;

import com.kanupriyaraheja.sample.Service.CircuitBreakerSampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CircuitBreakerSampleController {
    private CircuitBreakerSampleService circuitBreakerSampleService;
    @Autowired
    public CircuitBreakerSampleController(CircuitBreakerSampleService circuitBreakerSampleService) {
        this.circuitBreakerSampleService = circuitBreakerSampleService;
    }
    @GetMapping("/")
    public void sampleCircuitBreaker(){
        circuitBreakerSampleService.verifyCircuitbreaker();
    }
}
