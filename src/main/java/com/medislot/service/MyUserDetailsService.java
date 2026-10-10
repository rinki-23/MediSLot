package com.medislot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.medislot.entity.Users;
import com.medislot.repo.UserRepo;

// this class is used for give username and password from our database
@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//"UsernameNotFoundException" is the spring security own exception, so it turns into 401
		
		Users user = userRepo.findByEmail(username)
			.orElseThrow(()->new UsernameNotFoundException("User not found"));
	
	// spring does not understand our Users entity
	//so we convert it into UserDetails Object
	return User.withUsername(user.getEmail())
			.password(user.getPassword())  // already hashed in the database, do not encode again
			.roles(user.getRole().name())
			.build();
	
	
	}
	
	

}
