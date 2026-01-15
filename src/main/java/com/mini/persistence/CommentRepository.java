package com.mini.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mini.domain.Board;
import com.mini.domain.Comment;
import com.mini.domain.Members;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	Long countByBoard_BoardId(Long boardId);
	
	//오래된 댓글이 맨위로. 오름차순으로
	@Query("SELECT c FROM Comment c JOIN FETCH c.member WHERE c.board.boardId= :boardId" 
			+ " ORDER BY c.createTime ASC ")
	List<Comment> getAllByBoardIdWithMember(@Param("boardId") Long boardId);
	
	@Query("SELECT c FROM Comment c JOIN FETCH c.member WHERE c.board.boardId= :boardId" 
			+ " ORDER BY c.createTime ASC ")
	Page<Comment> getCommentPageWithMember(@Param("boardId") Long boardId, Pageable page);
	
}
