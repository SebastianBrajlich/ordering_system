package com.ltrlabs.product.service;

import com.ltrlabs.product.repository.ProductRepository;
import com.ltrlabs.product.service.dto.ProductDTO;
import com.ltrlabs.product.service.exception.ProductNotValidException;
import com.ltrlabs.product.service.mapper.ProductServiceMapper;
import com.ltrlabs.product.service.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public UUID addProduct(ProductDTO productDTO) throws ProductNotValidException {
        if (!ProductValidator.isProductValidForRegistration(productDTO)) throw new ProductNotValidException();
        return productRepository.createProduct(ProductServiceMapper.mapToProduct(productDTO));
    }

    public ProductDTO getProduct(UUID productID) throws ProductNotValidException {
        return ProductServiceMapper.mapToProductDTO(productRepository.getProduct(productID).orElseThrow(ProductNotValidException::new));
    }
}
