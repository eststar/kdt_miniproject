package com.mini;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mini.domain.Members;
import com.mini.domain.Provider;
import com.mini.domain.Role;
import com.mini.persistence.MemberRepository;

@SpringBootTest
public class MemberInit{
	@Autowired
	private MemberRepository memRepo;
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Test
	public void testInsert() {
		//테스트용 어드민 계정 생성
		String username = "adminToiletInfo";
		
		memRepo.save(Members.builder().memberId("LOCAL_"+username)
										.username(username)
										.password(encoder.encode("kdt202503toilet"))
										.createDate(OffsetDateTime.now()).role(Role.ROLE_ADMIN)
										.nickname("ADMINTest01").provider(Provider.LOCAL)
										.enabled(true)
										.build());
	}
}
