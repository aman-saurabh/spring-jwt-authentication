package com.technopassel.SpringJwtAuth.controller;

import com.technopassel.SpringJwtAuth.model.AuthRequest;
import com.technopassel.SpringJwtAuth.model.AuthResponse;
import com.technopassel.SpringJwtAuth.model.User;
import com.technopassel.SpringJwtAuth.repository.UserRepository;
import com.technopassel.SpringJwtAuth.service.MyUserDetailsService;
import com.technopassel.SpringJwtAuth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    //Don't use bCryptPasswordEncoder here as we have already modified PasswordEncoder(in SecurityConfigurer.java file) to use it everywhere.

    @Autowired
    AuthenticationManager authenticationManager;
    //Will use it to authenticate user using username and password.We won't create any custom function for that purpose.

    @Autowired
    MyUserDetailsService myUserDetailsService;
    //Will use it to get userDetails.As we have created it by modifying Spring Security's UserDetailsService which is made for same purpose.

    @Autowired
    JwtUtil jwtUtil;
    //Custom Utility create for handling JWT related tasks.

    @GetMapping("/addUser")
    public String home() {
        User user = new User();
        user.setUserName("Gitika");
        user.setPassword(passwordEncoder.encode("saurabh321"));
        user.setActive(true);
        user.setRoles("Role_Admin");
        userRepository.save(user);
        return "User created successfully";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            //We will not make custom function to verify user.
            //Rather we should use spring security's AuthenticationManagers's authenticate function to authenticate users.
            //As in this method everything is implemented out of the box,we just need to pass an object of "UsernamePasswordAuthenticationToken" class as argument
        } catch (BadCredentialsException e) {
            //If Authentication manager cannot verify user then it will throw "BadCredentialsException" and comes in catch block.
            throw new Exception("Incorrect username and password", e);
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(jwt, "success"));
    }
}
