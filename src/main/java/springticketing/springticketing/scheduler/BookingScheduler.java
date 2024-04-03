package springticketing.springticketing.scheduler;

import java.time.LocalDate;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import springticketing.springticketing.models.ApiResponse;
import springticketing.springticketing.models.Booking;
import springticketing.springticketing.service.BookingService;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookingScheduler {
    
    @Autowired
    private BookingService bookingService;

      
    @Scheduled(cron = "0 */1 * ? * *")
    public void updateExpiredBookings() {
        ApiResponse expiredBookingsResponse = bookingService.getAllBookings();

        List<Booking> expiredBookings = (List<Booking>) expiredBookingsResponse.getData();

        Date currentDate = new Date();

        for (Booking booking : expiredBookings) {
            if (isBookingExpired(booking, currentDate)) {
                updateBookingStatusToSuccess(booking);
            }
        }
    }
    
    private boolean isBookingExpired(Booking booking, Date currentDate) {
        return booking.getScreeningDate().before(currentDate) && booking.getStatus().equals("Aktif");
    }
    
    private void updateBookingStatusToSuccess(Booking booking) {
        booking.setStatus("Success");
        bookingService.updateBooking(booking.getId(), booking);
    }
}
