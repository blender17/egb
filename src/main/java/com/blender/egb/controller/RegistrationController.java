package com.blender.egb.controller;

import com.blender.egb.model.User;
import com.blender.egb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/registration")
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@PostMapping("/registration")
	public String addUser(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "registration";
		}

		if (!user.getPassword().equals(user.getPasswordConfirm())){
			model.addAttribute("passwordError", "Passwords not matched");
			return "registration";
		}

		if (!userService.saveUser(user, "USER")){
			model.addAttribute("usernameError", "Username exists");
			return "registration";
		}

		return "redirect:/login";
	}
}
