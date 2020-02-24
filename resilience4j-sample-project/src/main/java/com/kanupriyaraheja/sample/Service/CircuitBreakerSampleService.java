package com.kanupriyaraheja.sample.Service;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class CircuitBreakerSampleService {
    MockClass mockClass;
    public CircuitBreaker circuitBreaker;

    @Autowired
    public CircuitBreakerSampleService(MockClass mockClass, CircuitBreakerRegistry circuitBreakerRegistry, CircuitBreakerProperties circuitBreakerProperties) {
        this.mockClass = mockClass;
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("circuitbreaker", () -> circuitBreakerProperties.createCircuitBreakerConfig("backend1"));
    }

    public void verifyCircuitbreaker() {
        for (int i = 0; i < 300; i++) {
            System.out.println("counter = " + (i + 1));
            try {
                Thread.sleep(1000);
                Supplier<String> decoratedSupplier = CircuitBreaker
                        .decorateSupplier(circuitBreaker, mockClass::mockByCount);
                String result = null;
                try {
                    result = decoratedSupplier.get();
                } catch (CircuitBreakerOpenException ex) {
                    fallback();
                }
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("Successful call count: " + circuitBreaker.getMetrics().getNumberOfSuccessfulCalls()
                        + " | Failed call count: " + circuitBreaker.getMetrics().getNumberOfFailedCalls()
                        + " | Failure rate %:" + circuitBreaker.getMetrics().getFailureRate() + " | State: "
                        + circuitBreaker.getState());
            }
        }
    }
    private void fallback(){
        System.out.println("Fallback method");
    }
}
