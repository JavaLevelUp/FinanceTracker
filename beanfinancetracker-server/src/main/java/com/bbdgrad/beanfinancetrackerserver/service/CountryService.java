package com.bbdgrad.beanfinancetrackerserver.service;

import com.bbdgrad.beanfinancetrackerserver.controller.country.CountryRequest;
import com.bbdgrad.beanfinancetrackerserver.model.Bean;
import com.bbdgrad.beanfinancetrackerserver.model.Country;
import com.bbdgrad.beanfinancetrackerserver.model.Student;
import com.bbdgrad.beanfinancetrackerserver.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    public ResponseEntity<List<Country>> getCountries() {
        Optional<List<Country>> countryList = Optional.of(countryRepository.findAll());
        return ResponseEntity.ok().body(countryList.orElseGet(List::of));
    }

    public ResponseEntity<Country> registerCountry(CountryRequest countryRequest){
        Optional<Country> countryExist = countryRepository.findByName(countryRequest.getName());
        if(countryExist.isPresent()) return new ResponseEntity("Country already Exists", HttpStatus.CONFLICT);
        var newCountry = Country.builder().name(countryRequest.getName()).build();
        countryRepository.save(newCountry);
        return ResponseEntity.ok().body(newCountry);
    }

    public ResponseEntity<String> removeCountry(Integer id){
        Optional<Country> countryExist = countryRepository.findById(id);
        if(countryExist.isEmpty()){
            return new ResponseEntity<>("Country does not Exists", HttpStatus.NOT_FOUND);
        }
        countryRepository.deleteById(id);

        return ResponseEntity.ok().body("Country remove successfully");
    }

    public ResponseEntity<Country> getCountry(Integer id){
        Optional<Country> country = countryRepository.findById(id);
        if(country.isEmpty()){
            return new ResponseEntity("Country does exist Exists", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(country.orElse(null));
    }

    public ResponseEntity<Country> updateCountry(Integer id, String name) {
        if(name == null){
            return new ResponseEntity("Nothing to update", HttpStatus.BAD_REQUEST);
        }
        Optional<Country> country = countryRepository.findById(id);
        if(country.isEmpty()){
            return new ResponseEntity("Country already Exists", HttpStatus.NOT_FOUND);
        }
        country.get().setName(name);
        countryRepository.save(country.get());
        return ResponseEntity.ok().body(country.get());

    }
}
