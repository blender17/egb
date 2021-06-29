package com.blender.egb.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class ClassesSubjects implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

//	@EmbeddedId
//	private StudentClassKey studentClassKey = new StudentClassKey();

	@ManyToOne(fetch = FetchType.LAZY)
//	@MapsId("userId")
	private User userId;

	@ManyToOne(fetch = FetchType.LAZY)
//	@MapsId("studentClassId")
	private StudentClass studentClassId;

	@ManyToOne
//	@MapsId("subjectId")
	private Subject subject;
}
