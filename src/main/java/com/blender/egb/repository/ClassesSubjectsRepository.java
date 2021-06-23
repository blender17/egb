package com.blender.egb.repository;

import com.blender.egb.model.ClassesSubjects;
import com.blender.egb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassesSubjectsRepository extends JpaRepository<ClassesSubjects, Long> {

	List<ClassesSubjects> getAllByUserId(User user);
}
