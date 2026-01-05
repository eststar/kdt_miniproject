package com.mini;

import java.io.File;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.domain.Members;
import com.mini.domain.Provider;
import com.mini.domain.Role;
import com.mini.dto.MemberDTO;
import com.mini.persistence.MemberRepository;

@SpringBootTest
//@Transactional
public class MemberInit{
	@Autowired
	private MemberRepository memRepo;
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	@Autowired
    private ObjectMapper objectMapper;
//	@Test
//	public void testInsert() {
//		//테스트용 어드민 계정 생성
//		String username = "admin";
//		
//		memRepo.save(Members.builder().memberId("LOCAL_"+username)
//										.username(username)
//										.password(encoder.encode("test1234"))
//										.createDate(OffsetDateTime.now()).role(Role.ROLE_ADMIN)
//										.nickname("ADMINTest01").provider(Provider.LOCAL)
//										.enabled(true)
//										.build());
//	}
	
	@Test
	void loadExternalJson() {
		try {
			File file = new File("C:/workspace/testdata/member.json");
			List<Map<String, Object>> dataList = objectMapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {});
			
			for(Map<String, Object> map : dataList) {
				String username = (String)map.get("username");
				String provider = (String)map.get("provider");
				String nickname = (String)map.get("nickname");
				String password = Integer.toString((Integer)map.get("password"));
				String memberId = provider.toUpperCase()+"_"+username;
				OffsetDateTime createTime = OffsetDateTime.parse((String)map.get("create_date"));
				
				Members member = Members.builder().memberId(memberId)
														.username(username)
														.provider(Provider.findByString(provider))
														.nickname(nickname)
														.password(encoder.encode(password))
														.createDate(createTime)
														.enabled(true)
														.role(Role.ROLE_MEMBER)
														.build();
				memRepo.save(member);
			}
			
		} catch (Exception e) {
			System.err.println("파일을 찾을 수 없거나 읽기 오류: " + e.getMessage());
            e.printStackTrace();
		}
	}
}
