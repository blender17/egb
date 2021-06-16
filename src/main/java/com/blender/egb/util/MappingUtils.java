package com.blender.egb.util;

import com.blender.egb.model.Statistic;
import com.blender.egb.model.Student;
import com.blender.egb.model.StudentDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MappingUtils {

	public static StudentDTO mapToStudentDTO(Student student) {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setStudentId(student.getStudentId());
		studentDTO.setFirstName(student.getFirstName());
		studentDTO.setLastName(student.getLastName());
		studentDTO.setGender(student.getGender());
		studentDTO.setBirthday(student.getBirthday());
		studentDTO.setStudentClass(student.getStudentClass());
		studentDTO.setAvgMark(Utils.avgMark(student.getMarks()));
		studentDTO.setAttendancePercentage(Utils.attendancePercentage(student.getAttendances()));
		return studentDTO;
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