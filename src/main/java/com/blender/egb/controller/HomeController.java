package com.blender.egb.controller;

import com.blender.egb.model.ClassesSubjects;
import com.blender.egb.model.ClassesSubjectsDTO;
import com.blender.egb.model.StudentClass;
import com.blender.egb.model.User;
import com.blender.egb.repository.ClassesSubjectsRepository;
import com.blender.egb.repository.StudentClassRepository;
import com.blender.egb.util.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HomeController {

	private ClassesSubjectsRepository classesSubjectsRepository;
	private StudentClassRepository studentClassRepository;

	@Autowired
	public void setClassesSubjectsRepository(ClassesSubjectsRepository classesSubjectsRepository) {
		this.classesSubjectsRepository = classesSubjectsRepository;
	}

	@Autowired
	public void setStudentClassRepository(StudentClassRepository studentClassRepository) {
		this.studentClassRepository = studentClassRepository;
	}

	@GetMapping("/home")
	public String homePage(Model model, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof User user) {
			List<ClassesSubjects> classesSubjects = classesSubjectsRepository.getAllByUserId(user);
			List<StudentClass> studentClasses = studentClassRepository.getAllClassesByTeacherId(user.getUserId());
			List<ClassesSubjectsDTO> classesSubjectsDTO = MappingUtils.mapToClassSubjectsDTO(studentClasses, classesSubjects);
			model.addAttribute("studentClasses", classesSubjectsDTO);
		}
		return "home";
	}

	@GetMapping("/success")
	public String loginSuccessRedirect(Model model, HttpServletRequest httpServletRequest) {

		if (httpServletRequest.isUserInRole("ADMIN")) {
			return "redirect:/admin";
		} else if (httpServletRequest.isUserInRole("TEACHER")) {
			return "redirect:/home";
		}

		return "error";
	}

	@GetMapping("/")
	public String rootRedirect(Model model, HttpServletRequest httpServletRequest) {

		if (httpServletRequest.isUserInRole("ADMIN")) {
			return "redirect:/admin";
		} else if (httpServletRequest.isUserInRole("TEACHER")) {
			return "redirect:/home";
		}

		return "error";
	}

}
