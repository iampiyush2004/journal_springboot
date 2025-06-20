package com.Piyush.Journaling_app.service;

import com.Piyush.Journaling_app.entity.JournalEntry;
import com.Piyush.Journaling_app.entity.User;
import com.Piyush.Journaling_app.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;



    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try{
            User user = userService.findByUsername(userName);
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }
        catch(Exception e){
            System.out.println(e);
            throw new RuntimeException( "An error occured while saving the entry!" , e);
        }

    }

    public void saveEntry(JournalEntry journalEntry){

        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public void deleteById(ObjectId id, String userName){
        try {
            User user = userService.findByUsername(userName);
            boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveNewEntry(user);
                journalEntryRepository.deleteById(id);
            }

        }
        catch(Exception e){
                System.out.println(e);
                throw new RuntimeException("An error occured while deleting the entry!");
            }
        }



}


//controller ------> service ------> repository ------> mongoRepo functions
