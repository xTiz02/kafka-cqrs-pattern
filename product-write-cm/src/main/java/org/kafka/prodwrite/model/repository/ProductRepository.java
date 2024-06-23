package org.kafka.prodwrite.model.repository;

import org.kafka.prodwrite.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


    @Modifying
    @Query("update Product p set p.active = ?2, p.updatedAt = NOW() where p.unitCode = ?1")
    void changeProductActiveByUnitId(String unitId, boolean active);

    @Modifying
    @Query("update Product p set p.active = ?2 where p.id = ?1")
    void changeProductActiveById(int id, boolean active);

    @Modifying
    @Query("update Product p set p.stock = ?2 where p.id = ?1")
    void changeProductStock(int id, int stock);

    void deleteByUnitCode(String unitCode);

    Product findByUnitCode(String unitCode);


}
