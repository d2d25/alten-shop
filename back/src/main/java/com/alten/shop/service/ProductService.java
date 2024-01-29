package com.alten.shop.service;

import java.text.MessageFormat;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alten.shop.dto.ProductDTO;
import com.alten.shop.entity.Product;
import com.alten.shop.repository.ProductRepository;
import com.alten.shop.service.exception.NotFoundException;

/**
 * Service class for managing Product entities.
 * @Autor Dénez Fauchon
 */
@Service
public class ProductService implements IProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductDTO> getAll() {
        return productRepository.findAll().stream().map(
            product -> modelMapper.map(product, ProductDTO.class))
            .toList();
    }

    @Override
    public ProductDTO getById(Long id) throws NotFoundException {
        return modelMapper.map(getProductById(id), ProductDTO.class);
    }

    @Override
    public ProductDTO save(ProductDTO pro) {
        Product product = modelMapper.map(pro, Product.class);
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO update(Long id, ProductDTO productDTO) throws NotFoundException {
        Product product = getProductById(id);
        // set id to product to Null to avoid replacing the id product
        productDTO.setId(null);
        Product newproduct = modelMapper.map(productDTO, Product.class);
                
        // update product with new product
        modelMapper.map(newproduct, product);
        
        product = productRepository.save(product);

        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public void delete(Long id) throws NotFoundException{
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    private Product getProductById(Long id) throws NotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format("Product {0} not found", id)));
    }
}