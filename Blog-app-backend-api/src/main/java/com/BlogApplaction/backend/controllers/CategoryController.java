package com.BlogApplaction.backend.controllers;

import com.BlogApplaction.backend.payload.ApiResponce;
import com.BlogApplaction.backend.payload.CategoryDto;
import com.BlogApplaction.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    // create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto category = this.service.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(category,HttpStatus.CREATED);
    }

    // Update
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> update(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer catId ){
        CategoryDto updated = this.service.updateCategory(categoryDto, catId);
        return  new ResponseEntity<CategoryDto>(updated,HttpStatus.OK);
    }

    // Deleted

    @DeleteMapping("/{cId}")
    public ResponseEntity<ApiResponce> delete(@PathVariable Integer cId){
        this.service.deleteCategory(cId);
        return new ResponseEntity<ApiResponce>(new ApiResponce("Categray Deleted Sussufully", true),HttpStatus.OK);
    }


    // get By Category Id
    @GetMapping("/{cId}")
    public  ResponseEntity<CategoryDto> getbyId(@PathVariable Integer cId){
        CategoryDto category = this.service.getCategory(cId);
        return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
    }

    // get All catogery
    @GetMapping("/")
    public  ResponseEntity<List<CategoryDto>> getAll(){
        List<CategoryDto> categories = this.service.getCategories();
        return ResponseEntity.ok(categories);
    }
}
