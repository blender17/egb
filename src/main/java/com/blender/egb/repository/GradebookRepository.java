package com.blender.egb.repository;

import com.blender.egb.model.Gradebook;
import com.blender.egb.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GradebookRepository extends JpaRepository<Gradebook, Long> {

	@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
	@Query("SELECT new com.blender.egb.model.Statistic(g.subject.name, " +
			"COUNT(g.attend), SUM(CASE WHEN g.attend = true THEN 1 ELSE 0 END)) " +
			"FROM Gradebook g WHERE g.student.studentId = :studentId GROUP BY g.subject.name")
	List<Statistic> getSubjectsAttendanceByStudentId(@Param("studentId") long studentId);

	@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
	@Query("SELECT new com.blender.egb.model.Statistic(FUNCTION('MONTH', g.date), " +
			"COUNT(g.attend), SUM(CASE WHEN g.attend = true THEN 1 ELSE 0 END)) " +
			"FROM Gradebook g WHERE g.student.studentId = :studentId " +
			"AND g.date BETWEEN :startDate AND :endDate GROUP BY FUNCTION('MONTH', g.date)")
	List<Statistic> getAttendanceByIdGroupByMonth(@Param("studentId") long studentId,
	                                              @Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate);

	@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
	@Query("SELECT new com.blender.egb.model.Statistic(g.subject.name, AVG(g.mark)) " +
			"FROM Gradebook g WHERE g.student.studentId = :studentId GROUP BY g.subject.name")
	List<Statistic> getSubjectsAvgMarksByStudentId(@Param("studentId") long studentId);

	@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
	@Query("SELECT  new com.blender.egb.model.Statistic(FUNCTION('MONTH', g.date), AVG(g.mark)) " +
			"FROM Gradebook g WHERE g.student.studentId = :studentId AND g.date BETWEEN :startDate AND :endDate GROUP BY FUNCTION('MONTH', g.date)")
	List<Statistic> getAvgMarksByIdGroupByMonth(@Param("studentId") long studentId,
	                                            @Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate);


	@Query("SELECT g from Gradebook g where g.classId = :classId AND g.subject.subjectId = :subjectId")
	List<Gradebook> getGradebookByStudentClassIdAndSubjectId(@Param("classId") long classId, @Param("subjectId") long subjectId);

}
