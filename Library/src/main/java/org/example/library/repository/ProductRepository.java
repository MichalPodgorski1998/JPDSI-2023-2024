package org.example.library.repository;

import org.example.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p")
    Page<Product> pageProducts(Pageable pageable);
    @Query("select p from Product p where p.description like %?1% or p.title like  %?1% or p.albumTitle like  %?1% or p.artistName like  %?1% or p.category.categoryName like  %?1%")
    Page<Product> searchProducts(String keywords, Pageable pageable);
}
