package org.example.library.service;

import org.example.library.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category save(Category category);
    Category getById(Long id);
    Category update(Category category);
}
