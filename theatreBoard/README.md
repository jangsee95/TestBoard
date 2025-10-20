# theatreBoard

## 📖 프로젝트 소개

`theatreBoard`는 Java Servlet/JSP 기반의 웹 애플리케이션입니다. 이 프로젝트는 사용자 관리, 게시판, 연극 정보 조회 및 관리 기능을 제공하며, MVC(Model-View-Controller) 아키텍처 패턴을 따릅니다.

## ✨ 주요 기능

- **사용자 관리**
  - 회원가입 및 로그인/로그아웃
  - 회원정보 수정 및 비밀번호 변경
  - 회원 탈퇴

- **게시판**
  - 게시글 목록 조회 (페이징 기능 포함)
  - 게시글 상세 조회
  - 게시글 작성, 수정, 삭제
  - **댓글**: 게시글에 대한 댓글 작성, 수정, 삭제 (AJAX 적용)

- **연극 정보**
  - 연극 목록 조회
  - 연극 정보 등록
  - **리뷰**: 연극에 대한 리뷰 작성, 조회, 수정, 삭제 (개발 중)

## 🛠️ 기술 스택

- **Backend**: Java, Jakarta Servlet, JSP, JSTL
- **Database**: PostgreSQL
- **Build Tool**: Gradle
- **Password Hashing**: jBCrypt
- **JSON-lib**: Gson
- **Web Server**: Tomcat

## 📂 프로젝트 구조

```
theatreBoard/
├── src/main/
│   ├── java/
│   │   ├── board/      # 게시판 (Controller, Service, DAO, DTO)
│   │   ├── comment/    # 댓글 (Servlet, Service, DAO, DTO)
│   │   ├── common/     # 공통 모듈 (DBUtil, Error Handling)
│   │   ├── exception/  # 예외 처리
│   │   ├── review/     # 리뷰 (DAO, DTO)
│   │   ├── theatre/    # 연극 정보 (Controller, Service, DAO, DTO)
│   │   └── user/       # 사용자 (Controller, Service, DAO, DTO)
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── lib/
│       │   └── view/   # JSP 뷰 파일
│       └── index.jsp
├── build.gradle        # 프로젝트 의존성 및 빌드 설정
└── README.md
```

## ⚙️ 설치 및 실행 방법

### 1. 데이터베이스 설정

이 프로젝트는 PostgreSQL을 사용합니다. `Scripts/Script.sql` 파일의 DDL을 참고하여 아래 테이블들을 생성해주세요.

**Users Table:**
```sql
CREATE TABLE users (
    user_id VARCHAR(50) PRIMARY KEY,
    user_password VARCHAR(100) NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    user_email VARCHAR(100) UNIQUE NOT NULL,
    user_regdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Boards Table:**
```sql
CREATE TABLE boards (
    board_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    user_id VARCHAR(50) NOT NULL,
    board_regdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_boards_to_users
        FOREIGN KEY (user_id) 
        REFERENCES users(user_id) 
        ON DELETE CASCADE
);
```

**Comments Table:**
```sql
CREATE TABLE comments (
  comment_id SERIAL PRIMARY KEY,
  content TEXT NOT NULL,
  user_id VARCHAR(50) NOT NULL,
  board_id INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  CONSTRAINT fk_comments_to_users
    FOREIGN KEY (user_id) 
    REFERENCES users(user_id)
    ON DELETE CASCADE,
  CONSTRAINT fk_comments_to_boards
    FOREIGN KEY (board_id) 
    REFERENCES boards(board_id)
    ON DELETE CASCADE
);
```

**Theatres Table:**
```sql
CREATE TABLE theatres (
    theatre_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    content TEXT,
    playtime INT,
    poster_url VARCHAR(255),
    performance_datetime TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Reviews Table:**
```sql
CREATE TABLE reviews (
    review_id SERIAL PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    theatre_id INT NOT NULL,
    title text,
    content TEXT,
    rating DECIMAL(2, 1) NOT NULL CHECK (rating >= 0.5 AND rating <= 5.0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reviews_to_users
        FOREIGN KEY (user_id) 
        REFERENCES users(user_id) 
        ON DELETE cascade,
    CONSTRAINT fk_reviews_to_theatres
        FOREIGN KEY (theatre_id) 
        REFERENCES theatres(theatre_id) 
        ON DELETE cascade
);
```

### 2. DB 연결 정보 설정

`src/main/java/common/util/DBUtil.java` 파일은 시스템 **환경변수**에서 DB 연결 정보를 가져옵니다. 프로젝트를 실행하기 전에 아래와 같이 환경변수를 설정해야 합니다.

- `DB_URL`: `jdbc:postgresql://localhost:5432/your_database`
- `DB_USERNAME`: `your_username`
- `DB_PASSWORD`: `your_password`

### 3. 빌드 및 실행

1.  **프로젝트 빌드**:
    프로젝트 루트 디렉토리에서 아래 명령어를 실행하여 `.war` 파일을 빌드합니다.
    ```bash
    ./gradlew build
    ```

2.  **Tomcat 배포**:
    생성된 `build/libs/theatreBoard-0.0.1-SNAPSHOT.war` 파일을 Tomcat의 `webapps` 디렉토리에 배포합니다.

3.  **서버 실행**:
    Tomcat 서버를 시작하고, 웹 브라우저에서 `http://localhost:8080/theatreBoard-0.0.1-SNAPSHOT/` 주소로 접속합니다.

## 🌐 URL 가이드

각 기능은 서블릿의 `act` 파라미터를 통해 호출됩니다.

- **사용자 관련**: `/user?act={action}`
  - `loginForm`, `login`, `joinForm`, `join`, `myPage`, `logout`, `editProfileForm`, `updateUser`, `changePasswordForm`, `changePassword`, `deleteUser`
- **게시판 관련**: `/board?act={action}`
  - `list`, `view`, `writeForm`, `write`, `updateForm`, `update`, `remove`
- **댓글 관련**: `/comment?act={action}`
  - `add`, `remove`, `update`
- **연극 정보 관련**: `/theatre?act={action}`
  - `list`, `writeForm`, `write`