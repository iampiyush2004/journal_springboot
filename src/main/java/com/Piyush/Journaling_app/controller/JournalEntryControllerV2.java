package com.Piyush.Journaling_app.controller;

import com.Piyush.Journaling_app.entity.JournalEntry;
import com.Piyush.Journaling_app.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController                                       // signifies controller
@RequestMapping("/journal")                           //works same like router from express
public class JournalEntryControllerV2 {


    @Autowired                                         //similar to inheritance
    private JournalEntryService journalEntryService;

    @GetMapping()
    public ResponseEntity<List<JournalEntry>> getAll(){                                                //localhost:8080 method : GET
    List<JournalEntry> found = journalEntryService.getAll();
    if(!found.isEmpty()){
        return new ResponseEntity<>(found , HttpStatus.OK);
    }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){//localhost:8080 method : GET

        try{
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> findEntryById(@PathVariable ObjectId myId){                         //localhost:8080 method : GET with Path Parameters


        Optional<JournalEntry> journalEntry =  journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId){                         //localhost:8080 method : Delete with Path Parameters
        journalEntryService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateEntryById(@PathVariable ObjectId myId , @RequestBody JournalEntry newEntry ){                         //localhost:8080 method : Put with Path Parameters
        JournalEntry old =  journalEntryService.findById(myId).orElse(null);

        if(old != null){
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.equals("") ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old , HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
