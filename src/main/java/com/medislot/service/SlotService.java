package com.medislot.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medislot.entity.Doctors;
import com.medislot.entity.SlotStatus;
import com.medislot.entity.Slots;
import com.medislot.repo.DoctorRepo;
import com.medislot.repo.SlotRepo;

@Service
public class SlotService {

	@Autowired
	private SlotRepo slotRepo;
	
	@Autowired
	private DoctorRepo doctorRepo;
	
	// add slot
	public Slots add(int id, Slots slot) {
		Doctors doctor = doctorRepo.findById(id)
				.orElseThrow(()->new RuntimeException("Doctor id does not exist"));
		
		if(slot.getSlotdate().isBefore(LocalDate.now())) {
			throw new RuntimeException("Before date is not valid");
		}
		
		if(slotRepo.existsByDoctorAndSlotdateAndStartTime(doctor, slot.getSlotdate(), slot.getStartTime())) {
			throw new RuntimeException("Slot already exists");
		}
		slot.setDoctor(doctor);
		slot.setStatus(SlotStatus.AVAILABLE);
		return slotRepo.save(slot);
	}
	
	public List<Slots> getAvailableSlots(int id, LocalDate date) {
		Doctors doctor = doctorRepo.findById(id)
				.orElseThrow(()->new RuntimeException("Doctor id does not exist"));
		
		List<Slots> s = slotRepo.findByDoctorAndSlotdateAndStatus(doctor, date, SlotStatus.AVAILABLE);
		return s;
	}
	
	public void delete(int id) {
		slotRepo.deleteById(id);
	}
}
