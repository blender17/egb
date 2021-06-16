package com.blender.egb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GradebookController {

	@GetMapping("/gradebooks")
	public String gradebooks(Model model) {
		return "gradebooks";
	}
}
