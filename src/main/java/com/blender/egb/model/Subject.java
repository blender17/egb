package com.blender.egb.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@ToString(exclude = "classesSubjects")
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long subjectId;
	private String name;
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<ClassesSubjects> classesSubjects;

	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Gradebook> gradebooks;
}
