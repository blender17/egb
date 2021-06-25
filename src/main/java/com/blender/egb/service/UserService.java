package com.blender.egb.service;

import com.blender.egb.model.Role;
import com.blender.egb.model.User;
import com.blender.egb.repository.RoleRepository;
import com.blender.egb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private RoleRepository roleRepository;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		return user;
	}

	public List<User> getAllUsersByRole(String roleName) {
		Role role = roleRepository.findByName(roleName);
		return userRepository.findAllByRoles(role);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(long id) {
		return userRepository.getById(id);
	}

	public void deleteUserById(long id) {
		userRepository.deleteById(id);
	}

	public boolean saveUser(User user, String role, boolean edit) {

		if (!edit) {
			User existingUser = userRepository.findByUsername(user.getUsername());

			if (existingUser != null) {
				return false;
			}
		}

		user.setRoles(
				Collections.singleton(
						switch (role) {
							case "ADMIN" -> new Role(1L, "ROLE_ADMIN");
							case "TEACHER" -> new Role(2L, "ROLE_TEACHER");
							case "STUDENT" -> new Role(3L, "ROLE_STUDENT");
							case "USER" -> new Role(4L, "ROLE_USER");
							default -> throw new IllegalStateException("Unexpected value: " + role);
						}
				)
		);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return true;
	}
}
