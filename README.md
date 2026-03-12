# KDT_MiniProject 🚻

![Static Badge](https://img.shields.io/badge/Spring_Boot-3.5.8-green?style=flat&logo=springboot)
![Static Badge](https://img.shields.io/badge/Java-21-blue?style=flat&logo=openjdk)
![Static Badge](https://img.shields.io/badge/PostgreSQL-Supabase-blue?style=flat&logo=postgresql)

**제주시 공공화장실 정보 제공 및 커뮤니티 플랫폼** 백엔드 애플리케이션입니다. 

공용 화장실의 개방 시간, 장소 시설(기저귀 교환대, 장애인용 여부 등) 상세 데이터를 클라이언트에 제공하며, 이와 연계된 자체 커뮤니티 게시판과 리뷰 엔드포인트를 통해 소통과 평가 기능이 가능하도록 설계되었습니다.

> [!TIP]
> 엔티티 다이어그램(ERD) 및 데이터 계층 관련 아키텍처 등 **자세한 프로젝트 분석 내용**은 [PROJECT_ANALYSIS_REPORT.md](./PROJECT_ANALYSIS_REPORT.md) 문서에서 확인하실 수 있습니다.

---

## 💡 주요 기능 (Features)

### 1. 사용자 관리 및 인증 (Security & Members)
- **자체 / 소셜 로그인**: JWT를 활용한 자체 아이디 로그인 및 **OAuth 2.0 (Google, Naver)** 소셜 로그인 지원
- **보안 및 인가 계층**: `ADMIN` 역할이 `MEMBER` 역할을 포함하는 계층 구조(Role Hierarchy) 지원
- **식별키 통합 및 암호화**: 데이터 무결성을 위해 `Provider_Username` 형태의 고유 식별키 생성 및 비밀번호 `BCrypt` 단방향 해시 적용
- **쿠키 및 세션**: 프론트엔드 연동을 위한 보안 쿠키(SameSite, Secure 설정 등)를 통한 토큰 관리 정책

### 2. 화장실 정보 서비스 (Toilet Info)
- **공공 데이터 연동 분석**: 공용 화장실의 위/경도, 도로명 주소, 개방 시간, 남/여 기저귀 교환 탁자 여부 및 대변기/소변기 수 등의 데이터를 제공 
- **초기 로딩**: `DummyInit` 초기 로더를 통해 외부 JSON 데이터를 데이터베이스에 동기화

### 3. 상호작용 및 커뮤니티 (Reviews & Board)
- **리뷰 (Reviews)**
  - 특정 화장실 식별 코드(`data_cd`)를 타겟으로 하여 **별점(Point)과 사용 후기 텍스트**를 저장 및 수정/삭제(CRUD) 제공
- **자유 게시판 및 댓글 (Board & Comment)**
  - 자유로운 글 작성을 위한 게시판
  - 하나의 게시글에 다수 사용자의 댓글을 허용. 게시글이 삭제되면 종속된 모든 댓글도 즉각 삭제되는 **Cascade (고아 객체 제거)** 전략

---

## 🛠 기술 스택 (Tech Stack)

| 구분 | 기술 목록 |
| --- | --- |
| **Backend** | Java 21, Spring Boot 3.5.8, Spring Data JPA |
| **Database** | PostgreSQL (Supabase 클라우드), HikariCP 커넥션 풀 |
| **Security** | Spring Security, JWT (Auth0), OAuth 2.0 Client |
| **Tools** | Maven, Lombok, Jackson JSON |
| **API Docs** | SpringDoc OpenAPI (Swagger UI) |

---

## 🚀 시작하기 (Getting Started)

### 사전 요구사항 (Prerequisites)
- **Java 21** 이상의 JDK 환경
- **Maven** 패키지 관리자 환경

### 설치 및 실행 과정

1. **저장소 가져오기**
   ```sh
   git clone https://github.com/your-username/MiniProject.git
   cd MiniProject
   ```

2. **클라우드 데이터베이스 설정 정보 주입**
   `src/main/resources/application.properties` 또는 `minioauth2.properties` (외부 파일)에서 자신의 PostgreSQL 데이터베이스 정보와 소셜 로그인 Client Secret을 설정합니다.
   ```properties
   spring.datasource.url=jdbc:postgresql://your-supabase-url.com:5432/postgres
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   ```

3. **애플리케이션 구동**
   ```sh
   ./mvnw spring-boot:run
   ```
   실행 완료 후 애플리케이션 REST API 서버는 기본적으로 `http://localhost:8081` (실행 포트)에서 구동됩니다.

---

## 📝 API 문서 가이드 (Swagger)

서버가 정상적으로 구동된 후 아래의 경로에 진입하여 클라이언트 연결을 위한 API 사양을 테스트하고 살펴볼 수 있습니다.
- [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)
