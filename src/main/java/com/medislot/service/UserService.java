package com.medislot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.medislot.entity.Users;
import com.medislot.exception.DuplicateResourceException;
import com.medislot.repo.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userRepo;
	
	public Users register(Users user){
		// check the given email is existed or not
		if(userRepo.existsByEmail(user.getEmail())) {
			throw new DuplicateResourceException("Email already exists");
		}
		String hashed = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashed);
		return userRepo.save(user);
		
	}
	
	// find user by email
	public Optional<Users> findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	public List<Users> getAllUsers() {
		return userRepo.findAll();
	}
}
