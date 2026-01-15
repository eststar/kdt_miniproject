package com.mini.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mini.domain.Board;
import com.mini.domain.Members;

public interface BoardRepository extends JpaRepository<Board, Long>{
	
	@Query("SELECT b FROM Board b JOIN FETCH b.member ORDER BY b.createDate DESC")
	Page<Board> getPageWithMembers(Pageable limit);
	
	@Query("SELECT b FROM Board b JOIN FETCH b.member ORDER BY b.createDate DESC")
	List<Board> getAllWithMembersAndComment();
	
	@Query("SELECT b FROM Board b JOIN FETCH b.member WHERE b.boardId = :id ")
	Optional<Board> getBoardDetailByIdWithMember(@Param("id") Long id);
	
}
