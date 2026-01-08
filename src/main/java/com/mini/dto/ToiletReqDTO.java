package com.mini.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToiletReqDTO {
	private String 	dataCd;
	private Double 	laCrdnt;
	private Double 	loCrdnt;
	private Integer pageNum = 0;
	private Integer pageSize = 10;
}
