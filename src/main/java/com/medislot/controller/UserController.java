package com.medislot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medislot.entity.Users;
import com.medislot.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public Users register( @RequestBody Users user) {
		
		return userService.register(user);
	}
	
	@GetMapping("/getAll")
	public List<Users> getAll(){
		return userService.getAllUsers();
	}
}
