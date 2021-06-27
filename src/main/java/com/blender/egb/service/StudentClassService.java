package com.blender.egb.service;

import com.blender.egb.model.Student;
import com.blender.egb.model.StudentClass;
import com.blender.egb.model.StudentDTO;
import com.blender.egb.repository.StudentClassRepository;
import com.blender.egb.util.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentClassService {

	private StudentClassRepository studentClassRepository;

	@Autowired
	public void setStudentClassController(StudentClassRepository studentClassRepository) {
		this.studentClassRepository = studentClassRepository;
	}

	public void getById(long id, Model model) {
		Optional<StudentClass> studentClass = studentClassRepository.findById(id);
		if (studentClass.isPresent()) {
			Set<Student> students = studentClass.get().getStudents();
			List<StudentDTO> studentsDTO = students.stream().map(MappingUtils::mapToStudentDTO).collect(Collectors.toList());
			model.addAttribute("students", studentsDTO);
			model.addAttribute("classCode", studentClass.get().getClassCode());
			model.addAttribute("classId", studentClass.get().getClassId());
		}
	}
}
