package com.medislot.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.medislot.entity.Slots;
import com.medislot.service.SlotService;

@RestController
@RequestMapping("/slot")
public class SlotController {
	
	@Autowired
	private SlotService slotService;

	@PostMapping("/add/{id}")
	public Slots addSlot(@PathVariable("id") int id, @RequestBody Slots slot) {
		return slotService.add(id, slot);
	}
	
	// show available 
	@GetMapping("/available/{id}/{date}")
	public List<Slots> getSlots(@PathVariable("id") int id, @PathVariable("date") LocalDate date){
		return slotService.getAvailableSlots(id, date);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") int id) {
		slotService.delete(id);
	}
}
