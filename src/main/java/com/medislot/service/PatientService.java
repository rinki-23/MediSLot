package com.medislot.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medislot.entity.Patient;
import com.medislot.entity.Role;
import com.medislot.entity.Users;
import com.medislot.exception.BadRequestException;
import com.medislot.exception.DuplicateResourceException;
import com.medislot.exception.ResourceNotFoundException;
import com.medislot.repo.PatientRepo;
import com.medislot.repo.UserRepo;

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
	
	public List<Patient> getAll() {
		return patientRepo.findAll();
	}
}
