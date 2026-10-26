package com.medislot.entity;

import jakarta.persistence.Entity;


public class DoctorRequest {
	
	private String name;
	private String email;
	private String password;
	private String specialization;
	private int experience;
	private double fees;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	@Override
	public String toString() {
		return "DoctorRequest [name=" + name + ", email=" + email + ", password=" + password + ", specialization="
				+ specialization + ", experience=" + experience + ", fees=" + fees + "]";
	}
	
	
	
	
	
}
