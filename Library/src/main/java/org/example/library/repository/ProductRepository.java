package org.example.library.repository;

import org.example.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p")
    Page<Product> pageProducts(Pageable pageable);
    @Query("select p from Product p where p.description like %?1% or p.title like  %?1% or p.albumTitle like  %?1% or p.artistName like  %?1% or p.category.categoryName like  %?1%")
    Page<Product> searchProducts(String keywords, Pageable pageable);
    @Query("select p from Product p where p.description like %?1% or p.title like  %?1% or p.albumTitle like  %?1% or p.artistName like  %?1% or p.category.categoryName like  %?1%")
    List<Product> searchProductsList(String keyword);

    @Query("select p from Product p ")
    List<Product> getAllProducts();

    @Query(value = "select * from products p order by rand() limit 4", nativeQuery = true)
    List<Product> listViewProducts();

    @Query(value = "select * from products p inner join categories c on c.category_id = p.category_id where p.category_id = ?1", nativeQuery = true)
    List<Product> getRelatedProducts(Long categoryID);

    @Query(value = "select  p form Product p inner join Category c on c.id = p.category.id where c.id=?1", nativeQuery = true)
    List<Product> getProductsInCategory(Long categoryId);


}
