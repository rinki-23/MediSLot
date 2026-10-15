package com.medislot.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medislot.entity.Doctors;
import com.medislot.entity.SlotStatus;
import com.medislot.entity.Slots;
import com.medislot.exception.BadRequestException;
import com.medislot.exception.DuplicateResourceException;
import com.medislot.exception.ResourceNotFoundException;
import com.medislot.repo.DoctorRepo;
import com.medislot.repo.SlotRepo;
import com.medislot.security.SecurityUtil;

@Service
public class SlotService {

	@Autowired
	private SlotRepo slotRepo;
	
	@Autowired
	private DoctorRepo doctorRepo;
	
	// add slot
	public Slots add(int id, Slots slot) {
		Doctors doctor = doctorRepo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Doctor id does not exist"));
		
		// only the owner of this doctor profile can add slots
		SecurityUtil.checkOwner(doctor.getUser().getEmail());
		if(slot.getSlotdate().isBefore(LocalDate.now())) {
			throw new BadRequestException("Slot date cannot be in the past");
		}
		
		// if the date is today, the start time must still be in the future
		if(slot.getSlotdate().isEqual(LocalDate.now()) && slot.getStartTime().isBefore(LocalTime.now())){
			throw new BadRequestException("Slot start time has already passed");
		}
		
		if(slotRepo.existsByDoctorAndSlotdateAndStartTime(doctor, slot.getSlotdate(), slot.getStartTime())) {
			throw new DuplicateResourceException("Slot already exists for this doctor at the given date and time");
		}
		slot.setDoctor(doctor);
		slot.setStatus(SlotStatus.AVAILABLE);
		return slotRepo.save(slot);
	}
	
	public List<Slots> getAvailableSlots(int id, LocalDate date) {
		Doctors doctor = doctorRepo.findById(id)
				.orElseThrow(()->new BadRequestException("Doctor id does not exist"));
		
		List<Slots> s = slotRepo.findByDoctorAndSlotdateAndStatus(doctor, date, SlotStatus.AVAILABLE);
		return s;
	}
	
	// delete slot
	public void delete(int id) {
		Slots slot = slotRepo.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("Slot not found"));
		
		SecurityUtil.checkOwner(slot.getDoctor().getUser().getEmail());
		slotRepo.deleteById(id);
	}
}
