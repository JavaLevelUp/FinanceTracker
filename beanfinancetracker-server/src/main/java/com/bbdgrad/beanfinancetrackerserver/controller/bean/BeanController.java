package com.bbdgrad.beanfinancetrackerserver.controller.bean;

import com.bbdgrad.beanfinancetrackerserver.model.Bean;
import com.bbdgrad.beanfinancetrackerserver.model.Student;
import com.bbdgrad.beanfinancetrackerserver.service.BeanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/bean")
public class BeanController {
    private final BeanService beanService;

    @GetMapping()
    public List<Bean> getBeans() {
        return beanService.getBeans();
    }

    @PostMapping
    public String registerBean(@RequestBody BeanRequest beanRequest) throws Exception {
        return beanService.registerBean(beanRequest);
    }
}
