package com.mini.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mini.domain.Reviews;
import com.mini.dto.AveragePointDTO;

public interface ReviewRepository extends JpaRepository<Reviews, Long>{
	
	//화장실에 따른 리뷰전체 조회
	@Query("SELECT r FROM Reviews r JOIN FETCH r.member WHERE r.toiletinfo.dataCd = :dataCd ORDER BY r.createDate DESC ")
	List<Reviews> getAllWithMember(@Param("dataCd")String dataCd);
	
	//리뷰 평균 상위 5
	@Query("SELECT new com.mini.dto.AveragePointDTO(t.toiletNm, ROUND(AVG(COALESCE(r.point, 1.0)), 1), COUNT(r)) " 
			+ " FROM Reviews r JOIN r.toiletinfo t "
			+ " GROUP BY t.dataCd, t.toiletNm "
			+ " HAVING COUNT(r) >= 3 "
			+ " ORDER BY AVG(COALESCE(r.point, 1.0)) DESC, COUNT(r) DESC;")
	List<AveragePointDTO> getAveragePointTopFive(Pageable limit);
	
	//리뷰 평균 하위 5
	@Query("SELECT new com.mini.dto.AveragePointDTO(t.toiletNm, ROUND(AVG(COALESCE(r.point, 1.0)), 1), COUNT(r)) " 
			+ " FROM Reviews r JOIN r.toiletinfo t "
			+ " GROUP BY t.dataCd, t.toiletNm ORDER BY AVG(COALESCE(r.point, 1.0)) ASC, COUNT(r) DESC; ")
	List<AveragePointDTO> getAveragePointBottomFive(Pageable limit);
	
	//리뷰 평균 리스트
	@Query("SELECT new com.mini.dto.AveragePointDTO(t.toiletNm, ROUND(AVG(COALESCE(r.point, 1.0)), 1), COUNT(r)) " 
			+ " FROM Reviews r JOIN r.toiletinfo t "
			+ " GROUP BY t.dataCd, t.toiletNm ORDER BY AVG(COALESCE(r.point, 1.0)) DESC, COUNT(r) DESC;")
	List<AveragePointDTO> getAveragePointAll();
}
