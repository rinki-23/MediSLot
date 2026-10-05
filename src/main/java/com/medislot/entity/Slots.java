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
	private LocalDate slotdate;
	private LocalTime startTime;
	@Enumerated(EnumType.STRING)
	private SlotStatus status;
	
	public Slots() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Slots(int id, Doctors doctor, LocalDate slotdate, LocalTime startTime, SlotStatus status) {
		super();
		this.id = id;
		this.doctor = doctor;
		this.slotdate = slotdate;
		this.startTime = startTime;
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

	public LocalDate getSlotdate() {
		return slotdate;
	}

	public void setSlotdate(LocalDate slotdate) {
		this.slotdate = slotdate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public SlotStatus getStatus() {
		return status;
	}

	public void setStatus(SlotStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Slots [id=" + id + ", doctor=" + doctor + ", slotdate=" + slotdate + ", startTime=" + startTime
				+ ", status=" + status + "]";
	}

	
}
