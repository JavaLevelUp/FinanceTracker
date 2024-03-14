package com.bbdgrad.beanfinancetrackerserver.service;

import com.bbdgrad.beanfinancetrackerserver.controller.bean.BeanRequest;
import com.bbdgrad.beanfinancetrackerserver.model.Bean;
import com.bbdgrad.beanfinancetrackerserver.model.Country;
import com.bbdgrad.beanfinancetrackerserver.repository.BeanRepository;
import com.bbdgrad.beanfinancetrackerserver.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class BeanService {
    private final BeanRepository beanRepository;
    private final CountryRepository countryRepository;

    public ResponseEntity<List<Bean>> getBeans() {
        Optional<List<Bean>> beanList = Optional.of(beanRepository.findAll());
        return ResponseEntity.ok(beanList.orElseGet(List::of));
    }

    public ResponseEntity<Bean> registerBean(BeanRequest beanRequest){
        Optional<Bean> beanExist = beanRepository.findByName(beanRequest.getName());

        if(beanExist.isPresent()){
            System.out.println("Bean already exists");
            return new ResponseEntity("Bean already Exists", HttpStatus.CONFLICT);
        }
        Optional<Country> countryExist = countryRepository.findById(beanRequest.getCountry_id());
        if(countryExist.isEmpty()){
            return new ResponseEntity("Country does not exist", HttpStatus.NOT_FOUND);
        }

        var newBean = Bean.builder().name(beanRequest.getName()).country_id(beanRequest.getCountry_id()).build();
        beanRepository.save(newBean);
        return ResponseEntity.ok(newBean);
    }

    public ResponseEntity<Bean> getBean(int beanId){
        Optional<Bean> bean = beanRepository.findById(beanId);
        if(bean.isEmpty()){
            return new ResponseEntity("Bean does not exist", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(bean.orElse(null));
    }

    public ResponseEntity<String> removeBean(int id) {
        if (!beanRepository.existsById(id)) {
            return new ResponseEntity<>("Bean does not Exists", HttpStatus.NOT_FOUND);
        }
        beanRepository.deleteById(id);
        return ResponseEntity.ok().body("Bean remove successfully");
    }

    public ResponseEntity<Bean> updateBean(Integer id, String name, Optional<Integer> country_id) {

        Optional<Bean> bean = beanRepository.findById(id);
        if(bean.isEmpty()){
            return new ResponseEntity("Bean does not Exists", HttpStatus.NOT_FOUND);
        }
        if(name != null){
            bean.get().setName(name);
        }
        country_id.ifPresent(integer -> bean.get().setCountry_id(integer));

        beanRepository.save(bean.get());
        return ResponseEntity.ok().body(bean.get());

    }
}
