package com.bbdgrad.beanfinancetrackerserver;

import com.bbdgrad.beanfinancetrackerserver.controller.category.CategoryController;
import com.bbdgrad.beanfinancetrackerserver.controller.category.CategoryRequest;
import com.bbdgrad.beanfinancetrackerserver.model.Category;
import com.bbdgrad.beanfinancetrackerserver.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CategoryControllerTest {
    // Adding Line for testing
    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    // Adding this line for testing
    @Test
    void testGetCategories() {

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Category 1", new BigDecimal(10000)));
        categories.add(new Category(1, "Category 2", new BigDecimal(10000)));

        when(categoryService.getCategories()).thenReturn(new ResponseEntity<List<Category>>(categories,HttpStatus.OK));

        ResponseEntity<List<Category>> returnedCategories = categoryController.getCategories();

        assertEquals(2, Objects.requireNonNull(returnedCategories.getBody()).size());
        assertEquals(categoryController.getCategories().getStatusCode(), HttpStatus.OK);
        assertEquals("Category 1", returnedCategories.getBody().getFirst().getName());
        assertEquals("Category 2", returnedCategories.getBody().get(1).getName());

        verify(categoryService, times(2)).getCategories();
    }

    @Test
    void testCreateCategory() {
        CategoryRequest categoryRequest = new CategoryRequest("Category 1", new BigDecimal(10000));
        Category createdCategory = new Category(1, "Category 1", new BigDecimal(10000));
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(createdCategory, HttpStatus.OK);

        when(categoryService.createCategory(categoryRequest)).thenReturn(expectedResponse);

        ResponseEntity<Object> actualResponse = categoryController.createCategory(categoryRequest);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

        Category responseCategory = new Category(1,"Category 1", new BigDecimal(10000));
        assertEquals(responseCategory, Objects.requireNonNull(actualResponse.getBody()));

        verify(categoryService, times(1)).createCategory(categoryRequest);
    }

    @Test
    void testCategoriesPostById() {

        Category category = new Category(1, "Category 1", new BigDecimal(10000));
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(category, HttpStatus.OK);

        when(categoryService.getCategory(1)).thenReturn(new ResponseEntity<Category>(category, HttpStatus.OK));

        // When
        ResponseEntity<Category> actualResponse = categoryController.getCategory(1);

        // Then
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(category, actualResponse.getBody());

        verify(categoryService, times(1)).getCategory(1);
    }
    //

    // 
    @Test
    void testCategoriesUpdate(){
        try {
            // Your test logic here
            Category category = new Category(1, "Category 1", new BigDecimal(10000));
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(category, HttpStatus.OK);

            when(categoryService.getCategory(1)).thenReturn(new ResponseEntity<>(category, HttpStatus.OK));

            ResponseEntity<Category> actualResponse = categoryController.getCategory(1);

            // Then
            assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
            assertEquals(category, actualResponse.getBody());

            verify(categoryService, times(1)).getCategory(1);

            // Given for update
            Category updatedCategory = new Category(1, "Updated Category 1", new BigDecimal(15000));
            ResponseEntity<Object> updateExpectedResponse = new ResponseEntity<>(updatedCategory, HttpStatus.OK);

            when(categoryService.updateCategory(1, new BigDecimal(1000))).thenReturn(updateExpectedResponse);

            // When for update
            ResponseEntity<Object> updateActualResponse = categoryController.updateCategory(1, new BigDecimal(100000));

            // Then for update
            assertEquals(HttpStatus.OK, updateActualResponse.getStatusCode());
            assertEquals(updatedCategory, updateActualResponse.getBody());

            verify(categoryService, times(1)).updateCategory(1, new BigDecimal(10000));

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

//    @Test
//    void testRemoveCategory_CategoryNotFound() {
//        // Given
//        int nonExistentCategoryId = 100;
//        when(categoryService.getCategory(nonExistentCategoryId)).thenReturn(new ResponseEntity<>(null,HttpStatus.valueOf("Category does not exist")));
//
//        // When
//        ResponseEntity<String> actualResponse = categoryController.removeCategory(nonExistentCategoryId);
//
//        // Then
//        assertEquals(HttpStatus.valueOf("Category does not exist"), actualResponse.getStatusCode());
//        //assertEquals("Category does not exist", actualResponse.getBody());
//
//        verify(categoryService, never()).removeCategory(nonExistentCategoryId);
//    }


}
