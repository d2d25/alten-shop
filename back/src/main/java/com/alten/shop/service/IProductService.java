package com.alten.shop.service;

import java.util.List;

import com.alten.shop.dto.ProductDTO;
import com.alten.shop.service.exception.NotFoundException;

/**
 * Interface for managing Product entities.
 * @Autor Dénez Fauchon
 */
public interface IProductService {

    /*
     * Method to get all products
     * @return List<ProductDTO> : list of products found
     */
    public List<ProductDTO> getAll();

    /*
     * Method to get product by id
     * @param id : product id to get
     * @return ProductDTO : product found
     * @throws NotFoundException if product not found
     */
    public ProductDTO getById(Long id) throws NotFoundException;

    /*
     * Method to save product
     * @param productDTO : ProductDTO to save
     * @return ProductDTO : saved product
     */
    public ProductDTO save(ProductDTO productDTO);

    /*
     * Method to update product
     * @param id : product id to update
     * @param pro : ProductDTO to update
     * @return ProductDTO : updated product
     * @throws NotFoundException if product not found
     */
    public ProductDTO update(Long id, ProductDTO pro) throws NotFoundException;

    /*
     * Method to delete product
     * @param id : product id to delete
     */
    public void delete(Long id) throws NotFoundException;
}