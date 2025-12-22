package com.mini.domain;

import java.time.OffsetDateTime;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Immutable
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class ToiletInfo {
	
	@Id
	@Column(name = "data_cd")
	private String dataCd;	//text //데이터코드
	private OffsetDateTime regDt;	//timestamp with time zone // 등록일시
	private Double laCrdnt;	//double precision // 위도좌표
	private Double loCrdnt;	//double precision // 경도좌표
	private String seNm; // 구분명
	private String emdNm; // 읍면동명
	private String rnAdres;	// 도로명주소
	private String lnmAdres;// 지번주소
	private String photoYn;	// 사진여부
	private String etcCn;	// 기타내용
	private String toiletNm; // 화장실명
	private String opnTimeInfo;	// 개방시간정보
	private String mngrInsttNm;	// 관리기관명
	private String telno;	// 전화번호
	private String toiletPosesnSeNm; // 화장실소유구분명
	private String toiletInstlPlacePttnNm;	// 화장실설치장소유형명
	private String filthPrcsMthdNm;	// 오물처리방식명
	private String emgncBellInstlYn;	// 비상벨설치여부
	private String toiletEntrncCctvInstlYn;	// 화장실입구CCTV설치여부
	private String diaperExhgTablYn;	// 기저귀교환탁자여부
	private String mwmnCmnuseToiletYn;	// 남녀공용화장실여부
	private Long maleClosetCnt;	//bigint // 남성대변기수
	private Long maleUrinalCnt;	//bigint // 남성소변기수
	private Long maleDspsnClosetCnt;	//bigint // 남성장애인대변기수
	private Long maleDspsnUrinalCnt;	//bigint // 남성장애인소변기수
	private Long maleChildClosetCnt;	 // bigint 남성어린이대변기수
	private Long maleChildUrinalCnt;	// bigint 남성어린이소변기수
	private Long femaleClosetCnt;	//bigint // 여성대변기수
	private Long femaleDspsnClosetCnt;	//bigint // 여성장애인대변기수
	private Long femaleChildClosetCnt;	//bigint 여성어린이대변기수
	private String photo; // 사진 데이터
	
}
