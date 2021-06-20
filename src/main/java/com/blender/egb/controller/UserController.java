package com.blender.egb.controller;

import com.blender.egb.model.StudentClass;
import com.blender.egb.repository.StudentClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	private StudentClassRepository classRepository;

	@Autowired
	public void setClassRepository(StudentClassRepository classRepository) {
		this.classRepository = classRepository;
	}

	@GetMapping(value = "/")
	public String homePage(Model model) {
		Iterable<StudentClass> studentClasses = classRepository.fetchAllClassesByTeacher(4);
		model.addAttribute("studentClasses", studentClasses);
		return "home";
	}

}
