package com.medislot.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medislot.entity.DoctorRequest;
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
}
