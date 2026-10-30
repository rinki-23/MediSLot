package com.medislot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medislot.entity.DoctorRequest;
import com.medislot.entity.Doctors;
import com.medislot.entity.StatusRequest;
import com.medislot.service.DoctorService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private DoctorService doctorService;
	
	@PostMapping("/doctors")
	public Map<String, Object> createDoctor(@RequestBody DoctorRequest request){
		return doctorService.createDoctor(request);
	}
	
	@GetMapping("/doctors")
	public List<Doctors> getAllDoctorsForAdmin(){
		return doctorService.getAllDoctorsForAdmin();
	}
	
	@PutMapping("/doctors/{id}/status")
	public Map<String, Object> setDoctor(@PathVariable int id, @RequestBody StatusRequest status) {
		return doctorService.setDoctorStatus(id, status.getActive());
		
	}
}
