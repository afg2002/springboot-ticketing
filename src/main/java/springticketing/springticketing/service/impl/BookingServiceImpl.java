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
        public ApiResponse getAllExpiredBookingsAndUpdateStatus() {
            ApiResponse response;
            try {
                // Mendapatkan waktu saat ini
                Date currentTime = new Date();
                // Ambil semua booking
                List<Booking> allBookings = bookingRepository.findAll();

                // Daftar booking yang telah kedaluwarsa
                List<Booking> expiredBookings = allBookings.stream()
                        // Filter booking yang memiliki screeningDate sudah kadaluarsa dan status masih aktif
                        .filter(booking -> isBookingExpired(booking, currentTime) && booking.getStatus().equalsIgnoreCase("Aktif"))
                        .collect(Collectors.toList());

                // Mengupdate status booking menjadi "Berhasil" untuk setiap booking yang telah kedaluwarsa
                for (Booking booking : expiredBookings) {
                    booking.setStatus("Berhasil");
                    bookingRepository.save(booking); // Menyimpan perubahan status booking ke database
                }

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
            Date bookingScreeningDate = booking.getScreeningDate();
            String screeningTime = booking.getScreeningTime();
            String[] arrHoursAndMinutesSeparator = screeningTime.split(":");
            bookingScreeningDate.setHours(Integer.parseInt(arrHoursAndMinutesSeparator[0]));
            bookingScreeningDate.setMinutes(Integer.parseInt(arrHoursAndMinutesSeparator[1]));
            System.out.println(bookingScreeningDate);
            System.out.println(currentTime);

            // Jika waktu screening sudah lewat dari waktu saat ini, maka booking sudah kadaluwarsa
            return bookingScreeningDate.before(currentTime);
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

            // Mengumpulkan semua nomor kursi dari booking yang baru
            List<String> seatNumbers = booking.getSeatNumber();
            System.out.println(booking);
            System.out.println(seatNumbers);
            System.out.println(booking.getMovieId());

            // Mencari semua booking yang memiliki nomor kursi yang sama dengan booking yang baru ditambahkan
                List<Booking> existingBookings = bookingRepository.findByMovieIdAndSeatNumberAndScreeningTimeAndStatusIn(
                    booking.getMovieId(), seatNumbers, booking.getScreeningTime(),Arrays.asList("Aktif", "Berhasil")
                );

            System.out.println( existingBookings);

            // Jika ada booking yang sudah ada dengan nomor kursi yang sama, kembalikan respons error
            if (!existingBookings.isEmpty()) {
                return responseErrorDuplicate(null);
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

