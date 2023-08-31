package vux.codejava.lib;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncrytedPassword {

	public static String encrytedPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
	
	public static boolean checkPassword(String password, String encoderPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(password, encoderPassword);
	}
}
