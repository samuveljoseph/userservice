package com.ecommerce.userservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.userservice.model.User;

@Repository
public interface UserRepo extends MongoRepository<User,String>{
	@Query("{username:?0}")
	Optional<User> existByUserName(String username);

	@Query("{emailId:?0}")
	Optional<User> existByEmailId(String emailId);
}
