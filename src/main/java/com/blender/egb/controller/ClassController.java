package com.blender.egb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClassController {


	@GetMapping("/student-class")
	public String teacher(Model model) {
		return "";
	}
}
