# 프로젝트 분석 보고서: KDT_MiniProject (화장실 정보 및 커뮤니티 서비스)

이 보고서는 `KDT_MiniProject` 프로젝트의 소스 코드를 분석하여 아키텍처, 기술 스택, 핵심 기능 및 구현 세부 사항을 정리한 것입니다.

## 1. 프로젝트 개요
**KDT_MiniProject**는 공용 화장실 정보를 제공하고, 사용자 간의 리뷰 공유 및 자유로운 소통을 목적으로 하는 웹 서비스의 **백엔드 시스템**입니다. Spring Boot를 기반으로 하며, 데이터의 영속성을 위해 클라우드 DB(Supabase)와 연동되어 있습니다.

## 2. 기술 스택 (Tech Stack)
- **언어 및 환경**: Java 21, Maven
- **프레임워크**: Spring Boot 3.5.8
- **데이터베이스**: PostgreSQL (Supabase 호스팅), Spring Data JPA
- **보안 및 인증**: Spring Security, JWT (Auth0), OAuth 2.0 (Google, Naver)
- **유틸리티**: Lombok, Jackson (JSON 파싱), SpringDoc OpenAPI (Swagger UI)
- **외부 연동**: OAuth2 소셜 로그인, Supabase 클라우드 데이터베이스

## 3. 핵심 기능 (Key Features)

### 3.1. 사용자 관리 및 인증 (Member & Security)
- **자체 로그인/회원가입**: ID/PW 기반의 가입 및 BCrypt 암호화를 통한 보안 강화.
- **소셜 로그인 (OAuth2)**: Google, Naver 계정을 이용한 간편 로그인 지원.
- **JWT 기반 인증**: 무상태(Stateless) 아키텍처를 위해 JWT 토큰 발급 및 검증 로직 구현.
- **권한 관리**: 일반 사용자(`ROLE_MEMBER`)와 관리자(`ROLE_ADMIN`) 권한 분리.

### 3.2. 화장실 정보 관리 (Toilet Info)
- **공공 데이터 조회**: 화장실 위치, 이름, 편의시설 등의 정보 제공.
- **데이터 로딩**: `DummyInit.java`를 통해 외부 JSON 파일에서 대량의 화장실 데이터를 DB로 초기화하는 기능 포함.

### 3.3. 리뷰 및 평점 시스템 (Reviews)
- **사용자 리뷰**: 특정 화장실에 대한 텍스트 리뷰 작성 및 평점 부여.
- **CRUD 제공**: 사용자가 작성한 리뷰의 수정 및 삭제 기능.

### 3.4. 커뮤니티 게시판 (Board & Comment)
- **자유 게시판**: 게시글 작성, 태그/제목 검색 기능 등을 포함한 소통 공간.
- **댓글 시스템**: 게시글에 대한 다중 댓글 기능 지원.

## 4. 프로젝트 구조 (Directory Structure)

```text
com.mini
├── config            # 보안(Security), Swagger, JPA 설정 클래스
├── controller        # REST API 엔드포인트 (Board, Member, Review 등)
├── domain            # JPA Entity 클래스 (DB 테이블 매핑)
├── dto               # 데이터 전송 객체 (Request/Response DTO)
├── exception         # 커스텀 예외 처리 및 전역 예외 핸들러
├── persistence       # Spring Data JPA Repository 인터페이스
├── service           # 비즈니스 로직 처리 레이어
└── util              # JWT 유틸리티 등 공통 기능
```

## 5. 주요 구현 특징 및 고찰

- **클라우드 DB 연동**: `application.properties` 설정을 통해 Supabase와 연동하여 별도의 로컬 DB 설치 없이도 데이터 일관성을 유지합니다.
- **확장성 고려**: DTO 레이어를 철저히 분리하여 Entity가 외부로 직접 노출되는 것을 방지하고, 유지보수성을 높였습니다.
- **API 문서화**: Swagger UI(`springdoc-openapi`)를 통합하여 개발 중 API 테스트 및 문서 확인이 용이하도록 설정되었습니다.
- **프론트엔드 연동**: 외부 React 앱(`localhost:3000` 또는 Vercel 배포 주소)과의 CORS 설정을 통해 원활한 통신을 지원합니다.

## 6. 결론
`KDT_MiniProject`는 최신 Spring Boot 환경에서 JPA와 Spring Security를 활용한 탄탄한 백엔드 구조를 갖추고 있습니다. 특히 소셜 로그인과 JWT 인증의 결합, 그리고 외부 클라우드 DB 활용은 실제 배포 환경을 고려한 실무적인 설계로 판단됩니다.
