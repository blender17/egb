package com.blender.egb.controller;

import com.blender.egb.model.Gradebook;
import com.blender.egb.model.GradebookDTO;
import com.blender.egb.model.Student;
import com.blender.egb.repository.GradebookRepository;
import com.blender.egb.repository.StudentRepository;
import com.blender.egb.util.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Controller
public class GradebookController {

	private GradebookRepository gradebookRepository;
	private StudentRepository studentRepository;

	@Autowired
	public void setGradebookRepository(GradebookRepository gradebookRepository) {
		this.gradebookRepository = gradebookRepository;
	}

	@Autowired
	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@GetMapping("/gradebook")
	public String gradebook(Model model) {
		return "gradebook";
	}

	@GetMapping("/gradebook/{classId}/{subjectId}")
	public String gradebookByClassAndSubject(Model model, @PathVariable long classId, @PathVariable long subjectId) {
		List<GradebookDTO> gradebooks = gradebookRepository.getGradebookByStudentClassIdAndSubjectId(classId, subjectId)
				.stream().map(MappingUtils::mapToGradebookDTO).collect(Collectors.toList());
		List<Student> students = studentRepository.getAllByStudentClassClassId(classId);
		List<String> studentsNames = students.stream().map(Student::getName).collect(Collectors.toList());

		TreeSet<LocalDate> dates = new TreeSet<>();
		for (GradebookDTO gradebook : gradebooks) {
			dates.add(gradebook.getDate());
		}
		model.addAttribute("gradebooks", gradebooks);
		model.addAttribute("dates", dates);
		model.addAttribute("students", studentsNames);
		return "gradebook";
	}

	@PostMapping("/gradebook/{classId}/{subjectId}")
	public String gradebookByClassAndSubject(@RequestBody List<GradebookDTO> gradebookDTO,
	                                         @PathVariable long classId,
	                                         @PathVariable long subjectId, Model model) {
		List<Gradebook> gradebooks = gradebookRepository.getGradebookByStudentClassIdAndSubjectId(classId, subjectId);
		List<Gradebook> modifiedGradebooks = MappingUtils.mapGradebooksDTOtoEntityList(gradebookDTO, gradebooks);
		gradebookRepository.saveAll(modifiedGradebooks);
		return "redirect:/gradebook/" + classId + "/" + subjectId;
	}
}
