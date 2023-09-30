package com.BlogApplaction.backend.service.impl;

import com.BlogApplaction.backend.entities.Category;
import com.BlogApplaction.backend.exceptions.ResourceNotFoundException;
import com.BlogApplaction.backend.payload.CategoryDto;
import com.BlogApplaction.backend.repository.CategoryRepo;
import com.BlogApplaction.backend.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepo repo;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat = this.modelMapper.map(categoryDto, Category.class);
        Category save = this.repo.save(cat);

        return this.modelMapper.map(save,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cats = this.repo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category ", "Category Id", categoryId));
        cats.setCategoryTitle(categoryDto.getCategoryTitle());
        cats.setCategoryDescription(categoryDto.getCategoryDescription());
        Category save = this.repo.save(cats);

        return this.modelMapper.map(save,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category cat = this.repo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category ", "category id", categoryId));

        this.repo.delete(cat);

    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category sss = this.repo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category ", "category id", categoryId));

        return this.modelMapper.map(sss,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> all = this.repo.findAll();
        List<CategoryDto> collect = all.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());

        return collect;
    }
}
