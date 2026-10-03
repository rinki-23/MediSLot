package com.medislot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medislot.entity.Users;
import com.medislot.repo.UserRepo;

@Service
public class UserService {
	
	
	@Autowired
	private UserRepo userRepo;
	
	public Users register(Users user) {
		// check the given email is existed or not
		if(userRepo.existsByEmail(user.getEmail())) {
			throw new RuntimeException("Email already exist");
		}
		System.out.println("Successfully done service");
		return userRepo.save(user);
		
	}
	
	// find user by email
	public Optional<Users> findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
}
