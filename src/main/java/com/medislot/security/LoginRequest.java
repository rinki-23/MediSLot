package com.medislot.security;

// step 1
// This class only carries the data sent by the user at login time
//We use it instead of the Users entity because login needs just email and password
public class LoginRequest {

	private String email;
	private String password;
	
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
	@Override
	public String toString() {
		return "LoginRequest [email=" + email + ", password=" + password + "]";
	}
	
	
	
}
