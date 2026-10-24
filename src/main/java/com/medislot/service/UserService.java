package com.medislot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medislot.entity.Doctors;
import com.medislot.entity.Patient;
import com.medislot.entity.RegisterRequest;
import com.medislot.entity.Role;
import com.medislot.entity.Users;
import com.medislot.exception.BadRequestException;
import com.medislot.exception.DuplicateResourceException;
import com.medislot.repo.DoctorRepo;
import com.medislot.repo.PatientRepo;
import com.medislot.repo.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PatientRepo patientRepo;
	
	@Autowired
	private DoctorRepo doctorRepo;
	
	@Transactional
	public Users register(RegisterRequest request){
		// check the given email is existed or not
		if(userRepo.existsByEmail(request.getEmail())) {
			throw new DuplicateResourceException("Email already exists");
		}
		// simple validation
		if(request.getName() == null || request.getName().isBlank() || request.getEmail() == null 
				|| request.getEmail().isBlank() || request.getPassword() == null || request.getPassword().isBlank()){
			throw new BadRequestException("Name, email and password are required");
		
		}
	
		if(request.getAge()<1 || request.getAge() > 120) {
			throw new BadRequestException("Enter a valid age");
		}
		
		if(request.getPhone() == null || request.getPhone().isBlank()) {
			throw new BadRequestException("Phone is required");
		}
		
		//  build the user from the request data
		Users user = new Users();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.PATIENT);
		
		Users saved = userRepo.save(user);
		
		Patient patient = new Patient();
			patient.setUser(saved);
			patient.setAge(request.getAge());
			patient.setPhone(request.getPhone());
			patientRepo.save(patient);

		return saved;
		
	}
	
	// find user by email
	public Optional<Users> findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	public List<Users> getAllUsers() {
		return userRepo.findAll();
	}
}
