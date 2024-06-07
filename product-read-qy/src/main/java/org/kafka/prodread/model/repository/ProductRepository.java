package org.kafka.prodread.model.repository;

import org.kafka.prodread.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface ProductRepository extends MongoRepository<Product, String>{

    //actualizar solo el campo active
    @Query("{writeDbId : ?0}")
    @Update("{ $set: { active : ?1 } }")
    void updateActive(int productId, boolean active);

    Product findProductByWriteDbId(int writeDbId);
}
