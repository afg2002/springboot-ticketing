package springticketing.springticketing.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import springticketing.springticketing.models.Booking;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends MongoRepository<Booking,String> {
    List<Booking> findByUserIdAndIsCanceled(String userId, boolean isCanceled);

    @Query("{ 'userId' : ?0, 'isCanceled' : false }")
    List<Booking> findByUserId(String userId);
    
    List<Booking> findByMovieIdAndSeatNumberAndScreeningTimeAndStatus(String movieId, String seatNumber, String screeningTime, List<String> status);
    List<Booking> findByMovieIdAndScreeningTime(String movieId,String screeningTime);
    Boolean existsByMovieIdAndSeatNumber(String movieId, String seatNumber);

    @Query("{ 'screeningDate': { $lt: ?0 }, 'screeningTime': { $lt: ?1 }, 'status': { $ne: 'Cancel' } }")
    List<Booking> findAllExpiredBookings(Date currentDate, String currentTime);
}
