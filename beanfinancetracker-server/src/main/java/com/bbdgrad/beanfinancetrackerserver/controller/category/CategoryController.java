package com.bbdgrad.beanfinancetrackerserver.controller.category;

import com.bbdgrad.beanfinancetrackerserver.model.Category;
import com.bbdgrad.beanfinancetrackerserver.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<Category>> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable("categoryId") Integer categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<String> removeCategory(@PathVariable("categoryId") int categoryId) {
        return categoryService.removeCategory(categoryId);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<Object> updateCategory(@PathVariable("categoryId") Integer categoryId, @RequestParam(required = false) float monthlyBudget) throws Exception {
        System.out.println(monthlyBudget);
        return categoryService.updateCategory(categoryId, BigDecimal.valueOf(monthlyBudget));
    }
}
