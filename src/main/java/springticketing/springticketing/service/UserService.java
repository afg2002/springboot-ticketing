package springticketing.springticketing.service;

import org.springframework.stereotype.Service;
import springticketing.springticketing.models.ApiResponse;
import springticketing.springticketing.models.Booking;
import springticketing.springticketing.models.User;

import java.util.Optional;

public interface UserService {
    public ApiResponse authenticateUser(String username, String password);
    public Optional<User> findByIdWithoutPassword(String id);

    public User findByUsername(String username);
}
