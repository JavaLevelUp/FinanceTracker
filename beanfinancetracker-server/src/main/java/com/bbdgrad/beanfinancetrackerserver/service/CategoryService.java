package com.bbdgrad.beanfinancetrackerserver.service;

import com.bbdgrad.beanfinancetrackerserver.controller.category.CategoryRequest;
import com.bbdgrad.beanfinancetrackerserver.model.Category;
import com.bbdgrad.beanfinancetrackerserver.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return ResponseEntity.ok().body(categoryList);
    }

    public ResponseEntity<Object> createCategory(CategoryRequest categoryRequest) {
        Optional<Category> categoryExist = categoryRepository.findByName(categoryRequest.getName());
        if (categoryExist.isPresent()) {
            return new ResponseEntity<>("Category already exists", HttpStatus.CONFLICT);
        }
        Category newCategory = Category.builder().name(categoryRequest.getName()).monthlyBudget(categoryRequest.getMonthlyBudget()).build();
        categoryRepository.save(newCategory);
        return ResponseEntity.ok().body(newCategory);
    }

    public ResponseEntity<String> removeCategory(Integer id) {
        if (!categoryRepository.existsById(id)) {
            return new ResponseEntity<>("Category does not exist", HttpStatus.NOT_FOUND);
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().body("Category removed successfully");
    }

    public ResponseEntity<Category> getCategory(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    public ResponseEntity<Object> updateCategory(Integer id, BigDecimal monthlyBudget) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
        }
        if (monthlyBudget == null) {
            return new ResponseEntity<>("Nothing to update", HttpStatus.BAD_REQUEST);
        }
        category.get().setMonthlyBudget(monthlyBudget);
        categoryRepository.save(category.get());
        return ResponseEntity.ok().body(category.get());
    }
}
