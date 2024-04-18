package springticketing.springticketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springticketing.springticketing.models.ApiResponse;
import springticketing.springticketing.models.Booking;
import springticketing.springticketing.service.BookingService;
import springticketing.springticketing.service.MovieService;
import springticketing.springticketing.service.UserService;
import springticketing.springticketing.utility.JwtUtils;



@RestController @RequestMapping("/api/booking")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600) // Ganti dengan origin yang diizinkan
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    UserService userService;

    @Autowired
    MovieService movieService;

    @Autowired
    private JwtUtils jwtUtils;

    @DeleteMapping("/delete-booking/{id}")
    public ResponseEntity<Object> cancelBooking(@PathVariable String id) {
        ApiResponse response = bookingService.cancelBooking(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update-booking/{id}")
    public ResponseEntity<Object> updateBooking(@PathVariable String id, @RequestBody Booking updatedBookingData) {
        ApiResponse response = bookingService.updateBooking(id, updatedBookingData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-booking/{id}")
    public ResponseEntity<Object> getBookingById(@PathVariable String id){
        ApiResponse response = bookingService.getBookingByIdRes(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add-booking")
    public ResponseEntity<Object> insertBooking(@RequestBody Booking booking) {
        ApiResponse response = bookingService.saveBooking(booking);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/list-booking")
    public ResponseEntity<Object> getAllBookings(){
        ApiResponse response = bookingService.getAllBookings();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/list-booking/{movieId}/{screeningTime}")
    public ResponseEntity<Object> getBookingsByMovieIdAndScreeningTime
        (@PathVariable String movieId, @PathVariable String screeningTime){
        ApiResponse response = bookingService.getBookingByMovieIdAndScreeningTime(movieId,screeningTime);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    @GetMapping("/canceled")
    public ResponseEntity<Object> getAllCanceledBookings() {
        ApiResponse response = bookingService.getAllCanceledBookings();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/expired-bookings")
    public ResponseEntity<Object> getAllExpiredBookings(){
        ApiResponse response = bookingService.getAllExpiredBookingsAndUpdateStatus();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
