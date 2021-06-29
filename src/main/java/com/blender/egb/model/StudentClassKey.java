package com.blender.egb.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
public class StudentClassKey implements Serializable {

	private Long userId;
	private Long studentClassId;
	private Long subjectId;

}
