package com.blender.egb.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ClassesSubjects {

	@EmbeddedId
	private StudentClassKey studentClassKey;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("teacherId")
	private Teacher teacherId;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("studentClassId")
	private StudentClass studentClassId;

	private String subject;
}
