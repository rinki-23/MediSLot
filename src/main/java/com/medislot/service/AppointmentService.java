package com.medislot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medislot.entity.Appointmentstatus;
import com.medislot.entity.Appointmments;
import com.medislot.entity.Doctors;
import com.medislot.entity.Patient;
import com.medislot.entity.SlotStatus;
import com.medislot.entity.Slots;
import com.medislot.exception.BadRequestException;
import com.medislot.exception.DuplicateResourceException;
import com.medislot.exception.ForbiddenException;
import com.medislot.exception.ResourceNotFoundException;
import com.medislot.repo.AppointmentRepo;
import com.medislot.repo.DoctorRepo;
import com.medislot.repo.PatientRepo;
import com.medislot.repo.SlotRepo;
import com.medislot.security.SecurityUtil;

import jakarta.transaction.Transactional;

@Service
public class AppointmentService {

	@Autowired
	private PatientRepo patientRepo;
	
	@Autowired
	private SlotRepo slotRepo;
	
	@Autowired
	private AppointmentRepo appointmentRepo; 
	
	@Autowired
	private DoctorRepo doctorRepo;
	
	@Transactional
	public Appointmments bookAppointments(int patienttid, int slotid) {
		Patient patient = patientRepo.findById(patienttid)
				.orElseThrow(()->new ResourceNotFoundException("Patient id not found"));
	
		// only the owner of this patient profile can book for it
		SecurityUtil.checkOwner(patient.getUser().getEmail());
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
		
		// for jwt
		//only the patient or doctor of this appointment may cancel it
		String me = SecurityUtil.getLoggedInEmail();
		boolean isPatientOwner = me.equals(app.getPatient().getUser().getEmail());
		boolean isDoctorOwner = me.equals(app.getDoctor().getUser().getEmail());
		
		// logged in user must be doctor or patient
		// if the logged in email does not belong to patient or does not belong to doctor then throw exception
		if(!isPatientOwner && !isDoctorOwner) {
			throw new ForbiddenException("You are not allowed to cancel this appointment");
			
		}
		
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
		// use this before jwt 
	//	return appointmentRepo.findByPatientId(id);
		
		// find the patient(404 if not found)
		Patient patient = patientRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Patient not found"));
		
		// only the owner of this patient profile may continue(403 otherwise)
		SecurityUtil.checkOwner(patient.getUser().getEmail());
		
		return appointmentRepo.findByPatientId(id);
	}
	
	public List<Appointmments> viewDoctor(int id) {
		
		Doctors doctor = doctorRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Doctor not found"));
		
		SecurityUtil.checkOwner(doctor.getUser().getEmail());
		return appointmentRepo.findByDoctorId(id);
	}
}
