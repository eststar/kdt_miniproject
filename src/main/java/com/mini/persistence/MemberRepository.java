package com.mini.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mini.domain.Members;

public interface MemberRepository extends JpaRepository<Members, String>{

}
