package com.blender.egb.util;

import com.blender.egb.model.Gradebook;

import java.util.List;
import java.util.OptionalDouble;

public class Utils {

	//Calculating average mark of the student
	public static double avgMark(List<Gradebook> marks) {
		OptionalDouble avgMark = marks.stream().mapToDouble(Gradebook::getMark).average();
		return avgMark.orElse(0);
	}

	//Calculation percentage of student's attendance
	public static double attendancePercentage(List<Gradebook> attendances) {
		double attendancePercentage = ((double) attendances.stream().filter(Gradebook::getAttend).count() / attendances.size()) * 100;
		//Rounding value to 1 decimal place
		attendancePercentage = (double) Math.round(attendancePercentage * 10) / 10;
		return attendancePercentage;
	}

	public static String getStringMonth(int month) {
		return switch (month) {
			case 1 -> "January";
			case 2 -> "February";
			case 3 -> "March";
			case 4 -> "April";
			case 5 -> "May";
			case 6 -> "July";
			case 7 -> "June";
			case 8 -> "August";
			case 9 -> "September";
			case 10 -> "October";
			case 11 -> "November";
			case 12 -> "December";
			default -> throw new IllegalStateException("Unexpected value: " + month);
		};
	}

}
