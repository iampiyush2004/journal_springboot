package com.Piyush.Journaling_app.controller;

import com.Piyush.Journaling_app.entity.JournalEntry;
import com.Piyush.Journaling_app.entity.User;
import com.Piyush.Journaling_app.service.JournalEntryService;
import com.Piyush.Journaling_app.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController                                       // signifies controller
@RequestMapping("/user")                           //works same like router from express
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveEntry(user);
    }



    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser( @RequestBody User user , @PathVariable String userName){                         //localhost:8080 method : Put with Path Parameters
        User userInDb = userService.findByUsername(userName);

        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }



}
