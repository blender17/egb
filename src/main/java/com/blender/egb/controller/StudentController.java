package com.blender.egb.controller;

import com.blender.egb.model.Statistic;
import com.blender.egb.model.Student;
import com.blender.egb.model.StudentClass;
import com.blender.egb.model.StudentDTO;
import com.blender.egb.repository.AttendanceRepository;
import com.blender.egb.repository.MarksRepository;
import com.blender.egb.repository.StudentClassRepository;
import com.blender.egb.repository.StudentRepository;
import com.blender.egb.util.MappingUtils;
import com.blender.egb.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class StudentController {

	private final StudentRepository studentRepository;
	private final StudentClassRepository studentClassRepository;
	private final AttendanceRepository attendanceRepository;
	private final MarksRepository marksRepository;


	@Autowired
	public StudentController(StudentRepository studentRepository,
	                         StudentClassRepository studentClassRepository, AttendanceRepository attendanceRepository, MarksRepository marksRepository) {
		this.studentRepository = studentRepository;
		this.studentClassRepository = studentClassRepository;
		this.attendanceRepository = attendanceRepository;
		this.marksRepository = marksRepository;
	}


	@GetMapping("/students")
	public String showStudent(Model model) {
		List<Student> students = studentRepository.findAll();
		List<StudentDTO> studentsDTO = students.stream()
				.map(MappingUtils::mapToStudentDTO).collect(Collectors.toList());
		model.addAttribute("students", studentsDTO);
		return "students-list";
	}

	@GetMapping("/student/{id}")
	public String showStudent(@PathVariable(value = "id") long id, Model model) {
		Student student = studentRepository.findById(id).orElseThrow();

		student.setPhotoUrl(((student.getPhotoUrl() == null)
				|| (student.getPhotoUrl().equals("")))
				? "https://png.pngtree.com/png-vector/20190116/ourlarge/pngtree-vector-male-student-icon-png-image_322034.jpg" : student.getPhotoUrl());

		List<Statistic> avgMarksBySubjects = marksRepository.getSubjectsAvgMarksByStudentId(id);
		Map<String, Double> avgMarksByMonth = MappingUtils.monthlyStatisticAsMap(marksRepository
				.getAvgMarksByIdGroupByMonth(id, LocalDate.now().minusMonths(5), LocalDate.now()), "monthly");

		List<Statistic> attendanceBySubjects = attendanceRepository.getSubjectsAttendanceByStudentId(id);
		Map<String, Double> monthlyAttendance = MappingUtils.monthlyStatisticAsMap(attendanceRepository
				.getAttendanceByIdGroupByMonth(id, LocalDate.now().minusMonths(5), LocalDate.now()), "monthly");

		model.addAttribute("avgMark", Utils.avgMark(student.getMarks())) ;
		model.addAttribute("attendance", Utils.attendancePercentage(student.getAttendances()));
		model.addAttribute("age", Period.between(student.getBirthday(), LocalDate.now()).getYears());
		model.addAttribute("avgMarksBySubject", avgMarksBySubjects);
		model.addAttribute("avgMarksByMonth", avgMarksByMonth);
		model.addAttribute("attendanceBySubject", attendanceBySubjects);
		model.addAttribute("monthlyAttendance", monthlyAttendance);
		model.addAttribute("student", student);

		return "student-page";
	}

	@GetMapping("/student/add")
	public String addStudent(Model model) {
		Iterable<StudentClass> studentClasses = studentClassRepository.findAll();
		model.addAttribute("studentClasses", studentClasses);
		model.addAttribute("student", new Student());
		return "student-add";
	}

	@PostMapping("/student/add")
	public String addPostStudent(@ModelAttribute("student") Student student,
	                             @RequestParam(value = "classId")
			                             Long classId, Model model) {
		StudentClass studentClass = studentClassRepository.findById(classId).orElseThrow();
		student.setStudentClass(studentClass);
		studentRepository.save(student);
		return "redirect:/students";
	}

	@DeleteMapping("/student/{id}/delete")
	public String deleteStudent(@PathVariable(value = "id") long id, Model model) {
		studentRepository.deleteById(id);
		return "redirect:/students";
	}

}
