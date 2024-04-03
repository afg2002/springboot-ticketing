package springticketing.springticketing.models;


import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.OneToOne;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "movies")

public class Movie {
    @Id
    private String id;
    private String title;
    private String poster;
    private String description;
    private String genre;

    private Date releaseDate;
    private Double rating;

    private Boolean isShowed = true;

}
