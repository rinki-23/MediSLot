package com.medislot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medislot.entity.Patient;
import com.medislot.service.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	@PostMapping("/profile/{id}")
	public Patient createProfile(@PathVariable("id") int id, @RequestBody Patient patient) {
		return patientService.createProfile(id, patient);
	}
	
	@GetMapping("/getAll")
	public List<Patient> getAll() {
		return patientService.getAll();
	}
}
