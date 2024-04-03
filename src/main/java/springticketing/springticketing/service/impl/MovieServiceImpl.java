package springticketing.springticketing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springticketing.springticketing.models.ApiResponse;
import springticketing.springticketing.models.Movie;
import springticketing.springticketing.repository.MovieRepository;
import springticketing.springticketing.request.TokenHolderRequest;
import springticketing.springticketing.service.MovieService;
import springticketing.springticketing.utility.JwtUtils;

import java.util.List;
import java.util.Optional;


@Service
public class MovieServiceImpl extends ResponseServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TokenHolderRequest tokenHolderRequest;

    @Override
    public ApiResponse getAllMovies() {
        ApiResponse response;
        String token = tokenHolderRequest.getToken();
        try{
            if(jwtUtils.isTokenValid(token)){
                List<Movie> movie = movieRepository.findAll();
                response = responseSuccess(movie);
            }else{
                response = responseErrorToken(null);
            }
        }catch (Exception e){
            response = responseErrorGeneral(null);
        }
        return response;
    }

    @Override
    public ApiResponse getMovieByIdRes(String id) {
        ApiResponse response;

        String token = tokenHolderRequest.getToken();
        try{
            if(jwtUtils.isTokenValid(token)){
                Movie movie = movieRepository.findById(id).orElse(null);
                response = responseSuccess(movie);
            }else{
               response =  responseErrorToken(null);
            }
        }catch (Exception e){
             response = responseErrorToken(null);
        }
        return response;
    }

    @Override
    public ApiResponse saveMovie(Movie movie) {
        ApiResponse response;

        String token = tokenHolderRequest.getToken();
        try{
            if(jwtUtils.isTokenValid(token)){
                Movie savedMovie = movieRepository.save(movie);
                response = responseSuccess(savedMovie);
            }else{
                response = responseErrorToken(null);
            }
        }catch (Exception e){
            e.printStackTrace();
            response = responseErrorGeneral(null);
        }
        return response;
    }

    public ApiResponse deleteMovie(String id) {
        ApiResponse response;

        String token = tokenHolderRequest.getToken();
        try{
            if(jwtUtils.isTokenValid(token)){
                Optional<Movie> movie = movieRepository.findById(id);
                Movie existedMovie = movie.get();
                if(movie.isEmpty()){
                    response = responseErrorNotFound(null);
                }else{
                    response = responseSuccess(existedMovie);
                }
            }else{
                response = responseErrorToken(null);
            }
        }catch (Exception e){
            e.printStackTrace();
            response = responseErrorGeneral(e.getMessage());
        }

        return response;
    }

}
