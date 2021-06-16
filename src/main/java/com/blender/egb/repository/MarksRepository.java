package com.blender.egb.repository;

import com.blender.egb.model.Mark;
import com.blender.egb.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MarksRepository extends JpaRepository<Mark, Long> {

	@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
	@Query("SELECT new com.blender.egb.model.Statistic(m.subject, AVG(m.mark)) " +
			"FROM Mark m WHERE m.student.studentId = :studentId GROUP BY m.subject")
	List<Statistic> getSubjectsAvgMarksByStudentId(@Param("studentId") long studentId);

	@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
	@Query("SELECT  new com.blender.egb.model.Statistic(FUNCTION('MONTH', m.date), AVG(m.mark)) " +
			"FROM Mark m WHERE m.student.studentId = :studentId AND m.date BETWEEN :startDate AND :endDate GROUP BY FUNCTION('MONTH', m.date)")
	List<Statistic> getAvgMarksByIdGroupByMonth(@Param("studentId") long studentId,
	                                                 @Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate);

}
