package com.blender.egb.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Attendance {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long id;
	private String subject;
	private Boolean attend;
	private Date date;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "student_fkey"))
	private Student student;


	public Attendance() {}

	public Attendance(String subject, Boolean attend, Date date) {
		this.subject = subject;
		this.attend = attend;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Boolean isAttend() {
		return attend;
	}

	public void setAttend(Boolean attend) {
		this.attend = attend;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Attendance{" +
				"id=" + id +
				", subject='" + subject + '\'' +
				", attend=" + attend +
				", date=" + date +
				'}';
	}
}
