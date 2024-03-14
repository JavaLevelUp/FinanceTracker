package com.bbdgrad.beanfinancetrackerserver.controller.batch;

import com.bbdgrad.beanfinancetrackerserver.controller.bean.BeanRequest;
import com.bbdgrad.beanfinancetrackerserver.model.Batch;
import com.bbdgrad.beanfinancetrackerserver.model.Bean;
import com.bbdgrad.beanfinancetrackerserver.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/batch")
public class BatchController {
    private final BatchService batchService;

    @GetMapping()
    public ResponseEntity<List<Batch>> getBatches() {
        return batchService.getBatches();
    }

    @PostMapping
    public ResponseEntity<Batch> registerBatch(@RequestBody BatchRequest batchRequest) {
        return batchService.registerBatch(batchRequest);
    }

    @GetMapping("/{batchId}")
    public ResponseEntity<Batch> getBatch(@PathVariable("batchId") Integer batchId) {
        return batchService.getBatch(batchId);
    }

    @DeleteMapping("/delete/{batchId}")
    public ResponseEntity<String> removeBatch(@PathVariable("batchId") int batchId) {
        return batchService.removeBatch(batchId);
    }

    @PutMapping("/update/{batchId}")
    public ResponseEntity<Batch> updateBatch(@PathVariable("batchId") Integer batchId,
                                             @RequestParam(required = false) Optional<String> batch_date,
                                             @RequestParam(required = false) Optional<Float> weight,
                                             @RequestParam(required = false) Optional<Integer> bean_id) {
        return batchService.updateBatch(batchId, batch_date, weight, bean_id);
    }
}
