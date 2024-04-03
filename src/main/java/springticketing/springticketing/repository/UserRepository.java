package springticketing.springticketing.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import springticketing.springticketing.models.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);

    @Query(value = "{ 'id' : ?0 }", fields = "{ 'password' : 0 }")
    Optional<User> findByIdWithoutPassword(String id);
    User save(User user);
}
