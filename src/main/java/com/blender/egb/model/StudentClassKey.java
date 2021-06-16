package com.blender.egb.model;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class StudentClassKey implements Serializable {

	private Long teacherId;
	private Long studentClassId;

}
