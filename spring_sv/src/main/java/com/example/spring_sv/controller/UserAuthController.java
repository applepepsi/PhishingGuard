package com.example.spring_sv.controller;


import com.example.spring_sv.repository.UserRepository;



import com.example.spring_sv.model.Link;
import com.example.spring_sv.model.User;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserAuthController {
    
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;

    public UserAuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    @PostMapping("/link/post")
    public String linkPost(@RequestBody Link L) 
    {
        System.out.println("Link Reserch from Android");
		System.out.println("Link: " + L.getLink()); 
        String responseText = Python_Server_Post.sendPostRequest(L);
        
        String result = responseText.replaceAll("Received link: ", "");
        System.out.println("통신결과:"+result);
        // return m.getLink();
        return result;
    }
    @PostMapping("/login/post")
    public String loginPost(@RequestBody User U) 
    {

        System.out.println("Login Request from Android");
		System.out.println("UserName: " + U.getUsername()); 
        System.out.println("PassWord: " + U.getPassword()); 
        
        String password=U.getPassword();
        
   
        User user = userRepository.findByUsername(U.getUsername());
        if (user != null) {
            // Long userId=user.getNum();
            // String savedUsername=user.getUsername();
            String savedPassword = user.getPassword();
            // String savedRealname=user.getRealname();
            System.out.println("db에 저장된 pw: "+savedPassword);
            if (password.equals(savedPassword)) 
            {
                String token = jwtProvider.createToken(user);
                System.out.println(U.getUsername()+"사용자 로그인 성공");
                return token;
                // return "1";
            }
            else 
            {
                System.out.println("비밀번호 틀림");
                System.out.println("보낸 패스워드:"+password);
                System.out.println("저장된 패스워드:"+savedPassword);
                return "0";
            }
        } else {
            return "0";
        }
    }
    @GetMapping("/check/get")
    public String checkUser(@RequestHeader("Authorization") String authorizationHeader) 
    {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            
            String user = jwtProvider.getAccount(token);
            System.out.println("토큰값: "+token);
            System.out.println("사용자: "+user);

            return "1";
        } else {
            System.out.println("잘못된 토큰값: " + authorizationHeader);
            return "0"; 
        }
    }

    @PostMapping("/register/post")
    public String registerPost(@RequestBody User U) 
    {
        System.out.println("Login Request from Android");
		System.out.println("UserName: " + U.getUsername()); 
        System.out.println("PassWord: " + U.getPassword()); 
        System.out.println("RealName: " + U.getRealname()); 


        User user = userRepository.findByUsername(U.getUsername());
        if (user != null && U.getUsername().equals(user.getUsername())) 
        {
            System.out.println("username중복 발생"); 
            return "0";
        } else 
        {

            System.out.println("저장 성공"); 
            userRepository.save(U);
            return "1";
        }
    }
    

	
}


