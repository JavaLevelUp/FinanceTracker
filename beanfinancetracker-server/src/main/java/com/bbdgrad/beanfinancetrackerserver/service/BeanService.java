package com.bbdgrad.beanfinancetrackerserver.service;

import com.bbdgrad.beanfinancetrackerserver.controller.bean.BeanRequest;
import com.bbdgrad.beanfinancetrackerserver.model.Bean;
import com.bbdgrad.beanfinancetrackerserver.repository.BeanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class BeanService {
    private final BeanRepository beanRepository;

    public List<Bean> getBeans() {
        Optional<List<Bean>> beanList = Optional.of(beanRepository.findAll());
        return beanList.orElseGet(List::of);
    }

    public String registerBean(BeanRequest beanRequest) throws  Exception{
        Optional<Bean> beanExist = beanRepository.findByName(beanRequest.getName());
        if(beanExist.isPresent()){
            System.out.println("Bean already exists");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Bean already exists");
        }
        var newBean = Bean.builder().name(beanRequest.getName()).build();
        beanRepository.save(newBean);
        return "Bean created successfully";
    }

    public void removeBean(int id) {
        if (!beanRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bean with id " + id + " not found");
        }
        beanRepository.deleteById(id);
    }
}
