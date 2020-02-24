package com.kanupriyaraheja.sample.Service;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
class MockClass {
    private int counter=0;
    public String mockByRandom() {
        double random = Math.random();
        if (random < 0.6) {
            System.out.println("\tProcessing finished. Status = SUCCESS");
            return "SUCCESS";
        } else if (random < 0.8) {
            System.out.println("\tProcessing finished. Status = FAILURE");
            return "FAILURE";
        } else {
            System.out.println("\tProcessing finished. Status = BadProcessingException");
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public String mockByCount() {
        counter++;
        if (counter < 100) {
            return "SUCCESS";
        }
        else{
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
