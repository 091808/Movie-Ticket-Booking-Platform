package com.jts.Movie.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jts.Movie.Controller.UserController;
import com.jts.Movie.convertor.UserConvertor;
import com.jts.Movie.entities.User;
import com.jts.Movie.repositories.UserRepository;
import com.jts.Movie.request.UserRequest;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public String addUser(UserRequest userRequest) {
		Optional<User> users = userRepository.findByEmailId(userRequest.getEmailId());
		
		if(users.isPresent()) {
			throw new UserExist();
		}
		
		User user = UserConvertor.userDtoToUser(userRequest, passwordEncoder.encode("1234"));
		
		userRepository.save(user);
		return "User Saved Successfully";
	}

}
