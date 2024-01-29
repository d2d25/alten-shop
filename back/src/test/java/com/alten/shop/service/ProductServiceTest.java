package com.alten.shop.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.alten.shop.dto.ProductDTO;
import com.alten.shop.entity.Category;
import com.alten.shop.entity.InventoryStatus;
import com.alten.shop.entity.Product;
import com.alten.shop.repository.ProductRepository;
import com.alten.shop.service.exception.NotFoundException;
import com.github.javafaker.Faker;

/**
 * Test class for ProductService
 * @Autor Dénez Fauchon
 * @see ProductService
 */
@SpringBootTest
class ProductServiceTest {

	private static final Integer NB_PRODUCTS = 10;
	
    @Mock
    private ProductRepository productRepository;
    
    @Mock
    private ModelMapper modelMapper;
    
    @InjectMocks
    private ProductService productService;
    
    @Autowired
    private Faker faker;

    private List<Product> productsWithId;
    
    private List<Product> products;
    
    private List<ProductDTO> productsDTOwithId;
    
    private List<ProductDTO> productsDTO;

    @BeforeEach
    public void setUp() {
        
        productsWithId = new ArrayList<>();
        products = new ArrayList<>();
        productsDTOwithId = new ArrayList<>();
        productsDTO = new ArrayList<>();
        
        // Create products and productsDTO
        for (int i = 0; i < NB_PRODUCTS; i++) {
        	// Create productWithId
        	Product productWithId = Product.builder()
        			.id((long) i)
        			.code(faker.code().isbn10())
        			.name(faker.book().title())
        			.description(faker.lorem().paragraph())
        			.price(faker.number().numberBetween(1, 1000))
        			.quantity(faker.number().numberBetween(1, 100))
        			.inventoryStatus(InventoryStatus.values()[faker.number().numberBetween(0, InventoryStatus.values().length-1)])
        			.category(Category.values()[faker.number().numberBetween(0, Category.values().length-1)])
        			.image(faker.internet().image())
        			.rating(faker.number().numberBetween(1, 5))
        			.build();
        	// Create product
        	Product product = Product.builder()
        			.code(productWithId.getCode())
        			.name(productWithId.getName())
        			.description(productWithId.getDescription())
        			.price(productWithId.getPrice())
        			.quantity(productWithId.getQuantity())
        			.inventoryStatus(productWithId.getInventoryStatus())
        			.category(productWithId.getCategory())
        			.image(productWithId.getImage())
        			.rating(productWithId.getRating())
        			.build();
        	// Create productDTOwithId
        	ProductDTO productDTOwithId = ProductDTO.builder()
        			.id(productWithId.getId())
        			.code(productWithId.getCode())
        			.name(productWithId.getName())
        			.description(productWithId.getDescription())
        			.price(productWithId.getPrice())
        			.quantity(productWithId.getQuantity())
        			.inventoryStatus(productWithId.getInventoryStatus().getValue())
        			.category(productWithId.getCategory().getValue())
        			.image(productWithId.getImage())
        			.rating(productWithId.getRating())
        			.build();
        	// Create productDTO
        	ProductDTO productDTO = ProductDTO.builder()
        			.code(product.getCode())
        			.name(product.getName())
        			.description(product.getDescription())
        			.price(product.getPrice())
        			.quantity(product.getQuantity())
        			.inventoryStatus(product.getInventoryStatus().getValue())
        			.category(product.getCategory().getValue())
        			.image(product.getImage())
        			.rating(product.getRating())
        			.build();
        	
        	productsWithId.add(productWithId);
        	products.add(product);
        	productsDTOwithId.add(productDTOwithId);
        	productsDTO.add(productDTO);
        }
        
    }

    /**
     * Test of getAll method.
     * Result: Success
     */
    @DisplayName("Test getAll method - Success")
    @Test
    void testGetAll() {
    	
    	when(productRepository.findAll()).thenReturn(productsWithId);
    	productsWithId.forEach(productWithId -> {
			when(modelMapper.map(productWithId, ProductDTO.class))
					.thenReturn(productsDTOwithId.get(productsWithId.indexOf(productWithId)));
		});
    	assertEquals(productsDTOwithId, productService.getAll());
    }

    /**
     * Test of getAll method when no product found.
     * Result: Success
     */
    @DisplayName("Test getAll method when no product found - Success")
    @Test
    void testGetAllWhenNoProductFound() {
        when(productRepository.findAll()).thenReturn(List.of());
        assertEquals(List.of(), productService.getAll());
    }
    
