package springticketing.springticketing.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import springticketing.springticketing.models.User;
import springticketing.springticketing.service.impl.UserServiceImpl;


@SpringBootTest
public class MyMongoServiceTest {


    @Autowired
    private UserServiceImpl userService;


    @Test
    public void testInsertUsers() {
        User user1 = new User();
        user1.setName("Naruto Uzumaki");
        user1.setUsername("naruto");
        user1.setPassword("rasengan");
        userService.saveUser(user1);

        User user2 = new User();
        user2.setName("Monkey D. Luffy");
        user2.setUsername("strawhat");
        user2.setPassword("gomugomu");
        userService.saveUser(user2);

        User user3 = new User();
        user3.setName("Goku");
        user3.setUsername("supersaiyan");
        user3.setPassword("kamehameha");
        userService.saveUser(user3);

        User user4 = new User();
        user4.setName("Eren Yeager");
        user4.setUsername("attacktitan");
        user4.setPassword("freedom");
        userService.saveUser(user4);

        User user5 = new User();
        user5.setName("Levi Ackerman");
        user5.setUsername("captainlevi");
        user5.setPassword("cleanfreak");
        userService.saveUser(user5);

        User user6 = new User();
        user6.setName("Asuna Yuuki");
        user6.setUsername("lightningstrike");
        user6.setPassword("swordskeill");
        userService.saveUser(user6);

        User user7 = new User();
        user7.setName("Edward Elric");
        user7.setUsername("fullmetal");
        user7.setPassword("alchemy");
        userService.saveUser(user7);

        User user8 = new User();
        user8.setName("Mikasa Ackerman");
        user8.setUsername("scarfwarrior");
        user8.setPassword("erendere");
        userService.saveUser(user8);

        User user9 = new User();
        user9.setName("Itachi Uchiha");
        user9.setUsername("anbulegend");
        user9.setPassword("genjutsu");
        userService.saveUser(user9);

        User user10 = new User();
        user10.setName("Saitama");
        user10.setUsername("onepunchman");
        user10.setPassword("herofornothing");
        userService.saveUser(user10);
    }


}
