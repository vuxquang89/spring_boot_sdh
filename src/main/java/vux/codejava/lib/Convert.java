package vux.codejava.lib;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
		case 2:
			roles.add("ROLE_EDITOR");
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		//LocalDateTime localDateTime = LocalDateTime.parse(dateTime);

		//return localDateTime.toLocalDate().toString();
		return LocalDateTime.parse(dateTimeString, formatter).toString();
	}
	
	
	public static String dateToString(String dateTime) {
		String dateTimeString = dateTime.replace("T", " ");
		return dateTimeString;
	}
	
	public static int durationBetweenLocalDateTime(LocalDateTime from, LocalDateTime to) {
		Duration duration = Duration.between(from, to);
		
		int m = (int)duration.toMinutes();
		
		//int hours   = (int) ((s / (60*60)) % 24);
		//int m = (int)((s / 60) % 60);
		
		return m;
	}
	
	public static long convertToMinutes(LocalDateTime from, LocalDateTime to) {
		//long hours = ChronoUnit.HOURS.between(shift.getDateReceive(), shift.getDateShift());
        long t = ChronoUnit.MINUTES.between(from, to);
        return t;
	}
	
	public static String convertToHoursMinutes(long t) {
		int hours   = (int)t / 60;   // since both are ints, you get an int
        int minutes = (int)t % 60;
        return hours + "h" + minutes;
	}
	
	public static String[] convertStringToMonthYear(String month) {
		String[] months = month.split("-");
		if(months[0].length() < 4) {
			String swap = months[0];
			months[0] = months[1];
			months[0] = swap;
		}
		return months;
	}
	
	//https://www.digitalocean.com/community/tutorials/spring-validation-example-mvc-validator
}
