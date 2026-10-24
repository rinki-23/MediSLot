package com.medislot.entity;

public class RegisterRequest {

	private String name;
	private String email;
	private String password;
	private int age;
	private String phone;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public RegisterRequest(String name, String email, String password, int age, String phone) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.age = age;
		this.phone = phone;
	}
	public RegisterRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
