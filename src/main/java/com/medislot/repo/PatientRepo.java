package com.medislot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medislot.entity.Patient;
import com.medislot.entity.Users;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer>{
	
	public Patient findById(int id);
	boolean existsByUser(Users user);
}
