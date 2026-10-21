package com.medislot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medislot.entity.Patient;
import com.medislot.entity.Users;
import com.medislot.exception.ResourceNotFoundException;
import com.medislot.repo.PatientRepo;
import com.medislot.repo.UserRepo;
import com.medislot.security.JwtUtil;
import com.medislot.security.LoginRequest;
import com.medislot.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PatientRepo patientRepo;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserRepo userRepo;
	
	@PostMapping("/register")
	public Users register( @RequestBody Users user) {
		
		return userService.register(user);
	}
	
	@GetMapping("/getAll")
	public List<Users> getAll(){
		return userService.getAllUsers();
	}
	
	
	// step 3

	@PostMapping("/login")
	public ResponseEntity<?> token(@RequestBody LoginRequest request){
		// Step1: verify email and password, 
		// if the password is wrong, this line throws BadCredentialsException
		//and the code below it never runs
		String email = request.getEmail();
		String password = request.getPassword();
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(email, password));
		
		// step 2 : password is correct , so load the user from DB to get role
		Users user = userRepo.findByEmail(email)
		.orElseThrow(()-> new ResourceNotFoundException("User not found"));
		
		// step3 : convert the enum role to string
		String role = user.getRole().name();
		
		//Step 4: create the jwt token using email and role
		String token = jwtUtil.generateToken(email, role);
		
		Map<String, Object> response = new HashMap<>();
		if(role.equals("PATIENT")) {
			Patient patient = patientRepo.findByUserId(user.getId())
					.orElseThrow(()-> new ResourceNotFoundException("ROle must be equals to Patinet"));
			response.put("profileId", patient.getId());
		}
		// send the token back as JSON {"token", "eyJ..."}
		
		response.put("token", token);
		response.put("role", role);
		
		return ResponseEntity.ok(response);
	}
}
