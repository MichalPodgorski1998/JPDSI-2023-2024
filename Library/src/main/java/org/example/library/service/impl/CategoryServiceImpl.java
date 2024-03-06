package org.example.library.service.impl;

import org.example.library.model.Category;
import org.example.library.repository.CategoryRepository;
import org.example.library.repository.ProductRepository;
import org.example.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category update(Category category) {
        Category categoryUpdate = categoryRepository.findById(category.getId()).get();
        categoryUpdate.setCategoryName(category.getCategoryName());
        return categoryRepository.save(category);
    }




    @Override
    public boolean canDeleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return category != null && !category.isAssociatedWithProducts();
    }

    @Override
    public void deleteCategory(Long id) {
        if (canDeleteCategory(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Nie można usunąć kategorii, ponieważ jest powiązana z produktami.");
        }
    }
}
