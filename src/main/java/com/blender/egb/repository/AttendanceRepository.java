package com.blender.egb.repository;

import com.blender.egb.model.Attendance;
import com.blender.egb.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
	@Query("SELECT new com.blender.egb.model.Statistic(a.subject, " +
			"COUNT(a.attend), SUM(CASE WHEN a.attend = true THEN 1 ELSE 0 END)) " +
			"FROM Attendance a WHERE a.student.studentId = :studentId GROUP BY a.subject")
	List<Statistic> getSubjectsAttendanceByStudentId(@Param("studentId") long studentId);

	@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
	@Query("SELECT new com.blender.egb.model.Statistic(FUNCTION('MONTH', a.date), " +
			"COUNT(a.attend), SUM(CASE WHEN a.attend = true THEN 1 ELSE 0 END)) " +
			"FROM Attendance a WHERE a.student.studentId = :studentId " +
			"AND a.date BETWEEN :startDate AND :endDate GROUP BY FUNCTION('MONTH', a.date)")
	List<Statistic> getAttendanceByIdGroupByMonth(@Param("studentId") long studentId,
	                                              @Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate);

}