package com.Piyush.Journaling_app.controller;

import com.Piyush.Journaling_app.entity.JournalEntry;
import com.Piyush.Journaling_app.entity.User;
import com.Piyush.Journaling_app.repository.UserRepository;
import com.Piyush.Journaling_app.service.JournalEntryService;
import com.Piyush.Journaling_app.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController                                       // signifies controller
@RequestMapping("/user")                           //works same like router from express
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PutMapping
    public ResponseEntity<?> updateUser( @RequestBody User user ){                         //localhost:8080 method : Put with Path Parameters

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User userInDb = userService.findByUsername(userName);


            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping
    public  ResponseEntity<?> deleteUserById( @RequestBody User user ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
