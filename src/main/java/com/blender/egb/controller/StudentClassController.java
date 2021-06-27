package com.blender.egb.controller;

import com.blender.egb.service.StudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StudentClassController {

	private StudentClassService studentClassService;

	@Autowired
	public void setStudentClassService(StudentClassService studentClassService) {
		this.studentClassService = studentClassService;
	}

	@GetMapping("/class/{id}")
	public String getClassById(@PathVariable long id, Model model) {
		studentClassService.getById(id, model);
		return "class";
	}
}
