package springticketing.springticketing.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springticketing.springticketing.models.ApiResponse;
import springticketing.springticketing.request.LoginRequest;
import springticketing.springticketing.service.UserService;
import springticketing.springticketing.utility.JwtUtils;


@RestController @RequestMapping("/api/users")
public class UserController {
   @Autowired
    private UserService userService;

   @Autowired
    private JwtUtils jwtUtils;

   @PostMapping("/login")
    public ResponseEntity<Object>loginUser(@RequestBody LoginRequest loginRequest){
       String username  = loginRequest.getUsername();
       String password  = loginRequest.getPassword();
       ApiResponse response = userService.authenticateUser(username, password);
       return new ResponseEntity<>(response, HttpStatus.OK);
   }

}
