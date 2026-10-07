package com.medislot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medislot.entity.Appointmments;
import com.medislot.service.AppointmentService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	
	@PostMapping("/book/{pid}/{sid}")
	public Appointmments bookAppointment(@PathVariable("pid") int pid, @PathVariable("sid") int sid) {
		return appointmentService.bookAppointments(pid, sid);
	}
	
	@PutMapping("/cancel/{id}")
	public Appointmments cancel(@PathVariable("id") int id) {
		return appointmentService.cancelAppointments(id);
	}
	
	@GetMapping("/patient/{id}")
	public List<Appointmments> viewPatient(@PathVariable("id") int id){
		return appointmentService.viewPatient(id);
	}
	
	@GetMapping("/doctor/{id}")
	public List<Appointmments> viewDoctor(@PathVariable("id") int id){
		return appointmentService.viewDoctor(id);
	}
	
	
}
