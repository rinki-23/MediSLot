package com.medislot.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medislot.entity.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer>{
	
	Optional<Users> findByEmail(String email);
	
	// check the login email is registered or not
	boolean existsByEmail(String email);
	
	public Optional<Users> findById(int id);

}
