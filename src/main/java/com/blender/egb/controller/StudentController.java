package com.blender.egb.controller;

import com.blender.egb.model.Statistic;
import com.blender.egb.model.Student;
import com.blender.egb.repository.GradebookRepository;
import com.blender.egb.repository.StudentRepository;
import com.blender.egb.util.MappingUtils;
import com.blender.egb.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;

@Controller
public class StudentController {

	private StudentRepository studentRepository;
	//private StudentClassRepository studentClassRepository;
	private GradebookRepository gradebookRepository;

	@Autowired
	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	/*@Autowired
	public void setStudentClassRepository(StudentClassRepository studentClassRepository) {
		this.studentClassRepository = studentClassRepository;
	}*/

	@Autowired
	public StudentController(GradebookRepository gradebookRepository) {
		this.gradebookRepository = gradebookRepository;
	}


	@GetMapping("/student/{id}")
	public String showStudent(@PathVariable(value = "id") long id, Model model) {
		Student student = studentRepository.findById(id).orElseThrow();

		student.setPhotoUrl(((student.getPhotoUrl() == null)
				|| (student.getPhotoUrl().equals("")))
				? "https://png.pngtree.com/png-vector/20190116/ourlarge/pngtree-vector-male-student-icon-png-image_322034.jpg"
				: student.getPhotoUrl());

		List<Statistic> avgMarksBySubjects = gradebookRepository.getSubjectsAvgMarksByStudentId(id);
		Map<String, Double> avgMarksByMonth = MappingUtils.monthlyStatisticAsMap(gradebookRepository
				.getAvgMarksByIdGroupByMonth(id, LocalDate.now().minusMonths(5), LocalDate.now()), "monthly");

		List<Statistic> attendanceBySubjects = gradebookRepository.getSubjectsAttendanceByStudentId(id);
		Map<String, Double> monthlyAttendance = MappingUtils.monthlyStatisticAsMap(gradebookRepository
				.getAttendanceByIdGroupByMonth(id, LocalDate.now().minusMonths(5), LocalDate.now()), "monthly");

		model.addAttribute("avgMark", Utils.avgMark(student.getGradebooks())) ;
		model.addAttribute("attendance", Utils.attendancePercentage(student.getGradebooks()));

		model.addAttribute("age", Period.between(student.getBirthday(), LocalDate.now()).getYears());
		model.addAttribute("avgMarksBySubject", avgMarksBySubjects);
		model.addAttribute("avgMarksByMonth", avgMarksByMonth);
		model.addAttribute("attendanceBySubject", attendanceBySubjects);
		model.addAttribute("monthlyAttendance", monthlyAttendance);
		model.addAttribute("student", student);

		return "student-page";
	}

}
