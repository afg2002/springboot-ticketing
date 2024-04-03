package springticketing.springticketing.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600) // Ganti dengan origin yang diizinkan
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "Selamat datang di API Movie";
    }
}
