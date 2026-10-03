package com.medislot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medislot.entity.Doctors;
import com.medislot.service.DoctorService;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;
	
	@PostMapping("/profile/{id}")
	public Doctors doctorProfile(@PathVariable("id") int id, @RequestBody Doctors doctor) {
		return doctorService.createDoctorprofile(id, doctor);
	}
	
	@GetMapping("/getall")
	public List<Doctors> getAllDoctor(){
		List<Doctors> list = doctorService.getAllDoctors();
		return list;
	}
	@GetMapping("/get/{id}")
	public Doctors getDoctorById(@PathVariable("id") int id) {
		return doctorService.getById(id);
	}
}
