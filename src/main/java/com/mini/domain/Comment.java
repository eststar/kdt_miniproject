package com.mini.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long 			commentId; 	// 리뷰ID
	@Builder.Default
	@Column(columnDefinition = "DATE DEFAULT CURRENT_DATE")
	private LocalDate 	createTime = LocalDate.now(); //작성시간
	@Column(columnDefinition = "TEXT")
	private String 			content; 	//리뷰내용               
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Members 		member; 	//리뷰 작성한 멤버정보
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_Id", nullable = false)
	private Board board;
	
	public void changeContent(String content) {
		this.content = content == null ? this.content : content;
	}
	
	public void addComment(Board board) {
		this.board = board;
		board.getCommentList().add(this);
	}
}
