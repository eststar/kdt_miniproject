package com.mini.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRespDTO<T> {
	private List<T> dtoList;      
    private int pageNum;          // 현재 페이지
    private int pageSize;
    private int totalPages;       // 전체 페이지 수
    private long totalElements;   // 전체 데이터 수
    private boolean isLast;       // 마지막 페이지 여부
    private boolean isFirst;      // 첫 페이지 여부
    
    public PageRespDTO(Page<?> page, List<T> dtoList) {
    	this.dtoList = dtoList;   
    	this.pageNum = page.getNumber();       
    	this.totalPages = page.getTotalPages();    
    	this.totalElements = page.getTotalElements();
    	this.isLast = page.isLast();  
    	this.isFirst = page.isFirst();
    	this.pageSize = page.getSize();
    }          
}
