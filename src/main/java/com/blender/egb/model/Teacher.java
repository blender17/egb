package com.blender.egb.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long teacherId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String position;
	private String academicDegree;
	private String gender;
	private String phoneNumber;
	private String email;
	private String address;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "teacherId")
	private Set<ClassesSubjects> subjects;

}