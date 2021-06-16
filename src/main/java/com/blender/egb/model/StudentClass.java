package com.blender.egb.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = "students")
@Entity
public class StudentClass {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long classId;

	@Column(unique = true)
	private String classCode;

	private String faculty;
	private String program;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "studentClass")
	private Set<Student> students;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "studentClassId")
	private Set<ClassesSubjects> subjects;

}
