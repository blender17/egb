package com.blender.egb.model;

import com.blender.egb.util.Utils;
import lombok.Data;

@Data
public class Statistic {

	private String subject;
	private int month;
	private long lessonsCount;
	private long attendCount;
	private Double avgMark;


	public Statistic(String subject, double avgMark) {
		this.subject = subject;
		this.avgMark = avgMark;
	}

	public Statistic(int month, double avgMark) {
		this.avgMark = avgMark;
		this.month = month;
	}

	public Statistic(String subject, long lessonsCount, long attendCount) {
		this.subject = subject;
		this.lessonsCount = lessonsCount;
		this.attendCount = attendCount;
	}

	public Statistic(int month, long lessonsCount, long attendCount) {
		this.month = month;
		this.lessonsCount = lessonsCount;
		this.attendCount = attendCount;
	}

	public double getStatistic() {
		if (avgMark != null) {
			//Rounding to 1 decimal place
			avgMark = (double) Math.round(avgMark * 10) / 10;
			return avgMark;
		} else {
			//Calculating percentage of student's attendance
			double attendancePercentage = ((double) attendCount / lessonsCount) * 100;
			//Rounding to 1 decimal place
			attendancePercentage = (double) Math.round(attendancePercentage * 10) / 10;
			return attendancePercentage;
		}
	}

	public String getMonth() {
		return Utils.getStringMonth(month);
	}
}
