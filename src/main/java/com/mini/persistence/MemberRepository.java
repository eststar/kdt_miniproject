package com.mini.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mini.domain.Members;
import com.mini.domain.Provider;

public interface MemberRepository extends JpaRepository<Members, String>{
	Optional<Members> findByUsernameAndProvider(String username, Provider provider);
	Boolean existsByUsernameAndProvider(String username, Provider provider);
	Boolean existsByUsername(String username);
	Boolean existsByNickname(String nickname);
	Optional<Members> findByNickname(String nickname);
	List<Members> findAllByNicknameIn(List<String> nicknamelist);
	
	@Query("SELECT m FROM Members m WHERE m.nickname IN :nicknamelist")	
	List<Members> getMembersByNicknames(@Param("nicknamelist") List<String> nicknamelist);
}
