package com.blender.egb.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(exclude = {"contacts", "gradebooks"})
@Entity
public class Student implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long studentId;

	@NonNull
	private String firstName;

	@NonNull
	private String lastName;

	@Nullable
	private String middleName;

	@Transient
	private String name;

	@Basic(fetch = FetchType.LAZY)
	private String status;

	@NonNull
	private String gender;

	@NonNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;

	@Nullable
	@Basic(fetch = FetchType.LAZY)
	private String photoUrl;

	@Embedded
	private Contacts contacts;

	@ManyToOne
	@JoinColumn(name = "class_id",foreignKey = @ForeignKey(name = "FK_student_class"))
	private StudentClass studentClass;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
	private List<Gradebook> gradebooks;

	public String getName() {
		if (!(middleName == null)) {
			name = firstName + " " + middleName.charAt(0) + ". " + lastName;
		} else {
			name = firstName + " " + lastName;
		}
		return name;
	}
}