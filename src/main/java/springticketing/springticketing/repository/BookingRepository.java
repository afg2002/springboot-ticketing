package springticketing.springticketing.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import springticketing.springticketing.models.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends MongoRepository<Booking,String> {
    List<Booking> findByUserIdAndIsCanceled(String userId, boolean isCanceled);

    @Query("{ 'userId' : ?0, 'isCanceled' : false }")
    List<Booking> findByUserId(String userId);

    List<Booking> findByMovieIdAndSeatNumberAndScreeningTime(String movieId, String seatNumber,String screeningTime);
    List<Booking> findByMovieIdAndScreeningTime(String movieId,String screeningTime);
    Boolean existsByMovieIdAndSeatNumber(String movieId, String seatNumber);
}
