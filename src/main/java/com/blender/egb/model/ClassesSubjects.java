package com.blender.egb.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class ClassesSubjects implements Serializable {

	@EmbeddedId
	private StudentClassKey studentClassKey = new StudentClassKey();

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	private User userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("studentClassId")
	private StudentClass studentClassId;

	@ManyToOne
	private Subject subject;
}
