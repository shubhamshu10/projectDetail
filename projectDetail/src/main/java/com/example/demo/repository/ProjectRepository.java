package com.example.demo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Project;

public interface ProjectRepository extends JpaRepository<Project,Integer>{
	//@Modifying
	@Query(value="select * from project as pr where pr.employee_id=:eid", nativeQuery = true)
    Page<Project> findProjectByEmployee(@Param("eid") int eid,Pageable p);
}
