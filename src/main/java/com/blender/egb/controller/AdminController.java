package com.blender.egb.controller;

import com.blender.egb.model.*;
import com.blender.egb.repository.ClassesSubjectsRepository;
import com.blender.egb.repository.StudentClassRepository;
import com.blender.egb.repository.StudentRepository;
import com.blender.egb.repository.SubjectsRepository;
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
	private SubjectsRepository subjectsRepository;
	private ClassesSubjectsRepository classesSubjectsRepository;

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

	@Autowired
	public void setSubjectsRepository(SubjectsRepository subjectsRepository) {
		this.subjectsRepository = subjectsRepository;
	}

	@Autowired
	public void setClassesSubjectsRepository(ClassesSubjectsRepository classesSubjectsRepository) {
		this.classesSubjectsRepository = classesSubjectsRepository;
	}

	//Admin functions for students

	@GetMapping("/admin")
	public String adminPage(Model model) {
		return "admin";
	}

	@GetMapping("/admin/students")
	public String studentsList(Model model) {
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
	public String addStudent(@ModelAttribute Student student,
	                         @RequestParam Long classId, Model model) {
		StudentClass studentClass = studentClassRepository.findById(classId).orElseThrow();
		student.setStudentClass(studentClass);
		studentRepository.save(student);
		return "redirect:/admin";
	}

	@GetMapping("/admin/student/{id}/edit")
	public String editStudent(Model model, @PathVariable long id) {
		Iterable<StudentClass> studentClasses = studentClassRepository.findAll();
		Student student = studentRepository.findById(id).orElseThrow();
		model.addAttribute("studentClasses", studentClasses);
		model.addAttribute("student", student);
		return "student-edit";
	}

	@PostMapping("/admin/student/{id}/edit")
	public String editStudent(@ModelAttribute Student student,
	                             @RequestParam Long classId, Model model, @PathVariable long id) {
		StudentClass studentClass = studentClassRepository.findById(classId).orElseThrow();
		student.setStudentClass(studentClass);
		student.setStudentId(id);
		studentRepository.save(student);
		return "redirect:/admin";
	}

	@DeleteMapping("/admin/student/{id}/delete")
	public String deleteStudent(@PathVariable long id, Model model) {
		studentRepository.deleteById(id);
		return "redirect:/admin";
	}

	//Admin functions for student classes

	@GetMapping("/admin/classes")
	public String studentClasses(Model model) {
		Iterable<StudentClass> studentClasses = studentClassRepository.findAll();
		model.addAttribute("studentClasses", studentClasses);
		return "classes";
	}

	@GetMapping("/admin/class/add")
	public String addClass(Model model) {
		Iterable<String> facultyList = studentClassRepository.fetchAllFaculties();
		Iterable<String> programList = studentClassRepository.fetchAllPrograms();
		model.addAttribute("facultyList", facultyList);
		model.addAttribute("programList", programList);
		model.addAttribute("studentClass", new StudentClass());
		return "class-add";
	}

	@PostMapping("/admin/class/add")
	public String addClass(@ModelAttribute StudentClass studentClass, Model model) {
		studentClassRepository.save(studentClass);
		return "redirect:/admin";
	}

	@GetMapping("/admin/class/{id}/edit")
	public String editClass(Model model, @PathVariable long id) {
		Iterable<String> facultyList = studentClassRepository.fetchAllFaculties();
		Iterable<String> programList = studentClassRepository.fetchAllPrograms();
		StudentClass studentClass = studentClassRepository.getById(id);
		model.addAttribute("facultyList", facultyList);
		model.addAttribute("programList", programList);
		model.addAttribute("studentClass", studentClass);
		return "class-edit";
	}

	@PostMapping("/admin/class/{id}/edit")
	public String editClass(@ModelAttribute StudentClass studentClass, Model model , @PathVariable long id) {
		studentClass.setClassId(id);
		studentClassRepository.save(studentClass);
		return "redirect:/admin";
	}

	@DeleteMapping("/admin/class/{id}/delete")
	public String deleteClass(@PathVariable long id, Model model) {
		studentClassRepository.deleteById(id);
		return "redirect:/admin";
	}

	@GetMapping("/admin/teacher/bind")
	public String bindTeacher(Model model) {
		List<Subject> subjects = subjectsRepository.findAll();
		List<User> teachers = userService.getAllUsersByRole("ROLE_TEACHER");
		List<StudentClass> studentClasses = studentClassRepository.findAll();
		model.addAttribute("teachers", teachers);
		model.addAttribute("subjects", subjects);
		model.addAttribute("studentClasses", studentClasses);
		model.addAttribute("classSubject", new ClassesSubjects());
		return "teacher-bind";
	}

	@PostMapping("/admin/teacher/bind")
	public String bindTeacher(@ModelAttribute ClassesSubjects classSubject, Model model,
	                          @RequestParam Long subjectId,
	                          @RequestParam Long teacherId,
	                          @RequestParam Long studentClassId) {
		Subject subject = subjectsRepository.getById(subjectId);
		User user = userService.getUserById(teacherId);
		StudentClass studentClass = studentClassRepository.findById(studentClassId).orElseThrow();
		classSubject.setSubject(subject);
		classSubject.setUserId(user);
		classSubject.setStudentClassId(studentClass);
		classesSubjectsRepository.save(classSubject);
		return "redirect:/admin";
	}

	//Subjects functions

	@GetMapping("/admin/subject/add")
	public String addSubject(Model model) {
		model.addAttribute("subject", new Subject());
		return "subject-add";
	}

	@PostMapping("/admin/subject/add")
	public String addSubject(@ModelAttribute Subject subject, Model model) {
		subjectsRepository.save(subject);
		return "redirect:/admin";
	}

	@GetMapping("/admin/subjects")
	public String subjectsList(Model model) {
		List<Subject> subjects = subjectsRepository.findAll();
		model.addAttribute("subjects", subjects);
		return "subjects-list";
	}

	@GetMapping("/admin/subject/{id}/edit")
	public String editSubject(Model model, @PathVariable long id) {
		Subject subject = subjectsRepository.getById(id);
		model.addAttribute("subject", subject);
		return "subject-edit";
	}

	@PostMapping("/admin/subject/{id}/edit")
	public String editSubject(@ModelAttribute Subject subject, Model model, @PathVariable long id) {
		subject.setSubjectId(id);
		subjectsRepository.save(subject);
		return "redirect:/admin";
	}

	@DeleteMapping("/admin/subject/{id}/delete")
	public String deleteSubject(@PathVariable long id, Model model) {
		subjectsRepository.deleteById(id);
		return "redirect:/admin";
	}

	//Admin functions for users

	@GetMapping("/admin/users")
	public String usersList(Model model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("title", "Users list");
		return "users-list";
	}

	@GetMapping("/admin/teachers")
	public String teachersList(Model model) {
		List<User> users = userService.getAllUsersByRole("ROLE_TEACHER");
		model.addAttribute("users", users);
		model.addAttribute("title", "Teachers list");
		return "users-list";
	}

	@GetMapping("/admin/user/add")
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		return "user-add";
	}

	@GetMapping("/admin/user/{id}/edit")
	public String editUser(Model model, @PathVariable long id) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "user-edit";
	}

	@PostMapping("/admin/user/{id}/edit")
	public String editUser(@ModelAttribute User user, @RequestParam String role, Model model, @PathVariable long id) {

		user.setUserId(id);

		if (!user.getPassword().equals(user.getPasswordConfirm())){
			model.addAttribute("passwordError", "Passwords not matched");
			return "user-edit";
		}

		userService.saveUser(user, role, true);

		return "redirect:/admin";
	}

	@PostMapping("/admin/user/add")
	public String addUser(@ModelAttribute User user, @RequestParam String role, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "user-add";
		}

		if (!user.getPassword().equals(user.getPasswordConfirm())){
			model.addAttribute("passwordError", "Passwords not matched");
			return "user-add";
		}

		if (!userService.saveUser(user, role, false)){
			model.addAttribute("usernameError", "Username exists");
			return "user-add";
		}

		return "redirect:/admin";
	}

	@DeleteMapping("/admin/user/{id}/delete")
	public String deleteUser(@PathVariable long id, Model model) {
		userService.deleteUserById(id);
		return "redirect:/admin";
	}

}
