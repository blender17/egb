package com.blender.egb.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Mark {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long id;
	private String subject;
	private Integer mark;
	private Date date;
	private String theme;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "student_fkey"))
	private Student student;

	public Mark() {}

	public Mark( String subject, Integer mark, Date date, String theme) {
		this.subject = subject;
		this.mark = mark;
		this.date = date;
		this.theme = theme;
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

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Override
	public String toString() {
		return "Mark{" +
				"id=" + id +
				", subject='" + subject + '\'' +
				", mark=" + mark +
				", date=" + date +
				", theme='" + theme + '\'' +
				'}';
	}
}
