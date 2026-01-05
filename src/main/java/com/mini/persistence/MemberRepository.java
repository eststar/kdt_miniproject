package com.mini.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mini.domain.Members;
import com.mini.domain.Provider;

public interface MemberRepository extends JpaRepository<Members, String>{
	Optional<Members> findByUsernameAndProvider(String username, Provider provider);
	Boolean existsByUsernameAndProvider(String username, Provider provider);
	Boolean existsByUsername(String username);
	Boolean existsByNickname(String nickname);
}
