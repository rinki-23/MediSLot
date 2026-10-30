package com.medislot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Doctors {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne
	@JoinColumn(name = "users_id")
	private Users user;
	private String specialization;
	private int experience;
	private double fees;
	// for admin we required this,  for all doctor we take by default value is true
	private boolean active = true;
	
	public Doctors() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Doctors(int id, Users user, String specialization, int experience, double fees, boolean active) {
		super();
		this.id = id;
		this.user = user;
		this.specialization = specialization;
		this.experience = experience;
		this.fees = fees;
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public double getFees() {
		return fees;
	}

	public void setFees(double fees) {
		this.fees = fees;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Doctors [id=" + id + ", user=" + user + ", specialization=" + specialization + ", experience="
				+ experience + ", fees=" + fees + ", active=" + active + "]";
	}

	
	
}
