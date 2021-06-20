package com.blender.egb.controller;

import com.blender.egb.model.Student;
import com.blender.egb.model.StudentClass;
import com.blender.egb.model.StudentDTO;
import com.blender.egb.model.User;
import com.blender.egb.repository.StudentClassRepository;
import com.blender.egb.repository.StudentRepository;
import com.blender.egb.service.UserService;
import com.blender.egb.util.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

	private UserService userService;
	private StudentRepository studentRepository;
	private StudentClassRepository studentClassRepository;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Autowired
	public void setStudentClassRepository(StudentClassRepository studentClassRepository) {
		this.studentClassRepository = studentClassRepository;
	}

	//Admin functions for students

	@GetMapping("/admin/students")
	public String showStudent(Model model) {
		List<Student> students = studentRepository.findAll();
		List<StudentDTO> studentsDTO = students.stream()
				.map(MappingUtils::mapToStudentDTO).collect(Collectors.toList());
		model.addAttribute("students", studentsDTO);
		return "students-list";
	}

	@GetMapping("/admin/student/add")
	public String addStudent(Model model) {
		Iterable<StudentClass> studentClasses = studentClassRepository.findAll();
		model.addAttribute("studentClasses", studentClasses);
		model.addAttribute("student", new Student());
		return "student-add";
	}

	@PostMapping("/admin/student/add")
	public String addPostStudent(@ModelAttribute("student") Student student,
	                             @RequestParam(value = "classId")
			                             Long classId, Model model) {
		StudentClass studentClass = studentClassRepository.findById(classId).orElseThrow();
		student.setStudentClass(studentClass);
		studentRepository.save(student);
		return "redirect:/students";
	}

	@DeleteMapping("/admin/student/{id}/delete")
	public String deleteStudent(@PathVariable(value = "id") long id, Model model) {
		studentRepository.deleteById(id);
		return "redirect:/students";
	}

	//Admin functions for student classes

	@GetMapping("/admin/classes")
	public String studentClasses(Model model) {
		Iterable<StudentClass> studentClasses = studentClassRepository.findAll();
		model.addAttribute("studentClasses", studentClasses);
		return "classes";
	}

	@GetMapping("/admin/class/add")
	public String addStudentToClass(Model model) {
		Iterable<String> facultyList = studentClassRepository.fetchAllFaculties();
		Iterable<String> programList = studentClassRepository.fetchAllPrograms();
		model.addAttribute("facultyList", facultyList);
		model.addAttribute("programList", programList);
		model.addAttribute("studentClass", new StudentClass());
		return "class-add";
	}

	@PostMapping("/admin/class/add")
	public String postStudentToClass(@ModelAttribute StudentClass studentClass, Model model) {
		studentClassRepository.save(studentClass);
		return "redirect:/classes";
	}

	//Admin functions for users

	@GetMapping("/admin/user/add")
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		return "user-add";
	}

	@PostMapping("/admin/user/add")
	public String addUser(@ModelAttribute User user, @RequestParam(value = "role") String role, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "user-add";
		}

		if (!user.getPassword().equals(user.getPasswordConfirm())){
			model.addAttribute("passwordError", "Passwords not matched");
			return "user-add";
		}

		if (!userService.saveUser(user, role)){
			model.addAttribute("usernameError", "Username exists");
			return "user-add";
		}

		return "redirect:/";
	}

}
