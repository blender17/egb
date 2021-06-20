package com.blender.egb.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long userId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String position;
	private String academicDegree;
	private String gender;
	private String phoneNumber;
	private String email;
	private String address;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userId")
	private Set<ClassesSubjects> subjects;

	private String username;
	private String password;
	@Transient
	private String passwordConfirm;
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}