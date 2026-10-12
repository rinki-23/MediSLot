package com.medislot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class MyConfig {

	
	
	@Bean
	public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception{
		http
		.httpBasic(Customizer.withDefaults())
		// postman want csrf token
		.csrf(csrf->
		csrf.disable())
		
		.authorizeHttpRequests(auth->auth
		.requestMatchers("/users/register", "/users/login").permitAll()
		.requestMatchers("/slot/available/{id}/{date}", "/doctor/getall", "/doctor/get/{id}").authenticated()
		
		.requestMatchers("/slot/add/{id}", "/slot/delete/{id}", "/patient/getAll", "/doctor/profile/{id}", 
				"/appointments/doctor/{id}").hasRole("DOCTOR")
		
		.requestMatchers("/patient/profile/{id}", 
				"/appointments/book/{pid}/{sid}", "/appointments/patient/{id}")
		.hasRole("PATIENT")
		
		.requestMatchers("/users/getAll").hasRole("ADMIN")
		
		.requestMatchers("/appointments/cancel/{id}").hasAnyRole("PATIENT", "DOCTOR")
		// and for any other request
		.anyRequest().authenticated()
		);
		return http.build();
	}
	
	// use for giving by our username and password , not from database
	//@Bean
	//public UserDetailsService userDetailService() {
	//	UserDetails rinki = User.withUsername("rinki")
		//		.password(this.passwordEncoder().encode("your password"))
			//	.roles("DOCTOR")
				//.build();
		
		//UserDetails riya = User.withUsername("riya")
			//	.password(this.passwordEncoder().encode("your password"))
				//.roles("PATIENT")
				//.build();
		
	//	UserDetails siya = User.withUsername("siya")
		//		.password(this.passwordEncoder().encode("your password"))
			//	.roles("ADMIN")
				//.build();
		
		//return new InMemoryUserDetailsManager(rinki, riya, siya);
	//}
	
	
	// step 2
	// Authentication Manager checks email + password for us
	// Internally it uses MyUserDetailsService(to load the user) and BCrypt(to match the password)
	//Spring does not expose it as a bean by default, so we create it here
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
}
