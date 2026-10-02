package com.medislot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Appointmments {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	@ManyToOne
	@JoinColumn(name = "slot_id")
	private Slots slot;
	
	@Enumerated(EnumType.STRING)
	private Appointmentstatus status;
	
	public Appointmments() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Appointmments(int id, Patient patient, Slots slot, Appointmentstatus status) {
		super();
		this.id = id;
		this.patient = patient;
		this.slot = slot;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Slots getSlot() {
		return slot;
	}

	public void setSlot(Slots slot) {
		this.slot = slot;
	}

	public Appointmentstatus getStatus() {
		return status;
	}

	public void setStatus(Appointmentstatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Appointmments [id=" + id + ", patient=" + patient + ", slot=" + slot + ", status=" + status + "]";
	}
	
	
}
