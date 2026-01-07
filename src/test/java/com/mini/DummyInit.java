package com.mini;

import java.io.File;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.mini.domain.Reviews;
import com.mini.domain.Role;
import com.mini.domain.ToiletInfo;
import com.mini.dto.MemberDTO;
import com.mini.persistence.MemberRepository;
import com.mini.persistence.ReviewRepository;
import com.mini.persistence.ToiletInfoRepository;

@SpringBootTest
//@Transactional
public class DummyInit{
	@Autowired
	private MemberRepository memRepo;
	@Autowired
	private ToiletInfoRepository toiletRepo;
	@Autowired
	private ReviewRepository reviewRepo;
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
	
//	@Test
	void loadExternalMemberJson() {
		try {
			File file = new File("C:/workspace/testdata/member.json");
			List<Map<String, Object>> dataList = objectMapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {});
			
			for(Map<String, Object> map : dataList) {
				String username = (String)map.get("username");
				String provider = (String)map.get("provider");
				String nickname = (String)map.get("nickname");
//				String password = Integer.toString((Integer)map.get("password"));
				String password = "1234";
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
	
	@Test
	void loadExternalReviewJson() {
		try {
			File file = new File("C:/workspace/testdata/review.json");
			List<Map<String, Object>> dataList = objectMapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {});
			Set<String> nicknameSet = new HashSet<>();
			Map<String, Members> nickMemMap = new HashMap<>();
		
			Set<String> toiletPKSet = new HashSet<>();
			Map<String, ToiletInfo> toiletMap = new HashMap<>();
			
			for(Map<String, Object> map : dataList) {
				String nickname = (String)map.get("user");
				nicknameSet.add(nickname);
				String toiletPk = (String)map.get("data_cd");
				toiletPKSet.add(toiletPk);
			}
			List<Members> dummyMembers = memRepo.findAllByNicknameIn(List.copyOf(nicknameSet));
			List<ToiletInfo> dummyToilets = toiletRepo.findAllById(List.copyOf(toiletPKSet));
			
			for(Members member : dummyMembers) {
				nickMemMap.put(member.getNickname(), member);
			}
			
			for(ToiletInfo info : dummyToilets) {
				toiletMap.put(info.getDataCd(), info);
			}
			
//			int cnt = 0;
			for(Map<String, Object> map : dataList) {
				Long reviewId = Long.parseLong(Integer.toString((Integer)map.get("id")));                   
				String nickname = (String)map.get("user");
				Members member = nickMemMap.get(nickname);
				if(member == null)
					continue;
								
				Integer point = (Integer)map.get("rating");                   
				String content = (String)map.get("text");
				String date = (String)map.get("date");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
				LocalDate createDate = LocalDate.parse(date, formatter);
				
				String toiletPK = (String)map.get("data_cd");				
				ToiletInfo toiletinfo = toiletMap.get(toiletPK);
				if(toiletinfo == null)
					continue;
				
				Reviews review = Reviews.builder()
										.createDate(createDate)
										.content(content)
										.point(point)
										.member(member)
										.toiletinfo(toiletinfo)
										.build();
				reviewRepo.save(review);
//				cnt++;
//				if(cnt > 100)
//					return;
			}
//			Members owner = memRepo.findByNickname(nickname);
		} catch (Exception e) {
			System.err.println("파일을 찾을 수 없거나 읽기 오류: " + e.getMessage());
            e.printStackTrace();
		}
	}
}
