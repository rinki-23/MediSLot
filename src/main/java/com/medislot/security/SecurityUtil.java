package com.medislot.security;

import org.springframework.security.core.context.SecurityContextHolder;

import com.medislot.exception.ForbiddenException;

public class SecurityUtil {

	// returns the email of the user who is logged in for this request
	//JwtFilter stored this user in SecurityContextHolder, so we read it from there
	public static String getLoggedInEmail() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	// throws 403 if the data owner is not the logged in user
	public static void checkOwner(String ownerEmail) {
		String loggedInEmail = getLoggedInEmail();
		if(!loggedInEmail.equals(ownerEmail)) {
			throw new ForbiddenException("You are not allowed to access this data");
		}
	}
}
