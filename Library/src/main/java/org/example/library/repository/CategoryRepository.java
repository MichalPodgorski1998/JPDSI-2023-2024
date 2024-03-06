package org.example.library.repository;

import org.example.library.model.Category;
import org.example.library.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
