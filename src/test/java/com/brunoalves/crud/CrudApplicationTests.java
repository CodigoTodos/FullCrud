package com.brunoalves.crud;

import com.brunoalves.crud.dto.ProductDTO;
import com.brunoalves.crud.entity.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CrudApplicationTests {

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplate;

	private final TestH2Repository h2Repository;

	@Autowired
	public CrudApplicationTests(TestH2Repository h2Repository) {
		this.h2Repository = h2Repository;
	}

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/products");
	}

	@Test

	@Sql(statements = "DELETE FROM PRODUCT_TBL WHERE name='TV'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testAddProduct() {
		ProductDTO productDTO = new ProductDTO("TV", 3, 399);
		ProductDTO response = restTemplate.postForObject(baseUrl, productDTO, ProductDTO.class);
		assertEquals("TV", response.getName());
		assertEquals(1, h2Repository.findAll().size());
	}

	@Test
	@Sql(statements = "INSERT INTO PRODUCT_TBL (id,name, quantity, price) VALUES (4,'PHONE', 2, 97)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM PRODUCT_TBL WHERE name='PHONE'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testGetProducts() {
		List<ProductDTO> products = restTemplate.getForObject(baseUrl, List.class);
		assertEquals(1, products.size());
		assertEquals(1, h2Repository.findAll().size());
	}

	@Test
	@Sql(statements = "INSERT INTO PRODUCT_TBL (id,name, quantity, price) VALUES (1,'PEN', 4, 19)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM PRODUCT_TBL WHERE id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testFindProductById() {
		ProductDTO productDTO = restTemplate.getForObject(baseUrl + "/{id}", ProductDTO.class, 1);
		assertAll(
				() -> assertNotNull(productDTO),
				() -> assertEquals(1, productDTO.getId()),
				() -> assertEquals("PEN", productDTO.getName())
		);

	}

	@Test
	@Sql(statements = "INSERT INTO PRODUCT_TBL (id,name, quantity, price) VALUES (2,'COMPUTER', 6, 999)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM PRODUCT_TBL WHERE id=2", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testUpdateProduct(){
		ProductDTO productDTO = new ProductDTO("COMPUTER", 4, 1999);
		restTemplate.put(baseUrl+"/update/{id}", productDTO, 2);
		Product productFromDB = h2Repository.findById(2L).get();
		assertAll(
				() -> assertNotNull(productFromDB),
				() -> assertEquals("COMPUTER", productFromDB.getName()),
				() -> assertEquals(4, productFromDB.getQuantity()),
				() -> assertEquals(1999, productFromDB.getPrice())
		);
	}

	@Test
	@Sql(statements = "INSERT INTO PRODUCT_TBL (id,name, quantity, price) VALUES (8,'BOOKS', 5, 17)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testDeleteProduct(){
		int recordCount=h2Repository.findAll().size();
		assertEquals(1, recordCount);
		restTemplate.delete(baseUrl+"/delete/{id}", 8);
		assertEquals(0, h2Repository.findAll().size());

	}

}
