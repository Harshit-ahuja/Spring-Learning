package com.springlearning.harshit.module3HibernateAndSpringDataJPA.repositories;

import com.springlearning.harshit.module3HibernateAndSpringDataJPA.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByTitle(String title);

    /* Spring parses this method into: findBy + CreatedAt + After
       It converts 'CreatedAt' to camelCase ('createdAt') to match the Java Entity field via reflection,
       which Hibernate then maps to the SQL column (created_at)
     */
    List<ProductEntity> findByCreatedAtAfter(LocalDateTime after);

    List<ProductEntity> findByQuantityAndPrice(int quantity, BigDecimal price);

    List<ProductEntity> findByQuantityGreaterThanOrPriceLessThan(int quantity, BigDecimal price);

    List<ProductEntity> findByTitleLike(String title);

    List<ProductEntity> findByTitleContaining(String title);

    Optional<ProductEntity> findByTitleAndPrice(String title, BigDecimal price);


    /* This is JPQL. It lets us provide custom query that should run when the corresponding method is called.
       Notice that here too, we are just working with java entity name - 'ProductEntity'
       and java variable names 'title' & 'quantity'.
       We aren't working with DB table name and DB column names like 'product_table' & 'product_title'
       JPQL just works with java entity names and then later on during execution, hibernate converts this JPQL
       into native SQL that works with the DB names.
     */
    @Query("select e from ProductEntity e where e.title=?1 and e.quantity=?2") // Can write 'e.title=:title and e.quantity=:quantity' as well
    List<ProductEntity> findByTitleAndQuantity(String title, int quantity);
}
