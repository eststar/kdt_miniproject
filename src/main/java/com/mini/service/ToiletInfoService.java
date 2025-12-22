package com.mini.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mini.domain.ToiletInfo;
import com.mini.persistence.ToiletInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToiletInfoService {
	
	private final ToiletInfoRepository tInfoRepo;
	
	public List<ToiletInfo> getAllInfo() {
		return tInfoRepo.findAll();
	}
	
	public ToiletInfo getInfoById(String data_cd) {
		return tInfoRepo.findById(data_cd).get();
	}
}
