package com.blender.egb.service;

import com.blender.egb.model.Role;
import com.blender.egb.model.User;
import com.blender.egb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		return user;
	}

	public boolean saveUser(User user, String role) {

		User existingUser = userRepository.findByUsername(user.getUsername());

		if (existingUser != null) {
			return false;
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
