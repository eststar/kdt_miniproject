package com.mini.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mini.domain.Reviews;
import com.mini.persistence.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepo;
	
	public List<Reviews> getAllWithToiletinfoAndMember(String toiletinfo){
		return null;
	}
}
