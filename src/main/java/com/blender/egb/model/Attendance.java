package com.blender.egb.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Attendance {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long id;
	private String subject;
	private Boolean attend;
	private LocalDate date;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "FK_student"))
	private Student student;

}
