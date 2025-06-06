package com.Piyush.Journaling_app.repository;

import com.Piyush.Journaling_app.entity.JournalEntry;
import com.Piyush.Journaling_app.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
        User findByUserName(String userName);
        User deleteByUserName(String userName);

}
