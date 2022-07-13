package com.ecommerce.userservice.serviceimplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.userservice.converter.UserConverter;
import com.ecommerce.userservice.dto.UserDto;
import com.ecommerce.userservice.exception.UserException;
import com.ecommerce.userservice.model.User;
import com.ecommerce.userservice.repository.UserRepo;
import com.ecommerce.userservice.service.UserService;

@Service
public class UserServiceImplementation implements UserService {
	@Autowired
	UserRepo userRepo;
	@Autowired
	UserConverter converter;

	public UserDto signUp(UserDto userDto) throws UserException {
		if (userRepo.existByUserName(userDto.getUserName()).isPresent()) {
			throw new UserException("Error: user name exist!!");
		}
		if (userRepo.existByEmailId(userDto.getEmailId()).isPresent()) {
			throw new UserException("Error: emailId exist!!");
		}
		User user = converter.dtoToUser(userDto);
		user = userRepo.save(user);
		return converter.userToDto(user);
	}

	public UserDto editUserDetails(String id, UserDto userDto) throws UserException {
		
		if (userRepo.findById(id).isPresent()) {
			Optional<User> userData = userRepo.findById(id);
			User user = converter.dtoToUserUpdate(userDto, userData.get());
			user = userRepo.save(user);
			return converter.userToDto(user);
		} else {
			throw new UserException("Error user not found");
		}
	}

	public void deleteUserDetails(String id) throws UserException {
		if (userRepo.findById(id).isPresent()) {
			userRepo.deleteById(id);
		} else {
			throw new UserException("Error user not found!!");
		}
	}

	public UserDto userDetails(String id) throws UserException {
		
		if (userRepo.findById(id).isPresent()) {
			Optional<User> dto = userRepo.findById(id);
			return converter.userToDto(dto.get());
		} else {
			throw new UserException("Error user not found!");
		}
	}

	public List<UserDto> getAllUserDetails() {
		List<User> findall = userRepo.findAll();
		return converter.userToDto(findall);
	}
}
