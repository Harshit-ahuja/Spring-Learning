package com.springlearning.harshit.module3HibernateAndSpringDataJPA;

import com.springlearning.harshit.module3HibernateAndSpringDataJPA.entities.ProductEntity;
import com.springlearning.harshit.module3HibernateAndSpringDataJPA.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class Module3HibernateAndSpringDataJpaApplicationTests {

	@Autowired
	ProductRepository productRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testRepository_save() {
		ProductEntity productEntity = ProductEntity.builder()
				.sku("nestle123")
				.title("Nestle Chocolate")
				.price(BigDecimal.valueOf(123.45))
				.quantity(4)
				.build();
		ProductEntity savedProductEntity = productRepository.save(productEntity);
		System.out.println(savedProductEntity);
	}

	@Test
	void testRepository_get() {
		List<ProductEntity> entities = productRepository.findAll();
		System.out.println(entities);
	}

	@Test
	void testRepository_customMethodNames_1() {
		List<ProductEntity> entities = productRepository.findByTitle("Pepsi");
		System.out.println(entities);
	}

	@Test
	void testRepository_customMethodNames_2() {
		List<ProductEntity> entities = productRepository.
				findByCreatedAtAfter(LocalDateTime.of(2025, 1, 1, 1, 10));
		System.out.println(entities);
	}

	@Test
	void testRepository_customMethodNames_3() {
		List<ProductEntity> entities = productRepository.findByQuantityAndPrice(4, BigDecimal.valueOf(12.4));
		System.out.println(entities);
	}

	@Test
	void testRepository_customMethodNames_4() {
		List<ProductEntity> entities = productRepository.findByQuantityGreaterThanOrPriceLessThan(2, BigDecimal.valueOf(154.4));
		System.out.println(entities);
	}

	@Test
	void testRepository_customMethodNames_5() {
		List<ProductEntity> entities = productRepository.findByTitleLike("%Parle%");
		System.out.println(entities);
	}

	@Test
	void testRepository_customMethodNames_6() {
		List<ProductEntity> entities = productRepository.findByTitleContaining("Parle");
		System.out.println(entities);
	}

	@Test
	void testRepository_customMethodNames_7() {
		List<ProductEntity> entities = productRepository.findByTitleContainingIgnoreCase("PaRLe");
		System.out.println(entities);
	}

	/* For fetching single/unique entity instead of a list of entities,
	   we need to make sure that the query or constraints that we are providing is going to give us
	   a unique result only.
	   Like here, we have a uniqueConstraint 'title_price_unique' on the ProductEntity which means that
	   the combination of the product_title and price columns must be completely unique across the entire
	   table.
	   Hence, any combination of title and price is going to provide a unique result only.
	 */
	@Test
	void testRepository_customMethodNames_8() {
		Optional<ProductEntity> productEntity = productRepository.findByTitleAndPrice("Pepsi", BigDecimal.valueOf(14.4));
		productEntity.ifPresent(System.out::println);
	}

	@Test
	void testRepository_customMethodNames_9() {
		List<ProductEntity> entities = productRepository.findByTitleAndQuantity("Parle Candy", 3);
		System.out.println(entities);
	}
}
