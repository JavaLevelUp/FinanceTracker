package com.bbdgrad.beanfinancetrackerserver.controller.bean;

import com.bbdgrad.beanfinancetrackerserver.model.Bean;
import com.bbdgrad.beanfinancetrackerserver.model.Country;
import com.bbdgrad.beanfinancetrackerserver.model.Student;
import com.bbdgrad.beanfinancetrackerserver.service.BeanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/bean")
public class BeanController {
    private final BeanService beanService;

    @GetMapping()
    public ResponseEntity<List<Bean>> getBeans() {
        return beanService.getBeans();
    }

    @PostMapping
    public ResponseEntity<Bean> registerBean(@RequestBody BeanRequest beanRequest) throws Exception {
        return beanService.registerBean(beanRequest);
    }

    @GetMapping("/{beanId}")
    public ResponseEntity<Bean> getCountry(@PathVariable("beanId") Integer beanId){
        return beanService.getBean(beanId);
    }

    @DeleteMapping("/delete/{beanId}")
    public ResponseEntity<String> removeCountry(@PathVariable("beanId") int beanId){
        return beanService.removeBean(beanId);
    }

    @PutMapping("/update/{beanId}")
    public ResponseEntity<Bean> updateCountry(@PathVariable("beanId") Integer beanId, @RequestParam(required = false) String name)  {
        return beanService.updateBean(beanId, name);
    }
}
