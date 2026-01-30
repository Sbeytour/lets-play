package let_play.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import let_play.entities.User;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
}
