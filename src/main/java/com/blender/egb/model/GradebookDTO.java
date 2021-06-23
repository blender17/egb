package com.blender.egb.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class GradebookDTO {

	private String name;
	private LocalDate date;
	private String value;

}
