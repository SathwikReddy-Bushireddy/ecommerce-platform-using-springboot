package com.sathwik.ecom_project.service;

import com.sathwik.ecom_project.model.Product;
import com.sathwik.ecom_project.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo prodRepo;

    public List<Product> getAllProducts() {
        return prodRepo.findAll();
    }

    public Product getProductById(int prodId) {
        return prodRepo.findById(prodId).orElse(null);
    }

    public Product addProduct(Product prod, MultipartFile imageFile) throws IOException {
        prod.setImageName(imageFile.getOriginalFilename());
        prod.setImageType(imageFile.getContentType());
        prod.setImageData(imageFile.getBytes());
        return prodRepo.save(prod);
    }

    public Product updateProduct(int productId, Product product, MultipartFile imageFile) throws IOException {
        product.setImageData(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType((imageFile.getContentType()));
        return prodRepo.save(product);
    }

    public void deleteProductById(int productId) {
        prodRepo.deleteById(productId);
    }

    public List<Product> searchProducts(String keyword) {
        return prodRepo.searchProducts(keyword);
    }
}
