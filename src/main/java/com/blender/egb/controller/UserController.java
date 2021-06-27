package com.blender.egb.controller;

import com.blender.egb.model.ClassesSubjects;
import com.blender.egb.model.ClassesSubjectsDTO;
import com.blender.egb.model.StudentClass;
import com.blender.egb.model.User;
import com.blender.egb.repository.ClassesSubjectsRepository;
import com.blender.egb.repository.StudentClassRepository;
import com.blender.egb.service.UserService;
import com.blender.egb.util.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserController {

	private UserService userService;
	private ClassesSubjectsRepository classesSubjectsRepository;
	private StudentClassRepository studentClassRepository;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setClassesSubjectsRepository(ClassesSubjectsRepository classesSubjectsRepository) {
		this.classesSubjectsRepository = classesSubjectsRepository;
	}

	@Autowired
	public void setStudentClassRepository(StudentClassRepository studentClassRepository) {
		this.studentClassRepository = studentClassRepository;
	}

	@GetMapping("/user/{id}")
	public String getUserById(@PathVariable long id, Model model) {
		User user = userService.getUserById(id);
		if (user != null) {
			model.addAttribute("user", user);
			if (user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_TEACHER"))) {
				List<ClassesSubjects> classesSubjects = classesSubjectsRepository.getAllByUserId(user);
				List<StudentClass> studentClasses = studentClassRepository.getAllClassesByTeacherId(user.getUserId());
				List<ClassesSubjectsDTO> classesSubjectsDTO = MappingUtils.mapToClassSubjectsDTO(studentClasses, classesSubjects);
				model.addAttribute("studentClasses", classesSubjectsDTO);
			}
		}
		return "user-page";
	}

}
