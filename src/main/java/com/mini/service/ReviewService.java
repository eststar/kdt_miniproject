package com.mini.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mini.domain.Members;
import com.mini.domain.Reviews;
import com.mini.domain.SecurityUser;
import com.mini.domain.ToiletInfo;
import com.mini.dto.AveragePointDTO;
import com.mini.dto.MemberDTO;
import com.mini.dto.ReviewDTO;
import com.mini.dto.ReviewPostDTO;
import com.mini.dto.ReviewReqDTO;
import com.mini.persistence.MemberRepository;
import com.mini.persistence.ReviewRepository;
import com.mini.persistence.ToiletInfoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepo;
	private final ToiletInfoRepository toiletRepo;
	private final MemberRepository memRepo;
	
	
	public List<ReviewDTO> getAllWithMember(ReviewReqDTO reviewReq, SecurityUser userInfo){
		List<Reviews> reviewList = new ArrayList<>();
		List<ReviewDTO> dtoList = new ArrayList<>();
		if(userInfo == null)
			reviewList = reviewRepo.getAllWithMember(reviewReq.getDataCd());
		else
			reviewList = reviewRepo.getAllWithMemberMyReviewFirst(reviewReq.getDataCd(), userInfo.getMemberId());
		
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
	public ReviewDTO postReview(ReviewPostDTO reviewPost, MemberDTO memberdto) {
		ToiletInfo targetToilet = toiletRepo.findById(reviewPost.getDataCd()).orElseThrow(()->new EntityNotFoundException("해당 화장실 정보를 찾을 수 없습니다."));
		Members targetMember = memRepo.getReferenceById(memberdto.getMemberId());
		
		Reviews targetR = reviewRepo.save(Reviews.builder()
				.content(reviewPost.getContent())
				.toiletinfo(targetToilet)
				.point(reviewPost.getPoint())
				.member(targetMember)
				.build());
		
		return ReviewDTO.fromReviewEntity(targetR);
	}
	
	@Transactional
	public ReviewDTO updateReview(ReviewPostDTO reviewUpdate, MemberDTO memberdto, Boolean isAdmin) {
		Reviews targetReview = reviewRepo.findById(reviewUpdate.getReviewId()).orElseThrow(()-> new IllegalArgumentException("리뷰를 찾을 수 없음"));
		
		if(!targetReview.getMember().getMemberId().equals(memberdto.getMemberId()) && !isAdmin)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한 없음");
		
		targetReview.changeContent(reviewUpdate.getContent(), reviewUpdate.getPoint());
		return ReviewDTO.fromReviewEntity(targetReview);
	}
	
	@Transactional
	public Long deleteReview(ReviewPostDTO reviewDelete, MemberDTO memberdto, Boolean isAdmin) {
		Reviews targetReview = reviewRepo.findById(reviewDelete.getReviewId()).orElseThrow(()-> new IllegalArgumentException("리뷰를 찾을 수 없음"));
		Long reviewId = targetReview.getReviewId();
		if(!targetReview.getMember().getMemberId().equals(memberdto.getMemberId()) && !isAdmin)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");
		
		reviewRepo.delete(targetReview);
		return reviewId;
	}
}
