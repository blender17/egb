package com.blender.egb.service;

import com.blender.egb.model.Gradebook;
import com.blender.egb.model.GradebookDTO;
import com.blender.egb.model.Student;
import com.blender.egb.model.Subject;
import com.blender.egb.repository.GradebookRepository;
import com.blender.egb.repository.StudentRepository;
import com.blender.egb.repository.SubjectsRepository;
import com.blender.egb.util.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;


@Service
public class GradebookService {

	private GradebookRepository gradebookRepository;
	private StudentRepository studentRepository;
	private SubjectsRepository subjectsRepository;

	@Autowired
	public void setGradebookRepository(GradebookRepository gradebookRepository) {
		this.gradebookRepository = gradebookRepository;
	}

	@Autowired
	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Autowired
	public void setSubjectsRepository(SubjectsRepository subjectsRepository) {
		this.subjectsRepository = subjectsRepository;
	}

 	public void getGradebookByClassAndSubject(Model model, long classId, long subjectId) {
	    List<GradebookDTO> gradebooks = gradebookRepository.getGradebookByStudentClassIdAndSubjectId(classId, subjectId)
			    .stream().map(MappingUtils::mapToGradebookDTO).collect(Collectors.toList());
	    List<Student> students = studentRepository.getAllByStudentClassClassId(classId);
	    List<String> studentsNames = students.stream().map(Student::getName).collect(Collectors.toList());

	    TreeSet<LocalDate> dates = new TreeSet<>();
	    for (GradebookDTO gradebook : gradebooks) {
		    dates.add(gradebook.getDate());
	    }

	    String classCode = "";
	    String subject = subjectsRepository.getById(subjectId).getName();
	    if (!students.isEmpty()) {
	    	classCode = students.get(0).getStudentClass().getClassCode();
	    }

	    model.addAttribute("classCode", classCode);
	    model.addAttribute("subject", subject);
	    model.addAttribute("gradebooks", gradebooks);
	    model.addAttribute("dates", dates);
	    model.addAttribute("students", studentsNames);
    }


	public void saveGradebook(List<GradebookDTO> gradebookDTO, long classId, long subjectId, Model model) {
		List<Student> students = studentRepository.getAllByStudentClassClassId(classId);
		Subject subject = subjectsRepository.getById(subjectId);
		List<Gradebook> gradebooks = gradebookRepository.getGradebookByStudentClassIdAndSubjectId(classId, subjectId);
		List<Gradebook> modifiedGradebooks;
		if (gradebooks.isEmpty()) {
			modifiedGradebooks = MappingUtils.mapGradebooksDTOtoEntityList(gradebookDTO, students, subject);
		} else {
			modifiedGradebooks = MappingUtils.mapGradebooksDTOtoEntityList(gradebookDTO, gradebooks);
		}
		gradebookRepository.saveAll(modifiedGradebooks);
	}

}
