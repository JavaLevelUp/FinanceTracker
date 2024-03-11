package com.bbdgrad.beanfinancetrackerserver.controller.country;


import com.bbdgrad.beanfinancetrackerserver.model.Bean;
import com.bbdgrad.beanfinancetrackerserver.model.Country;
import com.bbdgrad.beanfinancetrackerserver.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.hibernate.id.uuid.CustomVersionOneStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/country")
public class CountryController {
    private final CountryService countryService;

    @GetMapping()
    public ResponseEntity<List<Country>> getAllCountries() {
        return countryService.getCountries();
    }

    @PostMapping()
    public ResponseEntity<Country> registerCountry(@RequestBody CountryRequest countryRequest) throws ResponseStatusException{
        try {
            return countryService.registerCountry(countryRequest);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<Country> getCountry(@PathVariable("countryId") Integer countryId){
        return countryService.getCountry(countryId);
    }

    @DeleteMapping("/delete/{countryId}")
    public ResponseEntity<String> removeCountry(@PathVariable("countryId") int countryId){
        return countryService.removeCountry(countryId);
    }

    @PutMapping("/update/{countryId}")
    public ResponseEntity<Country> updateCountry(@PathVariable("countryId") Integer countryId, @RequestParam(required = false) String name) throws Exception {
        return countryService.updateCountry(countryId, name);
    }
}
