package com.blender.egb.repository;

import com.blender.egb.model.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {

	@Query("SELECT distinct faculty from StudentClass order by faculty asc")
	List<String> fetchAllFaculties();

	@Query("SELECT distinct program from StudentClass order by program asc")
	List<String> fetchAllPrograms();

	@Query("SELECT DISTINCT sc FROM StudentClass sc INNER JOIN sc.subjects t WHERE t.userId.userId = :userId")
	List<StudentClass> getAllClassesByTeacherId(@Param("userId") long userId);

}
