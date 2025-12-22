package com.mini.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.domain.ToiletInfo;
import com.mini.service.ToiletInfoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/test/toiletinfo")
@RequiredArgsConstructor
public class ToiletInfoController {
	private final ToiletInfoService tInfoService;
	
	@GetMapping("/getinfo")
	public List<ToiletInfo> getAllInfo(){
		return tInfoService.getAllInfo();
	}
	
	@GetMapping("/getinfo")
	public ResponseEntity<?> getInfo(ToiletInfo tInfo){
		if(tInfo == null)
			return ResponseEntity.ok(tInfoService.getAllInfo());
		else {
			if(tInfo.getDataCd() != null)
				return ResponseEntity.ok(tInfoService.getInfoById(tInfo.getDataCd()));
		}
		return ResponseEntity.ok(null);
	}
}
