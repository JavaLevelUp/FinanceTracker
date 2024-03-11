package com.bbdgrad.beanfinancetrackerserver.controller.country;


import com.bbdgrad.beanfinancetrackerserver.model.Bean;
import com.bbdgrad.beanfinancetrackerserver.model.Country;
import com.bbdgrad.beanfinancetrackerserver.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.hibernate.id.uuid.CustomVersionOneStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/country")
public class CountryController {
    private final CountryService countryService;

    @GetMapping()
    public List<Country> getAllCountries() {
        return countryService.getCountries();
    }

    @PostMapping()
    public Country registerCountry(@RequestBody CountryRequest countryRequest) throws ResponseStatusException{
        try {
            return countryService.registerCountry(countryRequest);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
    }

    @GetMapping("/{countryId}")
    public Country getCountry(@PathVariable("countryId") Integer countryId){
        return countryService.getCountry(countryId);
    }

    @DeleteMapping("/delete/{countryId}")
    public String removeCountry(@PathVariable("countryId") int countryId){
        System.out.println("Hello from the other side");
        return countryService.removeCountry(countryId);
    }

    @PutMapping("/update/{countryId}")
    public Country updateCountry(@PathVariable("countryId") Integer countryId, @RequestParam(required = false) String name) throws Exception {
        return countryService.updateCountry(countryId, name);
    }
}
