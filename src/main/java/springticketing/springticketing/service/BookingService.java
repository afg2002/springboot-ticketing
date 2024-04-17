package springticketing.springticketing.service;

import java.util.List;
import springticketing.springticketing.models.ApiResponse;
import springticketing.springticketing.models.Booking;


public interface BookingService {
    public ApiResponse saveBooking(Booking booking);
    public ApiResponse cancelBooking(String id);
    public ApiResponse updateBooking(String id, Booking updatedBookingData);
    public ApiResponse getBookingByIdRes(String id);
    public ApiResponse getAllCanceledBookings();
    public ApiResponse getBookingByMovieIdAndScreeningTime(String movieId, String screeningTime);
    public ApiResponse getAllBookings();

    public ApiResponse getAllExpiredBookings();
    
}
