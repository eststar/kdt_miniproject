package com.mini.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mini.domain.Reviews;
import com.mini.dto.AveragePointDTO;

public interface ReviewRepository extends JpaRepository<Reviews, Long>{
	
	@Query("SELECT r FROM Reviews r JOIN FETCH r.member WHERE r.toiletinfo.dataCd = :dataCd")
	List<Reviews> getAllWithMember(@Param("dataCd")String dataCd);
	
	@Query("SELECT new com.mini.dto.AveragePointDTO(t.toiletNm, ROUND(AVG(COALESCE(r.point, 1.0)), 1), COUNT(r)) " 
			+ " FROM Reviews r JOIN r.toiletinfo t "
			+ " GROUP BY t.dataCd, t.toiletNm ORDER BY AVG(COALESCE(r.point, 1.0)) DESC "
			+ " LIMIT 5 ")
	List<AveragePointDTO> getAveragePointTopFive(Pageable limit);
	
	@Query("SELECT new com.mini.dto.AveragePointDTO(t.toiletNm, ROUND(AVG(COALESCE(r.point, 1.0)), 1), COUNT(r)) " 
			+ " FROM Reviews r JOIN r.toiletinfo t "
			+ " GROUP BY t.dataCd, t.toiletNm ORDER BY AVG(COALESCE(r.point, 1.0)) ASC "
			+ " LIMIT 5 ")
	List<AveragePointDTO> getAveragePointBottomFive(Pageable limit);
	
	@Query("SELECT new com.mini.dto.AveragePointDTO(t.toiletNm, ROUND(AVG(COALESCE(r.point, 1.0)), 1), COUNT(r)) " 
			+ " FROM Reviews r JOIN r.toiletinfo t "
			+ " GROUP BY t.dataCd, t.toiletNm ORDER BY AVG(COALESCE(r.point, 1.0)) DESC ")
	List<AveragePointDTO> getAveragePointAll();
}
