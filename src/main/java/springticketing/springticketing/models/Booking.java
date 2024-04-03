package springticketing.springticketing.models;


import io.github.kaiso.relmongo.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;
    private String seatNumber;
    private Date screeningDate;
    private String screeningTime;
    private boolean isCanceled = false;
    private String movieId;
    private String userId;

}
