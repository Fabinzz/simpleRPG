package com.fabinzz.simplerpg.repository;

import com.fabinzz.simplerpg.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User, String> {


}
