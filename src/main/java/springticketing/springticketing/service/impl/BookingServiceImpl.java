package springticketing.springticketing.service.impl;

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
        ApiResponse response;
        String token = tokenHolderRequest.getToken();
        try {

            if (!jwtUtils.isTokenValid(token)) {
                response =  responseErrorToken(null);
            }else{
                String userIdFromToken = jwtUtils.getUserIdFromToken(token);

                Optional<Booking> existBooking = bookingRepository.findById(id);

                if(existBooking.isPresent()){
                    Booking existingBooking = existBooking.get();
                    if(bookingRepository.existsByMovieIdAndSeatNumber(existingBooking.getMovieId(), updatedBookingData.getSeatNumber())){
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
        ApiResponse response;
        String token = tokenHolderRequest.getToken();

        try {
            if (!jwtUtils.isTokenValid(token)) {
                response = responseErrorToken(null);
                return response;
            }

            Optional<Movie> movie = movieRepository.findById(booking.getMovieId());
            if (movie.isEmpty()) {
                response = responseErrorNotFound(null);
                return response;
            }

            List<Booking> existingBooking = bookingRepository.
                    findByMovieIdAndSeatNumberAndScreeningTime(booking.getMovieId(), booking.getSeatNumber(), 
                            booking.getScreeningTime());
            if (existingBooking!=null && !existingBooking.isEmpty()) {
                response = responseErrorDuplicate(null);
                return response;
            }

            String userIdFromToken = jwtUtils.getUserIdFromToken(token);
            booking.setUserId(userIdFromToken);
            Booking savedBooking = bookingRepository.save(booking);
            response = responseSuccess(savedBooking);

        } catch (Exception e) {
            e.printStackTrace();
            response = responseErrorGeneral(e.getMessage());
        }
        return response;
    }
}
