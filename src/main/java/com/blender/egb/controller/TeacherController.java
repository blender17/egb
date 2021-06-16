package com.blender.egb.controller;

import com.blender.egb.model.StudentClass;
import com.blender.egb.model.Teacher;
import com.blender.egb.repository.StudentClassRepository;
import com.blender.egb.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TeacherController {

	private final TeacherRepository teacherRepository;
	private final StudentClassRepository classRepository;

	@Autowired
	public TeacherController(TeacherRepository teacherRepository, StudentClassRepository classRepository) {
		this.teacherRepository = teacherRepository;
		this.classRepository = classRepository;
	}

	@GetMapping(value = "/")
	public String homePage(Model model) {
		Iterable<StudentClass> studentClasses = classRepository.fetchAllClassesByTeacher(4);
		model.addAttribute("studentClasses", studentClasses);
		return "home";
	}

	@GetMapping("/teacher/add")
	public String addTeacher(Model model) {
		model.addAttribute("teacher", new Teacher());
		return "teacher-add";
	}

	@PostMapping("/teacher/add")
	public String addTeacher(@ModelAttribute Teacher teacher, Model model) {
		teacherRepository.save(teacher);
		return "redirect:/";
	}

}
