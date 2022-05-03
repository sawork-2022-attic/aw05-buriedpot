package com.micropos.carts.rest;

import com.micropos.carts.service.ProductService;
import com.micropos.products.api.ProductsApi;
import com.micropos.products.dto.ProductDto;
import com.micropos.carts.mapper.CartMapper;
import com.micropos.products.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class ProductController implements ProductsApi {

    private final CartMapper productMapper;

    private final ProductService productService;


    public ProductController(ProductService productService, CartMapper productMapper) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @Override
    @GetMapping("/com/micropos/products/")
    public ResponseEntity<List<ProductDto>> listProducts(){
        List<ProductDto> products = new ArrayList<>(productMapper.toProductsDto(this.productService.products()));
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Override
    @GetMapping("/com/micropos/products/{productId}")
    public ResponseEntity<ProductDto> showProductById(@PathVariable String productId) {
        Product product = this.productService.getProduct(productId);
        if (product != null) {
            return new ResponseEntity<>(productMapper.toProductDto(product), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }
}
