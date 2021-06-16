package com.blender.egb.model;

import com.blender.egb.util.Utils;
import lombok.Data;

@Data
public class MarksStatistic{

	private String subject;
	private double avgMark;
	private int monthNumber;

	public MarksStatistic(String subject, double avgMark) {
		this.subject = subject;
		this.avgMark = avgMark;
	}

	public MarksStatistic(int monthNumber, double avgMark) {
		this.avgMark = avgMark;
		this.monthNumber = monthNumber;
	}

	public String getMonth() {
		return Utils.getStringMonth(monthNumber);
	}

	public double getStatistic() {
		return avgMark;
	}
}