    /**
     * Test of getById method.
     * Result: Success
     */
    @DisplayName("Test getById method - Success")
    @Test
    void testGetById() throws Exception{
    	Product product;
    	ProductDTO productDTO;
    	
    	for (int i = 0; i < NB_PRODUCTS; i++) {
    		product = productsWithId.get(i);
    		productDTO = productsDTOwithId.get(i);
    		
    		when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
    		when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);
    		assertEquals(productDTO, productService.getById(product.getId()));
    	}
    }
    
    /**
     * Test of getById method when product not found.
     * Result: NotFoundException
     */
    @DisplayName("Test getById method when product not found - NotFoundException")
    @Test
    void testGetByIdWhenProductNotFound() {
    	Long id = (long) faker.number().numberBetween(NB_PRODUCTS, NB_PRODUCTS*2);
    	when(productRepository.findById(id)).thenReturn(Optional.empty());
    	
        assertThrows(NotFoundException.class, () -> {
            productService.getById(id);
        });
    }
    
    /**
     * Test of save method.
     * Result: Success
     */
    @DisplayName("Test save method - Success")
    @Test
    void testSave() {
        Product productWithId = productsWithId.get(0);
        Product product = products.get(0);
        ProductDTO productDTOwithId = productsDTOwithId.get(0);
        ProductDTO productDTO = productsDTO.get(0);
        
		for (int i = 0; i < NB_PRODUCTS; i++) {
			productWithId = productsWithId.get(i);
			product = products.get(i);
			productDTOwithId = productsDTOwithId.get(i);
			productDTO = productsDTO.get(i);

			when(modelMapper.map(productDTO, Product.class)).thenReturn(product);
			when(productRepository.save(product)).thenReturn(productWithId);
			when(modelMapper.map(productWithId, ProductDTO.class)).thenReturn(productDTOwithId);
			assertEquals(productDTOwithId, productService.save(productDTO));
		}
    }
    
    /**
     * Test of update method.
     * Result: Success
     */
    @DisplayName("Test update method - Success")
    @Test
    void testUpdate() throws Exception{
    	Product productOld;
    	Product productUpdated;
    	ProductDTO productDTONewValues;
    	ProductDTO productDTOUpdated;
    	Product productBaseOnNewValues;
    	
    	for (int i = 0; i < NB_PRODUCTS; i++) {
    		productOld = productsWithId.get(i);
    		productUpdated = Product.builder()
    				.id(productOld.getId())
        			.code(faker.code().isbn10())
        			.name(faker.book().title())
        			.description(faker.lorem().paragraph())
        			.price(faker.number().numberBetween(1, 1000))
        			.quantity(faker.number().numberBetween(1, 100))
        			.inventoryStatus(InventoryStatus.values()[faker.number().numberBetween(0, InventoryStatus.values().length-1)])
        			.category(Category.values()[faker.number().numberBetween(0, Category.values().length-1)])
        			.image(faker.internet().image())
        			.rating(faker.number().numberBetween(1, 5))
        			.build();
    		productDTONewValues = ProductDTO.builder()
    				.code(productUpdated.getCode())
        			.name(productUpdated.getName())
        			.description(productUpdated.getDescription())
        			.price(productUpdated.getPrice())
        			.quantity(productUpdated.getQuantity())
        			.inventoryStatus(productUpdated.getInventoryStatus().getValue())
        			.category(productUpdated.getCategory().getValue())
        			.image(productUpdated.getImage())
        			.rating(productUpdated.getRating())
        			.build();
    		productDTOUpdated = ProductDTO.builder()
    				.id(productOld.getId())
    				.code(productUpdated.getCode())
        			.name(productUpdated.getName())
        			.description(productUpdated.getDescription())
        			.price(productUpdated.getPrice())
        			.quantity(productUpdated.getQuantity())
        			.inventoryStatus(productUpdated.getInventoryStatus().getValue())
        			.category(productUpdated.getCategory().getValue())
        			.image(productUpdated.getImage())
        			.rating(productUpdated.getRating())
        			.build();
    		productBaseOnNewValues = Product.builder()
    				.code(productUpdated.getCode())
        			.name(productUpdated.getName())
        			.description(productUpdated.getDescription())
        			.price(productUpdated.getPrice())
        			.quantity(productUpdated.getQuantity())
        			.inventoryStatus(productUpdated.getInventoryStatus())
        			.category(productUpdated.getCategory())
        			.image(productUpdated.getImage())
        			.rating(productUpdated.getRating())
        			.build();
    		
    		when(productRepository.findById(productOld.getId())).thenReturn(Optional.of(productOld));
    		when(modelMapper.map(productDTONewValues, Product.class)).thenReturn(productBaseOnNewValues);
    		//modelMapper.map(productBaseOnNewValues, productOld); //TODO Problème avec le test d'une méthode void qui modifie un des paramètre d'entrer
    		when(productRepository.save(productOld)).thenReturn(productUpdated);
    		when(modelMapper.map(productUpdated, ProductDTO.class)).thenReturn(productDTOUpdated);
    		
    		assertEquals(productDTOUpdated, productService.update(productOld.getId(), productDTONewValues));
    		verify(modelMapper, times(1)).map(productBaseOnNewValues, productOld);
    	}
    }
    
    /**
     * Test of update method when product not found.
     * Result: NotFoundException
     */
    @DisplayName("Test update method when product not found - NotFoundException")
    @Test
    void testUpdateWhenProductNotFound() {
    	Long id = (long) faker.number().numberBetween(NB_PRODUCTS, NB_PRODUCTS*2);
    	ProductDTO productDTO = productsDTO.get(0);
    	when(productRepository.findById(id)).thenReturn(Optional.empty());
    	
        assertThrows(NotFoundException.class, () -> {
            productService.update(id, productDTO);
        });
    }
    
    /**
     * Test of delete method.
     * Result: Success
     */
    @DisplayName("Test delete method - Success")
    @Test
    void testDelete() throws Exception{
		Product product;

		for (int i = 0; i < NB_PRODUCTS; i++) {
			product = productsWithId.get(i);

			when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
			doNothing().when(productRepository).delete(product);
			productService.delete(product.getId());
			verify(productRepository, times(1)).delete(product);
		}
    }

    /**
     * Test of delete method when product not found.
     * Result: NotFoundException
     */
    @DisplayName("Test delete method when product not found - NotFoundException")
    @Test
    void testDeleteWhenProductNotFound() {
		Long id = (long) faker.number().numberBetween(NB_PRODUCTS, NB_PRODUCTS * 2);
		when(productRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> {
			productService.delete(id);
		});
    }
}
