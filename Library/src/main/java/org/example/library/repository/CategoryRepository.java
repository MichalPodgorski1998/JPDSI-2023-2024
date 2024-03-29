package org.example.library.repository;

import org.example.library.dto.CategoryDto;
import org.example.library.model.Category;
import org.example.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select p from Category p")
    Page<Category>
    pageCategories(Pageable pageable);
    @Query("select new org.example.library.dto.CategoryDto(c.id, c.categoryName, count(p.category.id)) from Category c inner join Product p on p.category.id = c.id " +
            "group by c.id")
    List<CategoryDto> getCategoryAndProduct();

}
