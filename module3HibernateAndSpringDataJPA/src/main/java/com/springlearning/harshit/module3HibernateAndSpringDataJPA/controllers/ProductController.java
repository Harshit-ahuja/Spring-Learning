package com.springlearning.harshit.module3HibernateAndSpringDataJPA.controllers;

import com.springlearning.harshit.module3HibernateAndSpringDataJPA.entities.ProductEntity;
import com.springlearning.harshit.module3HibernateAndSpringDataJPA.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    private final Integer PAGE_SIZE = 5;

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/orderByPrice")
    public List<ProductEntity> getAllProducts_OrderedByPrice() {
        return productRepository.findByOrderByPrice();
    }

    @GetMapping("/orderByTitleDesc")
    public List<ProductEntity> getAllProducts_OrderedByTitleDesc() {
        return productRepository.findByOrderByTitleDesc();
    }

    /*
        @GetMapping
        public List<ProductEntity> getAllProducts(@RequestParam(defaultValue = "id") String sortBy) {
            return productRepository.findBy(Sort.by(sortBy));
        }

        @GetMapping
        public List<ProductEntity> getAllProducts(@RequestParam(defaultValue = "id") String sortBy) {
            return productRepository.findBy(Sort.by(Sort.Direction.DESC, sortBy, "price", "quantity"));
        }
     */

    @GetMapping
    public List<ProductEntity> getAllProducts(@RequestParam(defaultValue = "id") String sortBy) {
        return productRepository.findBy(Sort.by(Sort.Order.desc(sortBy), Sort.Order.asc("id")));
    }

    // *** Pagination Implementation ***
    @GetMapping("/page")
    public List<ProductEntity> getAllProductsInPage(
            @RequestParam(defaultValue = "0") Integer pageNumber,    // Page number starts from 0
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {

         Pageable pageable = direction.contentEquals("DESC") ?
         PageRequest.of(pageNumber, PAGE_SIZE, Sort.Direction.DESC, sortBy)
         : PageRequest.of(pageNumber, PAGE_SIZE, Sort.Direction.ASC, sortBy);

        return productRepository.findAll(pageable).getContent();
    }

    // Filtering, Sorting and Pagination Combined
    @GetMapping("/page/filteredByTitle")
    public List<ProductEntity> getAllProductsInPageFilteredByTitle(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {

        Sort sort = direction.contentEquals("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE, sort);

        return productRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

}
