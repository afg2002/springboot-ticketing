package springticketing.springticketing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import springticketing.springticketing.models.ApiResponse;
import springticketing.springticketing.models.User;
import springticketing.springticketing.repository.UserRepository;
import springticketing.springticketing.service.UserService;
import springticketing.springticketing.utility.JwtUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl extends ResponseServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

;

    public ApiResponse authenticateUser(String username, String password)  {
        ApiResponse response;
        try{
            User user = userRepository.findByUsername(username);
            if (user != null){
                if(passwordEncoder.matches(password, user.getPassword())){
                    String token = jwtUtils.generateJwtToken(user.getUsername());
                    Map<String, Object> data = new HashMap<>();
                    data.put("token", token);
                    data.put("userId", user.getId());
                    data.put("username", user.getUsername());
                    data.put("name", user.getName());
                    response = responseSuccess(data);
                }else{
                    response = responseErrorNotMatch(null);
                }
            }else{
                response = responseErrorNotFound(null);
            }
        }catch (Exception e){
            e.printStackTrace();
            response = responseErrorGeneral(e.getMessage());

        }
        return response;
    }

    public User saveUser(User user){
        try{

            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

        }catch (Exception e){
            e.printStackTrace();
        }
        return userRepository.save(user);
    }

    public Optional<User> findByIdWithoutPassword(String id){
        return userRepository.findByIdWithoutPassword(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}