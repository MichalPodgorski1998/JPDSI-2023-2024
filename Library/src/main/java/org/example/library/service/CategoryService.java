package org.example.library.service;

import jakarta.transaction.Transactional;
import org.example.library.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category save(Category category);
    Category findById(Long id);
    Category update(Category category);

    boolean canDeleteCategory(Long id);

    void deleteCategory(Long id);
}
