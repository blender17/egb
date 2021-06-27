package com.blender.egb.controller;

import com.blender.egb.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StudentController {

	private StudentService studentService;

	@Autowired
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping("/student/{id}")
	public String showStudent(@PathVariable long id, Model model) {
		studentService.getStudentById(id, model);
		return "student-page";
	}

}
