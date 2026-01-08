package com.mini.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mini.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
	
}
