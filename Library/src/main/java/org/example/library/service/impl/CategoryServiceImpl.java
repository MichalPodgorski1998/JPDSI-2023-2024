package org.example.library.service.impl;

import org.example.library.model.Category;
import org.example.library.repository.CategoryRepository;
import org.example.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
   @Autowired
   private CategoryRepository repo;
    @Override
    public List<Category> findAll() {
        return repo.findAll();
    }

    @Override
    public Category save(Category category) {
        Category categorySave = new Category(category.getCategoryName());
        return repo.save(categorySave);
    }

    @Override
    public Category getById(Long id) {
        return repo.getById(id);
    }

    @Override
    public Category update(Category category) {
        Category categoryUpdate = new Category();
        categoryUpdate.setCategoryName(categoryUpdate.getCategoryName());
        return repo.save(categoryUpdate);
    }
}
