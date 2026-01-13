package com.mini.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mini.domain.Board;
import com.mini.domain.Members;
import com.mini.dto.BoardReqDTO;
import com.mini.dto.BoardRespDTO;
import com.mini.dto.MemberDTO;
import com.mini.dto.PageRespDTO;
import com.mini.persistence.BoardRepository;
import com.mini.persistence.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository bRepo;
	private final MemberRepository memRepo;
	
	public List<BoardRespDTO> getBoardWithMemberAndComment(){
		List<Board> boardList = bRepo.getAllWithMembersAndComment();
		
		List<BoardRespDTO> dtoList = new ArrayList<>();
		
		for(Board b : boardList) {
			dtoList.add(BoardRespDTO.fromBoardEntity(b));
		}
		
		return dtoList;
	}
	
	public PageRespDTO<BoardRespDTO> getBoardPageWithMemberAndComment(BoardReqDTO boardReq){
		Page<Board> boardPage = bRepo.getPageWithMembers(PageRequest.of(boardReq.getPageNum(), boardReq.getPageSize()));
		
		List<BoardRespDTO> dtoList = new ArrayList<>();
		
		for(Board b : boardPage.getContent()) {
			dtoList.add(BoardRespDTO.fromBoardEntity(b));
		}
		
		return new PageRespDTO<>(boardPage, dtoList);
	}
	
	public BoardRespDTO getBoardDetailWithMember(Long boardId) {
		Board targetB = bRepo.getBoardByIdWithMember(boardId).orElseThrow(()->new EntityNotFoundException("해당 게시글을 찾을 수 없습니다."));
		return BoardRespDTO.fromBoardEntity(targetB);
	}
	
	public List<BoardRespDTO> getAllWithMember(){
		List<Board> boardList = bRepo.getAllWithMembersAndComment();
		List<BoardRespDTO> dtoList = new ArrayList<>();
		
		for(Board b : boardList) {
			dtoList.add(BoardRespDTO.fromBoardEntity(b));
		}
		
		return dtoList;
	}
	
	public BoardRespDTO postBoard(BoardReqDTO boardReq, MemberDTO memberDTO) {
		Members currentMember =  memRepo.getReferenceById(memberDTO.getMemberId());
		
		Board targetB = bRepo.save(Board.builder()
										.title(boardReq.getTitle())
										.content(boardReq.getContent())
										.member(currentMember)
										.build());
		
		return BoardRespDTO.fromBoardEntity(targetB);
	}
	
	
	@Transactional
	public BoardRespDTO updateBoard(BoardReqDTO boardUpdate, MemberDTO memberdto, Boolean isAdmin) {
		Board targetBoard = bRepo.findById(boardUpdate.getBoardId()).orElseThrow(()-> new IllegalArgumentException("게시글을 찾을 수 없음"));
		
		if(!targetBoard.getMember().getMemberId().equals(memberdto.getMemberId()) && !isAdmin)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한 없음");
		
		targetBoard.changeContent(boardUpdate.getContent(), boardUpdate.getTitle());
		return BoardRespDTO.fromBoardEntity(targetBoard);
	}
	
	@Transactional
	public Long deleteBoard(Long targetBoardId, MemberDTO memberdto, Boolean isAdmin) {
		Board targetBoard = bRepo.findById(targetBoardId).orElseThrow(()-> new IllegalArgumentException("게시글을 찾을 수 없음"));
		Long boardId = targetBoard.getBoardId();
		if(!targetBoard.getMember().getMemberId().equals(memberdto.getMemberId()) && !isAdmin)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");
		
		bRepo.delete(targetBoard);
		return boardId;
	} 
}
