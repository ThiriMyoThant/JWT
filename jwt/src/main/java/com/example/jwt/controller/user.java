package com.example.jwt.controller;

//import com.example.jwt.service.MyUserDetailService;
//import com.example.jwt.util.JwtUtil;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
import com.example.jwt.model.AuthenticationRequest;
import com.example.jwt.model.AuthenticationResponse;
import com.example.jwt.service.MyUserDetailService;
import com.example.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController

public class user {

    @Autowired
    private AuthenticationManager authenticationManager ;
    @Autowired
    private JwtUtil jwtToken;
    @Autowired
    private MyUserDetailService userDetailsService;

   // @RequestMapping(value = "/test",method = RequestMethod.GET)
   @RequestMapping({ "/hello" })
    public String firstPage(){
        return "hello World";
    }

    @RequestMapping(value = "/authenticate" , method = RequestMethod.POST)
    public ResponseEntity<?> createAuthtoken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException e)
        {
            throw  new Exception("Incorrect Username and Password");
        }

        final UserDetails userDetails= userDetailsService.
                loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtToken.generateToken(userDetails);
        return  ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
