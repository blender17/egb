package com.blender.egb.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Student implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long studentId;
	private String firstName;
	private String lastName;
	private Integer classCode;
	private String faculty;
	private String status;
	private String gender;
	@Embedded
	private Contacts contacts;
	@OneToMany(mappedBy = "student")
	private List<Mark> marks;
	@OneToMany(mappedBy = "student")
	private List<Attendance> attendances;

	public Student() {}

	public Student(String firstName, String lastName, Integer classCode,
	               String faculty, String status, String gender,
	               Contacts contacts, List<Mark> marks, List<Attendance> attendances) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.classCode = classCode;
		this.faculty = faculty;
		this.status = status;
		this.gender = gender;
		this.contacts = contacts;
		this.marks = marks;
		this.attendances = attendances;
	}

	public Long getStudentId() {
		return studentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getClassCode() {
		return classCode;
	}

	public void setClassCode(Integer classCode) {
		this.classCode = classCode;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Contacts getContacts() {
		return contacts;
	}

	public void setContacts(Contacts contacts) {
		this.contacts = contacts;
	}

	public List<Mark> getMarks() {
		return marks;
	}

	public void setMarks(List<Mark> marks) {
		this.marks = marks;
	}

	public List<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Student student = (Student) o;
		return studentId.equals(student.studentId) && firstName.equals(student.firstName)
				&& lastName.equals(student.lastName) && classCode.equals(student.classCode)
				&& faculty.equals(student.faculty) && status.equals(student.status)
				&& gender.equals(student.gender) && contacts.equals(student.contacts)
				&& Objects.equals(marks, student.marks) && Objects.equals(attendances, student.attendances);
	}

	@Override
	public int hashCode() {
		return Objects.hash(studentId, firstName, lastName, classCode, faculty, status, gender, contacts, marks, attendances);
	}

	@Override
	public String toString() {
		return "Student{" +
				"studentId=" + studentId +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", classCode=" + classCode +
				", faculty='" + faculty + '\'' +
				", status='" + status + '\'' +
				", gender='" + gender + '\'' +
				", contacts=" + contacts +
				", marks=" + marks +
				", attendances=" + attendances +
				'}';
	}
}
