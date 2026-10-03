package com.medislot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medislot.entity.Appointmments;
@Repository
public interface AppointmentRepo extends JpaRepository<Appointmments, Integer>{
	
	public Appointmments findById(int id);
	
	

}
