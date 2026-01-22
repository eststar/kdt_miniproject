# MiniProject

공중 화장실 정보 제공 및 커뮤니티 기능을 갖춘 웹 애플리케이션입니다.

## Features

-   **사용자 인증**: JWT 토큰 기반의 자체 로그인 및 소셜 로그인(OAuth2) 기능
-   **화장실 정보**: 공중 화장실 정보 조회
-   **리뷰**: 화장실에 대한 리뷰 작성, 조회, 수정, 삭제 및 평점 부여
-   **게시판**: 자유로운 주제로 소통할 수 있는 게시판 및 댓글 기능

## Tech Stack

-   **Backend**:
    -   Java 21
    -   Spring Boot 3.5.8
    -   Spring Data JPA
    -   Spring Security
-   **Database**:
    -   PostgreSQL
-   **Authentication**:
    -   JWT (JSON Web Token)
    -   OAuth 2.0
-   **API Documentation**:
    -   SpringDoc (Swagger-UI)
-   **Build Tool**:
    -   Maven

## API Documentation

프로젝트 실행 후, 다음 URL에서 API 문서를 확인할 수 있습니다.
-   [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Getting Started

### Prerequisites

-   Java 21
-   Maven

### Installation & Run

1.  **저장소 복제:**
    ```sh
    git clone https://github.com/your-username/MiniProject.git
    cd MiniProject
    ```

2.  **application.properties 설정:**
    `src/main/resources/application.properties` 파일에 자신의 PostgreSQL 데이터베이스 정보에 맞게 설정을 변경합니다.
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/your-db
    spring.datasource.username=your-username
    spring.datasource.password=your-password
    ```

3.  **애플리케이션 실행:**
    ```sh
    ./mvnw spring-boot:run
    ```
    애플리케이션은 `localhost:8080`에서 실행됩니다.
