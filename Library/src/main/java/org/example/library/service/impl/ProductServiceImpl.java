package org.example.library.service.impl;

import org.example.library.dto.ProductDto;
import org.example.library.model.Product;
import org.example.library.repository.ProductRepository;
import org.example.library.service.ProductService;
import org.example.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageUpload imageUpload;

    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> productDtoList = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        for (Product product : products){
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setTitle(product.getTitle());
            productDto.setDescription(product.getDescription());
            productDto.setArtistName(product.getArtistName());
            productDto.setAlbumTitle(product.getAlbumTitle());
            productDto.setPrice(product.getPrice());
            productDto.setQuantity(product.getQuantity());
            productDto.setCategory(product.getCategory());
            productDto.setImage(product.getImage());
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    @Override
    public Product save(MultipartFile imageProduct, ProductDto productDto) {
    try {
        Product product = new Product();

        System.out.println("imageProduct: " + imageProduct);

        if (imageProduct.isEmpty()) {
            product.setImage(null); // Ustawia null, gdy nie dodano zdjęcia
        } else {
            imageUpload.uploadImage(imageProduct);
            product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
        }
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        product.setArtistName(productDto.getArtistName());
        product.setAlbumTitle(productDto.getAlbumTitle());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        return productRepository.save(product);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }

    }

    @Override
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product productUpdate = productRepository.getById(productDto.getId());

                if (imageProduct == null || imageProduct.isEmpty()) {
                    productUpdate.setImage(null);
                } else{
                    if(!imageUpload.checkExist(imageProduct)){
                        imageUpload.uploadImage(imageProduct);
                    }
                    productUpdate.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
                }


            productUpdate.setTitle(productDto.getTitle());
            productUpdate.setDescription(productDto.getDescription());
            productUpdate.setCategory(productDto.getCategory());
            productUpdate.setArtistName(productDto.getArtistName());
            productUpdate.setAlbumTitle(productDto.getAlbumTitle());
            productUpdate.setPrice(productDto.getPrice());
            productUpdate.setQuantity(productDto.getQuantity());
            return productRepository.save(productUpdate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.getById(id);
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory());
        productDto.setArtistName(product.getArtistName());
        productDto.setAlbumTitle(product.getAlbumTitle());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(product.getQuantity());
        productDto.setImage(product.getImage());
        return productDto;
    }

    @Override
    public Page<Product> searchProducts(int pageNo, String keywords) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<Product> products = productRepository.searchProducts(keywords, pageable);
        return products;
    }

    @Override
    public Page<Product> pageProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<Product> productPages = productRepository.pageProducts(pageable);
        return productPages;
    }

    @Override
    public Product updateWithoutImage(ProductDto productDto) {
        try {
            Product productUpdate = productRepository.getById(productDto.getId());

            // Tutaj możesz zaktualizować inne pola, jeśli to konieczne
            productUpdate.setTitle(productDto.getTitle());
            productUpdate.setDescription(productDto.getDescription());
            productUpdate.setCategory(productDto.getCategory());
            productUpdate.setArtistName(productDto.getArtistName());
            productUpdate.setAlbumTitle(productDto.getAlbumTitle());
            productUpdate.setPrice(productDto.getPrice());
            productUpdate.setQuantity(productDto.getQuantity());

            return productRepository.save(productUpdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
