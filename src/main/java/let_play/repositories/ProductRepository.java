package let_play.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import let_play.entities.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByUserId(String userId);

    void deleteByUserId(String userID);

}
