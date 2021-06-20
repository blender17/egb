package com.blender.egb.controller;

import com.blender.egb.model.Student;
import com.blender.egb.model.StudentClass;
import com.blender.egb.model.StudentDTO;
import com.blender.egb.repository.StudentClassRepository;
import com.blender.egb.util.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class StudentClassController {

	private StudentClassRepository studentClassRepository;

	@Autowired
	public void setStudentClassController(StudentClassRepository studentClassRepository) {
		this.studentClassRepository = studentClassRepository;
	}

	@GetMapping("/admin/classes")
	public String studentClasses(Model model) {
		Iterable<StudentClass> studentClasses = studentClassRepository.findAll();
		model.addAttribute("studentClasses", studentClasses);
		return "classes";
	}

	@GetMapping("/class/{id}")
	public String showClass(@PathVariable(value = "id") long id, Model model) {
		Optional<StudentClass> studentClass = studentClassRepository.findById(id);
		if (studentClass.isPresent()) {
			Set<Student> students = studentClass.get().getStudents();
			List<StudentDTO> studentsDTO = students.stream().map(MappingUtils::mapToStudentDTO).collect(Collectors.toList());
			model.addAttribute("students", studentsDTO);
			model.addAttribute("classCode", studentClass.get().getClassCode());
		}
		return "class";
	}

	@GetMapping("/admin/class/add")
	public String addStudent(Model model) {
		Iterable<String> facultyList = studentClassRepository.fetchAllFaculties();
		Iterable<String> programList = studentClassRepository.fetchAllPrograms();
		model.addAttribute("facultyList", facultyList);
		model.addAttribute("programList", programList);
		model.addAttribute("studentClass", new StudentClass());
		return "class-add";
	}

	@PostMapping("/admin/class/add")
	public String addPostStudent(@ModelAttribute StudentClass studentClass, Model model) {
		studentClassRepository.save(studentClass);
		return "redirect:/classes";
	}
}
