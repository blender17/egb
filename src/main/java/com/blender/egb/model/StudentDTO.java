package com.blender.egb.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentDTO {

	private Long studentId;
	private String firstName;
	private String lastName;
	private String gender;
	private LocalDate birthday;
	private StudentClass studentClass;
	private Double avgMark;
	private Double attendancePercentage;
}
