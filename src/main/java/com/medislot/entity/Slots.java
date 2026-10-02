package com.medislot.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Slots {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private Doctors doctor;
	private LocalDate slot_date;
	private LocalTime start_time;
	@Enumerated(EnumType.STRING)
	private SlotStatus status;
	
	public Slots() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Slots(int id, Doctors doctor, LocalDate slot_date, LocalTime start_time, SlotStatus status) {
		super();
		this.id = id;
		this.doctor = doctor;
		this.slot_date = slot_date;
		this.start_time = start_time;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Doctors getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctors doctor) {
		this.doctor = doctor;
	}

	public LocalDate getSlot_date() {
		return slot_date;
	}

	public void setSlot_date(LocalDate slot_date) {
		this.slot_date = slot_date;
	}

	public LocalTime getStart_time() {
		return start_time;
	}

	public void setStart_time(LocalTime start_time) {
		this.start_time = start_time;
	}

	public SlotStatus getStatus() {
		return status;
	}

	public void setStatus(SlotStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Slots [id=" + id + ", doctor=" + doctor + ", slot_date=" + slot_date + ", start_time=" + start_time
				+ ", status=" + status + "]";
	}
	
	
	
}
