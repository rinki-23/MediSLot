package com.medislot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medislot.entity.Appointmentstatus;
import com.medislot.entity.Appointmments;
import com.medislot.entity.Patient;
import com.medislot.entity.SlotStatus;
import com.medislot.entity.Slots;
import com.medislot.exception.BadRequestException;
import com.medislot.exception.DuplicateResourceException;
import com.medislot.exception.ResourceNotFoundException;
import com.medislot.repo.AppointmentRepo;
import com.medislot.repo.PatientRepo;
import com.medislot.repo.SlotRepo;

import jakarta.transaction.Transactional;

@Service
public class AppointmentService {

	@Autowired
	private PatientRepo patientRepo;
	
	@Autowired
	private SlotRepo slotRepo;
	
	@Autowired
	private AppointmentRepo appointmentRepo; 
	
	@Transactional
	public Appointmments bookAppointments(int patienttid, int slotid) {
		Patient patient = patientRepo.findById(patienttid)
				.orElseThrow(()->new ResourceNotFoundException("Patient id not found"));
	
		Slots slot = slotRepo.findById(slotid)
				.orElseThrow(()-> new ResourceNotFoundException("Slot not found"));
		
		if(slot.getStatus() != SlotStatus.AVAILABLE) {
			throw new BadRequestException("Slot already booked");
		}
		
		Appointmments app = new Appointmments();
		app.setPatient(patient);
		app.setSlot(slot);
		app.setDoctor(slot.getDoctor());
		app.setStatus(Appointmentstatus.BOOKED);
		
		slot.setStatus(SlotStatus.BOOKED);
		slotRepo.save(slot);
		return appointmentRepo.save(app);
		
		
	}
	
	// cancel appointments
	@Transactional
	public Appointmments cancelAppointments(int id) {
		// find the appointment, throw if does not exist
		Appointmments app = appointmentRepo.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("Appointment not found"));
		
		// block double cancel, otherwise the slot could be freed twice
		if(app.getStatus() == Appointmentstatus.CANCELLED) {
			throw new BadRequestException("Already Cancelled");
		}
		app.setStatus(Appointmentstatus.CANCELLED);
		
		// get the slot linked to this appointment
		Slots slot = app.getSlot();
		// free the slot so another patient can book it
		slot.setStatus(SlotStatus.AVAILABLE);
		// save both changes
		slotRepo.save(slot);
		return appointmentRepo.save(app);
	}
	
	//view methods
	public List<Appointmments> viewPatient(int id) {
		return appointmentRepo.findByPatientId(id);
	}
	
	public List<Appointmments> viewDoctor(int id) {
		return appointmentRepo.findByDoctorId(id);
	}
}
