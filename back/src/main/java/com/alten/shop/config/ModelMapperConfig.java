package com.alten.shop.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alten.shop.dto.ProductDTO;
import com.alten.shop.entity.Category;
import com.alten.shop.entity.InventoryStatus;
import com.alten.shop.entity.Product;

/**
 * Configuration class for ModelMapper
 * @Autor Dénez Fauchon
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        configureProductToProductDTOMapping(mapper);
        configureProductDTOToProductMapping(mapper);
        mapper.getConfiguration().setSkipNullEnabled(true);
        return mapper;
    }

    private void configureProductToProductDTOMapping(ModelMapper mapper) {
        TypeMap<Product, ProductDTO> typeMap = mapper.createTypeMap(Product.class, ProductDTO.class);
    
        typeMap.addMappings(mapping -> {
            mapping.map(src -> {
                if (src.getCategory() != null) {
                    return src.getCategory().getValue();
                }
                return null;
            }, ProductDTO::setCategory);
            
            mapping.map(src -> {
                if (src.getInventoryStatus() != null) {
                    return src.getInventoryStatus().getValue();
                }
                return null;
            }, ProductDTO::setInventoryStatus);
        });
    }

    private void configureProductDTOToProductMapping(ModelMapper mapper) {
        TypeMap<ProductDTO, Product> typeMap = mapper.createTypeMap(ProductDTO.class, Product.class);
    
        typeMap.addMappings(mapping -> {
            mapping.map(src -> {
                if (src.getCategory() != null) {
                    return Category.get(src.getCategory());
                }
                return null;
            }, Product::setCategory);
            
            mapping.map(src -> {
                if (src.getInventoryStatus() != null) {
                    return InventoryStatus.get(src.getInventoryStatus());
                }
                return null;
            }, Product::setInventoryStatus);
        });
    }
    
}