package com.blender.egb.util;

import com.blender.egb.model.Gradebook;

import java.util.List;
import java.util.Objects;

public class Utils {

	//Calculating average mark of the student and rounding to 1 decimal place
	public static double avgMark(List<Gradebook> marks) {
		double avgMark = marks.stream().filter(gradebook -> Objects.nonNull(gradebook.getMark())).mapToDouble(Gradebook::getMark).average().orElse(0);
		avgMark = (double) Math.round(avgMark * 10) / 10;
		return avgMark;
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
