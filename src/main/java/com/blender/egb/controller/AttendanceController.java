package com.blender.egb.controller;

import com.blender.egb.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AttendanceController {

	private final AttendanceRepository attendanceRepository;

	@Autowired
	public AttendanceController(AttendanceRepository attendanceRepository) {
		this.attendanceRepository = attendanceRepository;
	}

	@GetMapping("/attendance")
	public String attendanceHome(Model model) {
		model.addAttribute(attendanceRepository.findAll());
		return "attendance";
	}
}
