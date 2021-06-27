package com.blender.egb.service;

import com.blender.egb.model.Statistic;
import com.blender.egb.model.Student;
import com.blender.egb.repository.GradebookRepository;
import com.blender.egb.repository.StudentRepository;
import com.blender.egb.util.MappingUtils;
import com.blender.egb.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

	private StudentRepository studentRepository;
	private GradebookRepository gradebookRepository;

	@Autowired
	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Autowired
	public void setGradebookRepository(GradebookRepository gradebookRepository) {
		this.gradebookRepository = gradebookRepository;
	}


	public void getStudentById(long id, Model model) {
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
		model.addAttribute("avgMarksBySubject", avgMarksBySubjects);
		model.addAttribute("avgMarksByMonth", avgMarksByMonth);
		model.addAttribute("attendanceBySubject", attendanceBySubjects);
		model.addAttribute("monthlyAttendance", monthlyAttendance);
		model.addAttribute("student", student);
	}

}
