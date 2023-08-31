package vux.codejava.lib;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Convert {

	public static List<String> getRoleNames(Integer role) {
		List<String> roles = new ArrayList<String>();
		switch (role) {
		case 0:
			roles.add("ROLE_ROOT");
			break;

		case 1:
			roles.add("ROLE_ADMIN");
			break;
			
		default:
			roles.add("ROLE_USER");
			break;
		}
		
		return roles;
	}
	
	public static LocalDateTime stringToDateTime(String stringDateTime) {
		System.out.println("input string datetime");
		if(stringDateTime != null && !stringDateTime.equalsIgnoreCase("")) {
			String dateTimeString = stringDateTime.replace("T", " ");
	
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			return LocalDateTime.parse(dateTimeString, formatter);		
		}
		return null;
	}
	
	public static String getDate(String dateTime) {
		String dateTimeString = dateTime.replace("T", " ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		//LocalDateTime localDateTime = LocalDateTime.parse(dateTime);

		//return localDateTime.toLocalDate().toString();
		return LocalDateTime.parse(dateTimeString, formatter).toString();
	}
	
	public static int durationBetweenLocalDateTime(LocalDateTime from, LocalDateTime to) {
		Duration duration = Duration.between(from, to);
		
		int m = (int)duration.toMinutes();
		
		//int hours   = (int) ((s / (60*60)) % 24);
		//int m = (int)((s / 60) % 60);
		
		return m;
	}
	
	
	//https://www.digitalocean.com/community/tutorials/spring-validation-example-mvc-validator
}
