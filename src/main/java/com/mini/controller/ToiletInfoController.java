package com.mini.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.domain.ToiletInfo;
import com.mini.service.ToiletInfoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ToiletInfoController {
	private final ToiletInfoService tInfoService;
	
	@GetMapping("/getAllToiletInfo")
	public List<ToiletInfo> getAllInfo(){
		return tInfoService.getAllInfo();
	}
}
