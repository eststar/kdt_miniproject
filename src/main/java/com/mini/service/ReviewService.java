package com.mini.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mini.domain.Members;
import com.mini.domain.Reviews;
import com.mini.domain.ToiletInfo;
import com.mini.dto.AveragePointDTO;
import com.mini.dto.ReviewDTO;
import com.mini.dto.ReviewReqDTO;
import com.mini.persistence.MemberRepository;
import com.mini.persistence.ReviewRepository;
import com.mini.persistence.ToiletInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepo;
	private final ToiletInfoRepository toiletRepo;
	private final MemberRepository memRepo;
	
	
	public List<ReviewDTO> getAllWithMember(String dataCd){
		List<Reviews> reviewList = reviewRepo.getAllWithMember(dataCd);
		List<ReviewDTO> dtoList = new ArrayList<>();
		
		for(Reviews r : reviewList) {
			dtoList.add(ReviewDTO.fromReviewEntity(r));
		}
		return dtoList;
	}
	
	public List<AveragePointDTO> getAveragePointStat(ReviewReqDTO reqdto) {
		if(reqdto.getTopBottom() == null)
			return reviewRepo.getAveragePointAll();
		if(reqdto.getTopBottom().equalsIgnoreCase("top"))
			return reviewRepo.getAveragePointTopFive(PageRequest.of(0, 5));
		else if(reqdto.getTopBottom().equalsIgnoreCase("bottom"))
			return reviewRepo.getAveragePointBottomFive(PageRequest.of(0, 5));
		else
			throw new IllegalArgumentException("Invalid topBottom value: " + reqdto.getTopBottom());
	}
	
	//멤버 로그인 상태에서만 가능하도록 해야함
	public void postReview(ReviewDTO reviewDTO) {
		ToiletInfo targetToilet = toiletRepo.getReferenceById(reviewDTO.getDataCd());
		Members targetMember = memRepo.getReferenceById(reviewDTO.getMember().getMemberId());
		
		reviewRepo.save(Reviews.builder()
				.content(reviewDTO.getContent())
				.toiletinfo(targetToilet)
				.point(reviewDTO.getPoint())
				.member(targetMember)
				.build());
	}
}
