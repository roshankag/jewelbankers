package com.jewelbankers.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/jewelbankersapi/pincode")
public class PincodeController {

    @GetMapping("/{pincode}")
    public ResponseEntity<String> getPincodeDetails(@PathVariable String pincode) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.postalpincode.in/pincode/" + pincode;
        try {
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
            org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                entity,
                String.class
            );
            String response = responseEntity.getBody();

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("[{\"Message\":\"Error fetching pincode: " + e.getMessage() + "\",\"Status\":\"Error\",\"PostOffice\":null}]");
        }
    }
}
