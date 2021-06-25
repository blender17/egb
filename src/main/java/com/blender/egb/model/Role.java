package com.blender.egb.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.Set;

@Data
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Role implements GrantedAuthority {

	@Id
	private Long id;
	@ToString.Include
	private String name;
	@Transient
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;

	public Role(String name) {
		this.name = name;
	}

	public Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return getName();
	}
}
