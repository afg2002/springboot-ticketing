package springticketing.springticketing.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springticketing.springticketing.models.ApiResponse;
import springticketing.springticketing.models.Movie;
import springticketing.springticketing.service.MovieService;
import springticketing.springticketing.utility.JwtUtils;


@RestController @RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600) // Ganti dengan origin yang diizinkan
public class MovieController {
    @Autowired
    MovieService movieService;

    @Autowired
    private JwtUtils jwtUtils;


    @DeleteMapping("/delete-movie/{id}")
    public ResponseEntity<Object> deleteMovie(@PathVariable String id, @RequestHeader("Authorization") String token) {
        ApiResponse response = movieService.deleteMovie(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add-movie")
    public ResponseEntity<Object> insertMovie(@RequestBody Movie movie, @RequestHeader("Authorization") String token) {
        ApiResponse response = movieService.saveMovie(movie);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-movies")
    public ResponseEntity<Object> getAllMovies() {
        ApiResponse response = movieService.getAllMovies();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-movies/{id}")
    public ResponseEntity<Object> getMovieById(@PathVariable String id) {
        ApiResponse response = movieService.getMovieByIdRes(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}