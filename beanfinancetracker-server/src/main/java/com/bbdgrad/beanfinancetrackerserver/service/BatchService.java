package com.bbdgrad.beanfinancetrackerserver.service;

import com.bbdgrad.beanfinancetrackerserver.controller.batch.BatchRequest;
import com.bbdgrad.beanfinancetrackerserver.controller.country.CountryRequest;
import com.bbdgrad.beanfinancetrackerserver.model.Batch;
import com.bbdgrad.beanfinancetrackerserver.model.Bean;
import com.bbdgrad.beanfinancetrackerserver.model.Country;
import com.bbdgrad.beanfinancetrackerserver.repository.BatchRepository;
import com.bbdgrad.beanfinancetrackerserver.repository.BeanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BatchService {
    private final BatchRepository batchRepository;
    private final BeanRepository beanRepository;

    public ResponseEntity<List<Batch>> getBatches() {
        Optional<List<Batch>> countryList = Optional.of(batchRepository.findAll());
        return ResponseEntity.ok().body(countryList.orElseGet(List::of));
    }

    public ResponseEntity<Batch> registerBatch(BatchRequest batchRequest) {
        var newBatch = Batch.builder()
                .quantity(batchRequest.getQuantity())
                .weight(batchRequest.getWeight())
                .bean_id(batchRequest.getBean_id()).build();
        batchRepository.save(newBatch);
        return ResponseEntity.ok().body(newBatch);
    }

    public ResponseEntity<String> removeBatch(Integer id) {
        Optional<Batch> countryExist = batchRepository.findById(id);
        if (countryExist.isEmpty()) {
            return new ResponseEntity<>("Batch does not Exists", HttpStatus.NOT_FOUND);
        }
        batchRepository.deleteById(id);

        return ResponseEntity.ok().body("Batch removed successfully");
    }

    public ResponseEntity<Batch> getBatch(Integer id) {
        Optional<Batch> batch = batchRepository.findById(id);
        if (batch.isEmpty()) {
            return new ResponseEntity("Batch does not exist", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(batch.orElse(null));
    }

    public ResponseEntity<Batch> updateBatch(Integer id, Optional<Integer> quantity, Optional<Float> weight, Optional<Integer> bean_id) {

        Optional<Batch> batch = batchRepository.findById(id);
        if (batch.isEmpty()) {
            return new ResponseEntity("Batch does not Exists", HttpStatus.NOT_FOUND);
        }
        quantity.ifPresent(integer -> batch.get().setQuantity(integer));
        weight.ifPresent(aFloat -> batch.get().setWeight(aFloat));

        if (bean_id.isPresent()) {
            Optional<Bean> beanExist = beanRepository.findById(bean_id.get());
            if (beanExist.isEmpty()) {
                return new ResponseEntity("Bean does not Exists", HttpStatus.NOT_FOUND);
            }
            batch.get().setBean_id(bean_id.get());
        }

        batchRepository.save(batch.get());
        return ResponseEntity.ok().body(batch.get());

    }
}
