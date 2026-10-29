package com.medislot.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medislot.entity.DoctorRequest;
import com.medislot.entity.Doctors;
import com.medislot.entity.Role;
import com.medislot.entity.Users;
import com.medislot.exception.BadRequestException;
import com.medislot.exception.DuplicateResourceException;
import com.medislot.exception.ResourceNotFoundException;
import com.medislot.repo.DoctorRepo;
import com.medislot.repo.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class DoctorService {

	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private DoctorRepo doctorRepo;
	
	public Doctors createDoctorprofile(int id, Doctors doctor) {
		// find the user , if not found then throw exception
		Users user = userRepo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("User id does not exists"));
	
		// role must be doctor
		if(user.getRole() != Role.DOCTOR ) {
			throw new BadRequestException("Role of user must be doctor");
		}
		// this user must not have a doctor profile
		if(doctorRepo.existsByUser(user)) {
			throw new DuplicateResourceException("doctor profile already exists");
		}
		doctor.setUser(user);
		return doctorRepo.save(doctor);
	}
	
	// get all doctors
	public List<Doctors> getAllDoctors() {
		return doctorRepo.findAll();
	}
	
	// get doctor By id
	public Optional<Doctors> getById(int id) {
		return doctorRepo.findById(id);
	}
	
	@Transactional
	public Map<String, Object> createDoctor(DoctorRequest request) {
		if(request.getName() == null || request.getEmail() == null || request.getPassword() == null || 
				request.getSpecialization() == null) {
			throw new BadRequestException("All fields are required");
		}
		
		if(request.getFees() == 0) {
			throw new BadRequestException("Fees must be greater than zero");
		}
		
		if(request.getExperience()<0) {
			throw new BadRequestException("Experience cannot be negative");
		}
		
		if(userRepo.existsByEmail(request.getEmail())) {
			throw new DuplicateResourceException("Email already exists");
		}
		
		Users user = new Users();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.DOCTOR);
		
		userRepo.save(user);
		
		Doctors doctor = new Doctors();
		doctor.setUser(user);
		doctor.setSpecialization(request.getSpecialization());
		doctor.setExperience(request.getExperience());
		doctor.setFees(request.getFees());
		
		doctorRepo.save(doctor);
		return Map.of("message", "Doctor created successfully");
	}
	

}
