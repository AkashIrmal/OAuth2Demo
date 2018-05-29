package com.fintec.auth;

import java.io.UnsupportedEncodingException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Base64;

public class AuthConstants {

	public static final String STATE_ACTIVE = "Y";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_ADMIN1 = "ADMIN";
	public static final String ROLE_SECRETARY = "SECRETARY";
	
	
	public static boolean userType(String s){
		if(s.equalsIgnoreCase("N")){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean userEnabled(String s){
		if(s.equalsIgnoreCase("Y")){
			return true;
		}else{
			return false;
		}
	}

	/*public static void main(String[] args) {
		String creds = String.format("%s:%s", "fintec", "fintec");
		try {
			System.out.println("Basic " + new String(Base64.encode(creds.getBytes("UTF-8"))));
		}
		catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Could not convert String");
		}
		
		System.out.println(new BCryptPasswordEncoder(6).encode("fintec"));
		System.out.println(new BCryptPasswordEncoder(6).encode("Passw0rd"));
	}*/
}
