package com.blender.egb.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Gradebook {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long id;
	private Integer mark;
	private Boolean attend;
	private LocalDate date;
	private Long classId;
	private String topic;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "FK_student"))
	private Student student;

	@ManyToOne
	private Subject subject;

}
