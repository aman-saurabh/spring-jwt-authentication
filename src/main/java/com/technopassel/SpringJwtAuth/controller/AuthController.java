package com.technopassel.SpringJwtAuth.controller;

import com.technopassel.SpringJwtAuth.model.User;
import com.technopassel.SpringJwtAuth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String home() {
        User user = new User();
        user.setUserName("Aman");
        user.setPassword("aman@123");
        user.setActive(true);
        user.setRoles("Role_User");
        user.setActive(true);
        userRepository.save(user);
        return "User created successfully";
    }
}
