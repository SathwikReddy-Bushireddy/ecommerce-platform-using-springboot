package com.sathwik.ecom_project.controller;

import com.sathwik.ecom_project.model.Product;
import com.sathwik.ecom_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService prodService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(prodService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{prodId}")
    public ResponseEntity<Product> getProduct(@PathVariable int prodId){
        Product prod=prodService.getProductById(prodId);
        if(prod!=null)
            return new ResponseEntity<>(prod,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try{
            Product product1 = prodService.addProduct(product,imageFile);
            return new ResponseEntity<>(product,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        Product product=prodService.getProductById(productId);
        byte[] imageFile=product.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable int productId,@RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product product1= null;
        try {
            product1 = prodService.updateProduct(productId,product,imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to Update",HttpStatus.BAD_REQUEST);
        }
        if(product1!=null)
            return new ResponseEntity<>("Updated",HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to Update",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId){
        System.out.println("In delete mapping");
        Product product1=prodService.getProductById(productId);
        if(product1!=null){
            prodService.deleteProductById(productId);
            return new ResponseEntity<>("Product Deleted",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Product Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        List<Product> products=prodService.searchProducts(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}
