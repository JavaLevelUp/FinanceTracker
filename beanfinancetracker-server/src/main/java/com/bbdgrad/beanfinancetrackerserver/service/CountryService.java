package com.bbdgrad.beanfinancetrackerserver.service;

import com.bbdgrad.beanfinancetrackerserver.controller.country.CountryRequest;
import com.bbdgrad.beanfinancetrackerserver.model.Bean;
import com.bbdgrad.beanfinancetrackerserver.model.Country;
import com.bbdgrad.beanfinancetrackerserver.model.Student;
import com.bbdgrad.beanfinancetrackerserver.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    public List<Country> getCountries() {
        Optional<List<Country>> countryList = Optional.of(countryRepository.findAll());
        return countryList.orElseGet(List::of);
    }

    public Country registerCountry(CountryRequest countryRequest){
        Optional<Country> countryExist = countryRepository.findByName(countryRequest.getName());
        if(countryExist.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Country already exists");
        }
        var newCountry = Country.builder().name(countryRequest.getName()).build();
        countryRepository.save(newCountry);
        return newCountry;
    }

    public String removeCountry(Integer id){
        Optional<Country> countryExist = countryRepository.findById(id);
        if(countryExist.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country does not exists");
        }
        countryRepository.deleteById(id);

        return "Country remove successfully";
    }

    public Country getCountry(Integer id){
        Optional<Country> country = countryRepository.findById(id);
        if(country.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country does not exists");
        }
        return country.orElse(null);
    }

    public Country updateCountry(Integer id, String name) throws Exception {
        if(name == null){
            throw new Exception("Nothing to update");
        }
        Optional<Country> country = countryRepository.findById(id);
        if(country.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country does not exists");
        }
        country.get().setName(name);
        countryRepository.save(country.get());
        return country.get();

    }
}
