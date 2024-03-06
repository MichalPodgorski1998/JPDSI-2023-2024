package org.example.library.service;

import org.example.library.dto.ProductDto;
import org.example.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();
    Product save(MultipartFile imageProduct, ProductDto product);
    Product update(MultipartFile imageProduct, ProductDto productDto);
    void deleteById(Long id);
    ProductDto getById(Long id);
    Page<Product> searchProducts(int pageNo, String keywords);
    Page<Product> pageProducts(int pageNo);
    Product updateWithoutImage(ProductDto productDto);
}
