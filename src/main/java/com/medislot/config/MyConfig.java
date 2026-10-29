package com.medislot.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.medislot.security.JwtFilter;
import com.medislot.service.JwtAccessDeniedHandler;
import com.medislot.service.JwtAuthEntryPoint;

@Configuration
public class MyConfig {

	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private JwtAuthEntryPoint authEntryPoint;
	
	@Autowired
	private JwtAccessDeniedHandler accessDeniedHandler;
	
	//this bean tell spring which frontend are allowed to call our API 
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		
		// frontend addresses that are allowed
		config.setAllowedOrigins(List.of(
				"http://127.0.0.1:5500",
				"http://localhost:5500"));
		
		//Http methods the frontend is allowed to use
		// OPTIONS is the small "permission check" request the browser sned first
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		
		// headers the frontend is allowed to send
		// Authorization is needed because the jwt travels in this header
		config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
		
		// Apply this core rule to every URL of our API
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return source;
	}
	@Bean
	public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception{
		http
		// use this before token
		//.httpBasic(Customizer.withDefaults())
		// postman want csrf token
		.csrf(csrf->
		csrf.disable())
		// Tells spring Security to use the CORS rules defined in the bean above
		.cors(Customizer.withDefaults())
		// make stateless
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		// use when we create Jwt AuthEntryPoint and JwtAccessDeniedHandler
		.exceptionHandling(ex-> ex.authenticationEntryPoint(authEntryPoint).accessDeniedHandler(accessDeniedHandler))
		
		.authorizeHttpRequests(auth->auth
		.requestMatchers("/users/register", "/users/login").permitAll()
		.requestMatchers("/slot/available/{id}/{date}", "/doctor/getall", "/doctor/get/{id}").authenticated()
		
		.requestMatchers("/slot/add/{id}", "/slot/delete/{id}", "/patient/getAll", "/doctor/profile/{id}", 
				"/appointments/doctor/{id}", "/appointments/complete/{id}").hasRole("DOCTOR")
		
		.requestMatchers("/patient/profile/{id}", 
				"/appointments/book/{pid}/{sid}", "/appointments/patient/{id}", "/patient/update/{id}")
		.hasRole("PATIENT")
		
		.requestMatchers("/users/getAll", "/admin/doctors").hasRole("ADMIN")
		
		.requestMatchers("/appointments/cancel/{id}").hasAnyRole("PATIENT", "DOCTOR")
		// and for any other request
		.anyRequest().authenticated()
		)
		
		// Put out jwt filter BEFORE spring default login filter.
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		;
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
