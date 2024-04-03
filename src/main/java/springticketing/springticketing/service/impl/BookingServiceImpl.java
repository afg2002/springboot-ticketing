package springticketing.springticketing.service.impl;

import java.time.LocalDate;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springticketing.springticketing.models.ApiResponse;
import springticketing.springticketing.models.Booking;
import springticketing.springticketing.models.Movie;
import springticketing.springticketing.repository.BookingRepository;
import springticketing.springticketing.repository.MovieRepository;
import springticketing.springticketing.repository.UserRepository;
import springticketing.springticketing.request.TokenHolderRequest;
import springticketing.springticketing.service.BookingService;
import springticketing.springticketing.utility.JwtUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class BookingServiceImpl extends ResponseServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenHolderRequest tokenHolderRequest;



    @Override
    public ApiResponse getAllBookings(){
        ApiResponse response;
        String token = tokenHolderRequest.getToken();
        if(!jwtUtils.isTokenValid(token)){
            response = responseErrorToken(null);
        }else{
            String userId = jwtUtils.getUserIdFromToken(token);
            List<Booking> booking = bookingRepository.findByUserIdAndIsCanceled(userId, false);
            if(booking.isEmpty()){
                response = responseErrorNotFound(null);
            }else{
                response = responseSuccess(booking);
            }
        }
        return response;
    }
    
    
    

    public ApiResponse getAllCanceledBookings(){
        ApiResponse response;
        String token = tokenHolderRequest.getToken();
        if(!jwtUtils.isTokenValid(token)){
            response = responseErrorToken(null);
        }else{
            String userId = jwtUtils.getUserIdFromToken(token);
            List<Booking> booking = bookingRepository.findByUserIdAndIsCanceled(userId, true);
            if(booking.isEmpty()){
                response = responseErrorNotFound(null);
            }else{
                response = responseSuccess(booking);
            }
        }
        return response;
    }


    public ApiResponse getBookingByIdRes(String id){
        ApiResponse response;
        String token = tokenHolderRequest.getToken();
        try{
            if(!jwtUtils.isTokenValid(token)){
                response = responseErrorToken(null);
            }else{
                Optional<Booking> booking = bookingRepository.findById(id);
                if(booking.isEmpty()){
                    response = responseErrorNotFound(null);
                }else{
                    response = responseSuccess(booking.get());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            response = responseErrorGeneral(e.getMessage());
        }
        return response;
    }
    
    public ApiResponse getBookingByMovieIdAndScreeningTime(String movieId, String screeningTime){
        ApiResponse response;
        String token = tokenHolderRequest.getToken();
        try{
            if(!jwtUtils.isTokenValid(token)){
                response = responseErrorToken(null);
            }else{
                List<Booking> booking = bookingRepository.findByMovieIdAndScreeningTime(movieId,screeningTime);
                if(booking == null){
                    response = responseErrorNotFound(null);
                }else{
                    response = responseSuccess(booking);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            response = responseErrorGeneral(e.getMessage());
        }
        return response;
    }

    public ApiResponse updateBooking(String id, Booking updatedBookingData) {
        ApiResponse response = new ApiResponse();
        String token = tokenHolderRequest.getToken();
        try {

            if (!jwtUtils.isTokenValid(token)) {
                response =  responseErrorToken(null);
            }else{
                String userIdFromToken = jwtUtils.getUserIdFromToken(token);

                Optional<Booking> existBooking = bookingRepository.findById(id);

                if(existBooking.isPresent()){
                    Booking existingBooking = existBooking.get();
                    List<String> seatNumbers = updatedBookingData.getSeatNumber();
                    for(String seatNumber : seatNumbers){
                        if(bookingRepository.existsByMovieIdAndSeatNumber(existingBooking.getMovieId(), seatNumber)){
                            response =  responseErrorDuplicate(null);
                        }else if(!existingBooking.getUserId().equalsIgnoreCase(userIdFromToken)){
                            response =  responseErrorUserNotMatch(null);
                        }else{

                            existingBooking.setScreeningTime(updatedBookingData.getScreeningTime());
                            existingBooking.setSeatNumber(updatedBookingData.getSeatNumber());
                            existingBooking.setCanceled(updatedBookingData.isCanceled());
                            Booking data = bookingRepository.save(existingBooking);
                            response =  responseSuccess(data);
                        }
                    }
                    
                }else{
                    response = responseErrorNotFound(null);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            response =  responseErrorGeneral(e.getMessage());
        }
        return response;
    }

    public ApiResponse cancelBooking(String id) {
        ApiResponse response;
        String token = tokenHolderRequest.getToken();
        try {
            if (!jwtUtils.isTokenValid(token)) {
                response =  responseErrorToken(null);
            }else{
                Optional<Booking> existedBooking = bookingRepository.findById(id);
                String userIdFromToken = jwtUtils.getUserIdFromToken(token);
                if (existedBooking.isEmpty()) {
                    response =  responseErrorNotFound(null);
                } else if(!Objects.equals(existedBooking.get().getUserId(), userIdFromToken)){
                    System.out.println(existedBooking.get().getUserId() + " " + userIdFromToken);
                    response = responseErrorUserNotMatch(null);
                }else{
                    Booking booking = existedBooking.get();
                    booking.setCanceled(true);
                    Booking updatedBooking = bookingRepository.save(booking);
                    response = responseSuccess(updatedBooking);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = responseErrorGeneral(e.getMessage());
        }
        return response;
    }


   public ApiResponse saveBooking(Booking booking) {
    try {
        String token = tokenHolderRequest.getToken();

        // Check if the token is valid
        if (!jwtUtils.isTokenValid(token)) {
            return responseErrorToken(null);
        }

        // Retrieve the movie by ID
        Optional<Movie> movie = movieRepository.findById(booking.getMovieId());
        
        // If movie not found, return error response
        if (movie.isEmpty()) {
            return responseErrorNotFound(null);
        }

        // Check for existing bookings with the same movie ID, seat number, and screening time
        // Check for existing bookings with the same movie ID, seat number, and screening time
        for (String seatNumber : booking.getSeatNumber()) {
            List<Booking> existingBookings = bookingRepository.findByMovieIdAndSeatNumberAndScreeningTime(
                    booking.getMovieId(), seatNumber, booking.getScreeningTime());

            // If there are existing bookings for any seat number, return error response
            if (!existingBookings.isEmpty()) {
                return responseErrorDuplicate(null);
            }
        }


        // Get user ID from token
        String userIdFromToken = jwtUtils.getUserIdFromToken(token);
        booking.setUserId(userIdFromToken);

        // Get current date
        Date currentDate = new Date();
        
        // Get screening date from the movie
        Date screeningDate = movie.get().getReleaseDate();

        // Check if the booking date is before the screening date
        if (currentDate.before(screeningDate)) {
            // If the booking date is before the screening date, set the booking status to "Aktif"
            booking.setStatus("Aktif");
        } else {
            // Otherwise, set the booking status to "Berhasil"
            booking.setStatus("Berhasil");
        }

        // Save the booking
        Booking savedBooking = bookingRepository.save(booking);
        return responseSuccess(savedBooking);

    } catch (Exception e) {
        e.printStackTrace();
        return responseErrorGeneral(e.getMessage());
    }
}

}