package springticketing.springticketing.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
            List<Booking> booking = bookingRepository.findByUserId(userId);
            if(booking.isEmpty()){
                response = responseErrorNotFound(null);
            }else{
                response = responseSuccess(booking);
            }
        }
        return response;
    }


    // Untuk scheduler
    public ApiResponse getAllExpiredBookings() {
        ApiResponse response;
        try {
            // Mendapatkan waktu saat ini
            Date currentTime = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(currentTime);
            System.out.println("Sekarang " + currentDateTime);

            // Ambil semua booking
            List<Booking> allBookings = bookingRepository.findAll();

            // Daftar booking yang telah kedaluwarsa
            List<Booking> expiredBookings = allBookings.stream()
                    // Filter booking yang memiliki screeningDate sudah kadaluarsa
                    .filter(booking -> isBookingExpired(booking, currentTime))
                    .collect(Collectors.toList());

            // Berhasil: kembalikan daftar booking yang telah kedaluwarsa
            response = responseSuccess(expiredBookings);
        } catch (Exception e) {
            // Jika terjadi kesalahan, membuat respons error
            e.printStackTrace();
            response = responseErrorGeneral(e.getMessage());
        }
        return response;
    }

    // Method untuk memeriksa apakah booking telah kedaluwarsa
    private boolean isBookingExpired(Booking booking, Date currentTime) {
        try {
            // Mendapatkan waktu screening dari booking
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
            Date screeningTime = timeFormatter.parse(booking.getScreeningTime());
            System.out.println(booking.getScreeningTime());
            // Menggabungkan tanggal saat ini dengan waktu screening dari booking
            Calendar currentDateTime = Calendar.getInstance();
//            System.out.println(currentDateTime);
            Calendar screeningDateTime = Calendar.getInstance();
            screeningDateTime.setTime(screeningTime);
            System.out.println("Screening Time :" + screeningTime);
            screeningDateTime.set(Calendar.YEAR, currentDateTime.get(Calendar.YEAR));
            screeningDateTime.set(Calendar.MONTH, currentDateTime.get(Calendar.MONTH));
            screeningDateTime.set(Calendar.DAY_OF_MONTH, currentDateTime.get(Calendar.DAY_OF_MONTH));

            Date time = screeningDateTime.getTime();
            System.out.println(time);
            System.out.println(currentTime);

            // Jika waktu screening sudah lewat dari waktu saat ini, maka booking sudah kadaluwarsa
            return time.before(currentTime);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
                    booking.setStatus("Cancel");
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

        if (!jwtUtils.isTokenValid(token)) {
            return responseErrorToken(null);
        }

        Optional<Movie> movie = movieRepository.findById(booking.getMovieId());
        
        if (movie.isEmpty()) {
            return responseErrorNotFound(null);
        }

        for (String seatNumber : booking.getSeatNumber()) {
            List<String> statuses = Arrays.asList("Aktif", "Berhasil");
            List<Booking> existingBookings = bookingRepository.findByMovieIdAndSeatNumberAndScreeningTimeAndStatus(
                    booking.getMovieId(), seatNumber, booking.getScreeningTime(), statuses);

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