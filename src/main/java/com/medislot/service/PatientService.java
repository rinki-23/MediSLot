package com.medislot.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medislot.entity.Patient;
import com.medislot.entity.Role;
import com.medislot.entity.UpdateProfileRequest;
import com.medislot.entity.Users;
import com.medislot.exception.BadRequestException;
import com.medislot.exception.DuplicateResourceException;
import com.medislot.exception.ResourceNotFoundException;
import com.medislot.repo.PatientRepo;
import com.medislot.repo.UserRepo;
import com.medislot.security.SecurityUtil;

import jakarta.transaction.Transactional;

@Service
public class PatientService {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PatientRepo patientRepo;
	
	public Patient createProfile(int id, Patient patient) {
		Users user = userRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("User id does not exists"));
		
		if(user.getRole() != Role.PATIENT) {
			throw new BadRequestException("User must be patient");
		}
		
		if(patientRepo.existsByUser(user)) {
			throw new DuplicateResourceException("Profile already exists");
		}
		
		patient.setUser(user);
		return patientRepo.save(patient);
		
		
	}
	
	
	public Patient getProfile(int id) {
		Patient patient = patientRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("User id does not exist"));
		
		SecurityUtil.checkOwner(patient.getUser().getEmail());
		return patient;
	}
	
	@Transactional
	public Patient updateProfile(int id, UpdateProfileRequest request) {
		Patient patient = patientRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Patient not found"));
		
		SecurityUtil.checkOwner(patient.getUser().getEmail());
		if(request.getAge()==null || patient.getAge()<1 || request.getAge()>120) {
			throw new BadRequestException("Age must be valid");
		}
		
		if(request.getPhone() == null || !request.getPhone().matches("\\d{10}")) {
			throw new BadRequestException("Phone must be 10 digits");
		}
		
		patient.setAge(request.getAge());
		patient.setPhone(request.getPhone());
		
		return patientRepo.save(patient);
		
	}
	public List<Patient> getAll() {
		return patientRepo.findAll();
	}
}
