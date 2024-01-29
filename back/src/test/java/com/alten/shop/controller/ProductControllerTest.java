package com.alten.shop.controller;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.alten.shop.entity.Product;
import com.alten.shop.repository.ProductRepository;
import com.alten.shop.utils.ProductWrapper;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.hasSize;

/**
 * Test class for ProductController
 * We test the controller and verify that the data is properly saved in the database
 * @see ProductController
 * @Autor Dénez Fauchon
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductControllerTest {
	
	public static final String BASE_URL = "/products";
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;
	
	List<Product> products;
	
	@BeforeEach
	public void setUp() throws StreamReadException, DatabindException, IOException {
		// Retrieve all products on a JSON file
		products = objectMapper.readValue(new File("src/main/resources/products.json"), new TypeReference<ProductWrapper>() {}).getProducts();
		
		// Save products in database
		productRepository.saveAll(products);
		
		// Clear the cache
		productRepository.flush();
		
	    // Log the number of products saved in the database
	    long numberOfProductsInDatabase = productRepository.count();
	    System.out.println("Number of products in the database: " + numberOfProductsInDatabase);

	    // Assert the number of products in the database
	    Assertions.assertEquals(products.size(), numberOfProductsInDatabase, "Number of products in the database does not match expected count.");
	}
	
	/**
	 * Test of getAll method. 
	 * Result: Success
	 */
	@DisplayName("Test getAll method - Success")
	@Test
	public void testGetAll() throws Exception {
	    mockMvc.perform(get(BASE_URL))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType("application/hal+json"))
	            .andExpect(jsonPath("$._embedded.products", hasSize(products.size())));
	}

	
}
