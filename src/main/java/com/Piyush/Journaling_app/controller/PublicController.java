package com.Piyush.Journaling_app.controller;

import com.Piyush.Journaling_app.entity.User;
import com.Piyush.Journaling_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("create-user")
    public void createUser(@RequestBody User user){
        userService.saveEntry(user);
    }

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Ok";
    }
}
