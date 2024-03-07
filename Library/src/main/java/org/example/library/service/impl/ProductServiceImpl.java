package org.example.library.service.impl;

import org.example.library.dto.ProductDto;
import org.example.library.model.Product;
import org.example.library.repository.ProductRepository;
import org.example.library.service.ProductService;
import org.example.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageUpload imageUpload;

//    @Override
//    public List<ProductDto> findAll() {
//        List<ProductDto> productDtoList = new ArrayList<>();
//        List<Product> products = productRepository.findAll();
//        for (Product product : products){
//            ProductDto productDto = new ProductDto();
//            productDto.setId(product.getId());
//            productDto.setTitle(product.getTitle());
//            productDto.setDescription(product.getDescription());
//            productDto.setArtistName(product.getArtistName());
//            productDto.setAlbumTitle(product.getAlbumTitle());
//            productDto.setPrice(product.getPrice());
//            productDto.setQuantity(product.getQuantity());
//            productDto.setCategory(product.getCategory());
//            productDto.setImage(product.getImage());
//            productDtoList.add(productDto);
//        }
//        return productDtoList;
//    }

    //NOWE
    @Override
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = transferData(products);
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
    public Page<ProductDto> searchProducts(int pageNo, String keywords) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ProductDto> productDtoList = transferData(productRepository.searchProductsList(keywords));
        Page<ProductDto> products = toPage(productDtoList, pageable);
        return products;
    }

//    @Override
//    public Page<Product> pageProducts(int pageNo) {
//        Pageable pageable = PageRequest.of(pageNo, 5);
//        Page<Product> productPages = productRepository.pageProducts(pageable);
//        return productPages;
//    }

    //NOWE
    @Override
    public Page<ProductDto> pageProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ProductDto> products = transferData(productRepository.findAll());
        Page<ProductDto> productPages = toPage(products, pageable);
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
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public List<Product> listViewProducts() {
        return productRepository.listViewProducts();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public List<Product> getRelatedProducts(Long categoryId) {
        return productRepository.getRelatedProducts(categoryId);
    }

    @Override
    public List<Product> getProductsCategory(Long categoryId) {
        return null;
    }

    @Override
    public List<Product> getProductsInCategory(Long categoryId) {
        return productRepository.getProductsInCategory( categoryId);
    }

    //NOWE
    private Page toPage(List<ProductDto> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size()
                : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }


    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

//NOWE
    private List<ProductDto> transferData(List<Product> products) {
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : products) {
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
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

}
