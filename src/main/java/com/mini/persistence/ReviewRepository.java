package com.mini.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mini.domain.Reviews;

public interface ReviewRepository extends JpaRepository<Reviews, Long>{
	
	@Query("SELECT r FROM Review r JOIN FETCH r.toiletinfo JOIN FETCH r.member WHERE r.toiletinfo.dataCd = :toiletinfo")
	List<Reviews> getAllWithToiletinfoAndMember(@Param("toiletinfo") String toiletinfo);
}
