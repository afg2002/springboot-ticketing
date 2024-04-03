package springticketing.springticketing.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springticketing.springticketing.models.Movie;

import java.util.List;


public interface MovieRepository extends MongoRepository<Movie, String> {

    List<Movie> findByIsShowed(boolean isShowed);



}
