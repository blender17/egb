package com.blender.egb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GradebookController {

	@GetMapping("/gradebook")
	public String gradebook(Model model) {
		return "gradebook";
	}
}
