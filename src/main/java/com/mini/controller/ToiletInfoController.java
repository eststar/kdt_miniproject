package com.mini.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.dto.ToiletReqDTO;
import com.mini.service.ToiletInfoService;

import lombok.RequiredArgsConstructor;

//@Data
//class ToiletFilter {
//	private String 	dataCd;
//	private Double 	laCrdnt;
//	private Double 	loCrdnt;
//	private Integer pageNum = 0;
//	private Integer pageSize = 10;
//}
@RestController
@RequestMapping("/api/test/toiletinfo")
@RequiredArgsConstructor
public class ToiletInfoController {
	private final ToiletInfoService tInfoService;
	
	@GetMapping("/getallinfo")
	public ResponseEntity<?> getAllInfo(){
		return ResponseEntity.ok(tInfoService.getAllInfo());
	}
	
	@GetMapping("/getinfo")
	public ResponseEntity<?> getInfo(@RequestBody ToiletReqDTO tInfo){		
		return ResponseEntity.ok(tInfoService.getInfoById(tInfo.getDataCd()));		
	}
}
