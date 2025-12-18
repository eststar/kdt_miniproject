package com.mini.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mini.domain.ToiletInfo;

public interface ToiletInfoRepository extends JpaRepository<ToiletInfo, String>{

}
