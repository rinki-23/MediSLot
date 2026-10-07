package com.medislot.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medislot.entity.Appointmments;
@Repository
public interface AppointmentRepo extends JpaRepository<Appointmments, Integer>{
	
	public Optional<Appointmments> findById(int id);
	
	public List<Appointmments> findByPatientId(int patinetId);
	public List<Appointmments> findByDoctorId(int doctorId);
	
	

}
