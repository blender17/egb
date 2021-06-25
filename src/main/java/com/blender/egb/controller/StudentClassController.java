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
import org.springframework.web.bind.annotation.PathVariable;

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

	@GetMapping("/class/{id}")
	public String showClass(@PathVariable long id, Model model) {
		Optional<StudentClass> studentClass = studentClassRepository.findById(id);
		if (studentClass.isPresent()) {
			Set<Student> students = studentClass.get().getStudents();
			List<StudentDTO> studentsDTO = students.stream().map(MappingUtils::mapToStudentDTO).collect(Collectors.toList());
			model.addAttribute("students", studentsDTO);
			model.addAttribute("classCode", studentClass.get().getClassCode());
			model.addAttribute("classId", studentClass.get().getClassId());
		}
		return "class";
	}
}
