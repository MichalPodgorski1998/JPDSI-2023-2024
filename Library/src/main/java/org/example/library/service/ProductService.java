package org.example.library.service;

import org.example.library.dto.ProductDto;
import org.example.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();
    Product save(MultipartFile imageProduct, ProductDto product);
    Product update(MultipartFile imageProduct, ProductDto productDto);

    List<Product> getProductsInCategory(Long categoryId);

    void deleteById(Long id);
    ProductDto getById(Long id);
    Page<ProductDto> searchProducts(int pageNo, String keywords);
    Page<ProductDto> pageProducts(int pageNo);
    Product updateWithoutImage(ProductDto productDto);
    List<Product> getAllProducts();
    List<Product> listViewProducts();

    Product getProductById(Long id);

    List<Product> getRelatedProducts(Long categoryId);

    List<Product> getProductsCategory(Long categoryId);


}
