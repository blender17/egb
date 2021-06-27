package com.blender.egb.controller;

import com.blender.egb.model.GradebookDTO;
import com.blender.egb.service.GradebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class GradebookController {

	private GradebookService gradebookService;

	@Autowired
	public void setGradebookService(GradebookService gradebookService) {
		this.gradebookService = gradebookService;
	}

	@GetMapping("/gradebook/{classId}/{subjectId}")
	public String gradebookByClassAndSubject(Model model, @PathVariable long classId, @PathVariable long subjectId) {
		gradebookService.getGradebookByClassAndSubject(model, classId, subjectId);
		return "gradebook";
	}

	@PostMapping("/gradebook/{classId}/{subjectId}")
	public String gradebookByClassAndSubject(@RequestBody List<GradebookDTO> gradebookDTO,
	                                         @PathVariable long classId,
	                                         @PathVariable long subjectId, Model model) {
		gradebookService.saveGradebook(gradebookDTO, classId, subjectId, model);
		return "redirect:/gradebook/" + classId + "/" + subjectId;
	}
}
