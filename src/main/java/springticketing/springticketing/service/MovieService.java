package springticketing.springticketing.service;

import springticketing.springticketing.models.ApiResponse;
import springticketing.springticketing.models.Movie;

public interface MovieService {
    public ApiResponse getAllMovies();
    public ApiResponse getMovieByIdRes(String id);

    public ApiResponse saveMovie(Movie movie);

    public ApiResponse deleteMovie(String id);
}
