package com.blender.egb.model;

import lombok.Data;

import java.util.Map;

@Data
public class ClassesSubjectsDTO {

	private Long classId;
	private String classCode;
	private Map<Long, String> subjects;

}
