package com.blender.egb.controller;

import com.blender.egb.model.User;
import com.blender.egb.repository.UserRepository;
import com.blender.egb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

	private UserRepository userRepository;
	private UserService userService;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/admin/user/add")
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		return "user-add";
	}

	@PostMapping("/admin/user/add")
	public String addUser(@ModelAttribute User user, @RequestParam(value = "role") String role, BindingResult bindingResult, Model model) {

		System.out.println("Role: " + role);

		if (bindingResult.hasErrors()) {
			return "user-add";
		}

		if (!user.getPassword().equals(user.getPasswordConfirm())){
			model.addAttribute("passwordError", "Passwords not matched");
			return "user-add";
		}

		if (!userService.saveUser(user)){
			model.addAttribute("usernameError", "Username exists");
			return "user-add";
		}

		userRepository.save(user);

		return "redirect:/";
	}

}
