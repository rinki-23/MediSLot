package com.medislot.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medislot.entity.Doctors;
import com.medislot.entity.Users;

@Repository
public interface DoctorRepo extends JpaRepository<Doctors, Integer>{

	public Optional<Doctors> findById(int id);
	
	public Doctors findBySpecialization(String specialization);
	
	boolean existsByUser(Users user);
}
