package com.blender.egb.util;

import com.blender.egb.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class MappingUtils {

	public static List<ClassesSubjectsDTO> mapToClassSubjectsDTO(List<StudentClass> studentClasses, List<ClassesSubjects> subjects) {
		List<ClassesSubjectsDTO> classesSubjectsDTO = new ArrayList<>();
		for (StudentClass studentClass : studentClasses) {
			ClassesSubjectsDTO subjectsDTO = new ClassesSubjectsDTO();
			Map<Long, String> subjectsMap = new TreeMap<>();
			for (ClassesSubjects classesSubjects : subjects) {
				if (studentClass.getClassId().equals(classesSubjects.getStudentClassId().getClassId())) {
					if (subjectsDTO.getClassId() == null) {
						subjectsDTO.setClassId(studentClass.getClassId());
						subjectsDTO.setClassCode(studentClass.getClassCode());
					}
					subjectsMap.put(classesSubjects.getSubject().getSubjectId(), classesSubjects.getSubject().getName());
				}
			}
			subjectsDTO.setSubjects(subjectsMap);
			classesSubjectsDTO.add(subjectsDTO);
		}
		return classesSubjectsDTO;
	}

	public static StudentDTO mapToStudentDTO(Student student) {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setStudentId(student.getStudentId());
		studentDTO.setFirstName(student.getFirstName());
		studentDTO.setLastName(student.getLastName());
		studentDTO.setGender(student.getGender());
		studentDTO.setBirthday(student.getBirthday());
		studentDTO.setStudentClass(student.getStudentClass());
		studentDTO.setAvgMark(Utils.avgMark(student.getGradebooks()));
		studentDTO.setAttendancePercentage(Utils.attendancePercentage(student.getGradebooks()));
		return studentDTO;
	}

	public static GradebookDTO mapToGradebookDTO(Gradebook gradebook) {
		GradebookDTO gradebookDTO = new GradebookDTO();
		gradebookDTO.setName(gradebook.getStudent().getName());
		gradebookDTO.setDate(gradebook.getDate());
		String value = "";
		if (gradebook.getAttend()) {
			if (gradebook.getMark() != null) {
				value = String.valueOf(gradebook.getMark());
			}
		} else if(!gradebook.getAttend()) {
			value = "X";
		}
		gradebookDTO.setValue(value);
		return gradebookDTO;
	}

	public static List<Gradebook> mapGradebooksDTOtoEntityList(List<GradebookDTO> gradebooksDTO, List<Gradebook> gradebooks) {
		for (int i = 0; i < gradebooks.size(); i++) {
			for (int j = 0; j < gradebooksDTO.size(); j++) {
				if (gradebooks.get(i).getStudent().getName().equals(gradebooksDTO.get(j).getName())
						&& gradebooks.get(i).getDate().toString().equals(gradebooksDTO.get(j).getName())) {
					switch (gradebooksDTO.get(j).getValue()) {
						case "":
							gradebooks.get(i).setAttend(true);
							gradebooksDTO.remove(j);
							break;
						case "X":
							gradebooks.get(i).setAttend(false);
							gradebooksDTO.remove(j);
							break;
						default:
							gradebooks.get(i).setAttend(true);
							gradebooks.get(i).setMark(Integer.parseInt(gradebooksDTO.get(j).getValue()));
							gradebooksDTO.remove(j);
							break;
					}
				}
			}
		}
		if (!gradebooksDTO.isEmpty()) {
			for (int i = 0; i <gradebooksDTO.size(); i++) {
				Gradebook gradebook = new Gradebook();
				int finalI = i;
				Gradebook tempGradebook = gradebooks.stream().filter(gradebook1 -> gradebooksDTO.get(finalI).getName()
						.equals(gradebook1.getStudent().getName())).findAny().orElse(null);
				if (tempGradebook != null) {
					gradebook.setStudent(tempGradebook.getStudent());
					gradebook.setDate(gradebooksDTO.get(i).getDate());
					gradebook.setSubject(tempGradebook.getSubject());
					gradebook.setClassCode(tempGradebook.getClassCode());
					switch (gradebooksDTO.get(i).getValue()) {
						case "":
							gradebook.setAttend(true);
							break;
						case "X":
							gradebook.setAttend(false);
							break;
						default:
							gradebook.setMark(Integer.parseInt(gradebooksDTO.get(i).getValue()));
							gradebook.setAttend(true);
							break;
					}
				}
			}
		}
		return gradebooks;
	}

	public static Map<String, Double> monthlyStatisticAsMap(List<Statistic> statisticList, String statisticType) {
		Map<String, Double> statisticMap;

		if (statisticType.equals("monthly")) {
			statisticMap = statisticList.stream()
					.collect(Collectors.toMap(Statistic::getMonth, Statistic::getStatistic));
		} else if (statisticType.equals("subject")) {
			statisticMap = statisticList.stream()
					.collect(Collectors.toMap(Statistic::getSubject, Statistic::getStatistic));
		} else throw new IllegalStateException("Unexpected value: " + statisticType);

		return statisticMap;
	}

}