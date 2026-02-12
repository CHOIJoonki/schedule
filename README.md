# spring-schedule

일정 관리 애플리케이션 - Spring Boot + JPA

---

## API 명세서

### 일정 API

| 기능 | Method | URL | Request | Response | 상태코드 |
|------|--------|-----|---------|----------|----------|
| 일정 생성 | POST | /schedules | Body | 생성된 일정 정보 | 201 Created |
| 전체 일정 조회 (페이징) | GET | /schedules | Query | 일정 페이지 | 200 OK |
| 선택 일정 조회 | GET | /schedules/{id} | Path | 일정 정보 + 댓글 | 200 OK |
| 일정 수정 | PUT | /schedules/{id} | Path, Body | 수정된 일정 정보 | 200 OK |
| 일정 삭제 | DELETE | /schedules/{id} | Path | - | 200 OK |

### 인증 API

| 기능 | Method | URL | Request | Response | 상태코드 |
|------|--------|-----|---------|----------|----------|
| 로그인 | POST | /login | Body | - | 200 OK |
| 로그아웃 | POST | /logout | - | - | 200 OK |

### 유저 API

| 기능 | Method | URL | Request | Response | 상태코드 |
|------|--------|-----|---------|----------|----------|
| 유저 생성 | POST | /users | Body | 생성된 유저 정보 | 201 Created |
| 전체 유저 조회 | GET | /users | - | 유저 목록 | 200 OK |
| 선택 유저 조회 | GET | /users/{id} | Path | 유저 정보 | 200 OK |
| 유저 수정 | PUT | /users/{id} | Path, Body | 수정된 유저 정보 | 200 OK |
| 유저 삭제 | DELETE | /users/{id} | Path | - | 200 OK |

### 댓글 API

| 기능 | Method | URL | Request | Response | 상태코드 |
|------|--------|-----|---------|----------|----------|
| 댓글 생성 | POST | /comments | Body | 생성된 댓글 정보 | 201 Created |
| 댓글 조회 | GET | /comments | Query | 댓글 목록 | 200 OK |

---

### 1. 일정 생성 (로그인 필요)

**Request**
- 세션에서 로그인한 유저 정보를 자동으로 가져옵니다.

```json
{
  "title": "일정 제목",
  "content": "일정 내용"
}
```

**Response**
```json
{
  "scheduleId": 1,
  "title": "일정 제목",
  "content": "일정 내용",
  "username": "작성자명",
  "createdAt": "2025-02-07T10:00:00",
  "updatedAt": "2025-02-07T10:00:00"
}
```

---

### 2. 전체 일정 조회 (페이징)

**Request**
- Query Parameter:
    - `page` - 페이지 번호 (기본값: 0)
    - `size` - 페이지 크기 (기본값: 10)

```
GET /schedules
GET /schedules?page=0&size=10
```

**Response**
```json
{
  "content": [
    {
      "title": "일정 제목",
      "content": "일정 내용",
      "commentCount": 3,
      "username": "작성자명",
      "createdAt": "2025-02-10T13:55:00",
      "updatedAt": "2025-02-10T13:55:00"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 10,
  "number": 0
}
```

---

### 3. 선택 일정 조회

**Request**
```
GET /schedules/{id}
```

**Response**
```json
{
  "scheduleId": 1,
  "title": "일정 제목",
  "content": "일정 내용",
  "username": "작성자명",
  "createdAt": "2025-02-07T10:00:00",
  "updatedAt": "2025-02-07T10:00:00",
  "comments": [
    {
      "commentId": 1,
      "content": "댓글 내용",
      "author": "작성자명",
      "scheduleId": 1,
      "createdAt": "2025-02-07T10:00:00",
      "updatedAt": "2025-02-07T10:00:00"
    }
  ]
}
```

---

### 4. 일정 수정 (로그인 필요, 본인만 가능)

**Request**
- 세션으로 본인 확인 후 수정합니다.

```json
{
  "title": "수정된 제목",
  "content": "일정 내용"
}
```

**Response**
```json
{
  "scheduleId": 1,
  "title": "수정된 제목",
  "content": "일정 내용",
  "username": "작성자명",
  "createdAt": "2025-02-07T10:00:00",
  "updatedAt": "2025-02-07T11:00:00"
}
```

---

### 5. 일정 삭제 (로그인 필요, 본인만 가능)

**Request**
```
DELETE /schedules/{id}
```
- 세션으로 본인 확인 후 삭제합니다.

**Response**
- 상태코드: 200 OK

---

### 6. 로그인

**Request**
```json
{
  "email": "user@example.com",
  "password": "12345678"
}
```

**Response**
- 상태코드: 200 OK
- 세션에 유저 정보 저장 (Cookie: JSESSIONID)

---

### 7. 로그아웃

**Request**
```
POST /logout
```

**Response**
- 상태코드: 200 OK
- 세션 무효화

---

### 8. 회원가입 (유저 생성)

**Request**
```json
{
  "username": "유저명",
  "email": "user@example.com",
  "password": "12345678"
}
```

**Response**
```json
{
  "userId": 1,
  "username": "유저명",
  "email": "user@example.com",
  "createdAt": "2025-02-07T10:00:00",
  "updatedAt": "2025-02-07T10:00:00"
}
```

---

### 9. 전체 유저 조회

**Request**
```
GET /users
```

**Response**
```json
[
  {
    "userId": 1,
    "username": "유저명",
    "email": "user@example.com",
    "createdAt": "2025-02-07T10:00:00",
    "updatedAt": "2025-02-07T10:00:00"
  }
]
```

---

### 10. 선택 유저 조회

**Request**
```
GET /users/{id}
```

**Response**
```json
{
  "userId": 1,
  "username": "유저명",
  "email": "user@example.com",
  "createdAt": "2025-02-07T10:00:00",
  "updatedAt": "2025-02-07T10:00:00"
}
```

---

### 11. 유저 수정

**Request**
```json
{
  "username": "수정된 유저명",
  "email": "updated@example.com"
}
```

**Response**
```json
{
  "userId": 1,
  "username": "수정된 유저명",
  "email": "updated@example.com",
  "createdAt": "2025-02-07T10:00:00",
  "updatedAt": "2025-02-07T11:00:00"
}
```

---

### 12. 유저 삭제

**Request**
```
DELETE /users/{id}
```

**Response**
- 상태코드: 200 OK

---

### 13. 댓글 생성 (로그인 필요)

**Request**
- 세션에서 로그인한 유저 정보를 자동으로 가져옵니다.

```json
{
  "content": "댓글 내용",
  "scheduleId": 1
}
```

**Response**
```json
{
  "commentId": 1,
  "content": "댓글 내용",
  "username": "작성자명",
  "scheduleId": 1,
  "createdAt": "2025-02-09T17:41:00",
  "updatedAt": "2025-02-09T17:41:00"
}
```

---

### 14. 댓글 전체 조회

**Request**
```
GET /comments?scheduleId=1
```

**Response**
```json
[
  {
    "commentId": 1,
    "content": "댓글 내용",
    "username": "작성자명",
    "scheduleId": 1,
    "createdAt": "2025-02-09T17:41:00",
    "updatedAt": "2025-02-09T17:41:00"
  }
]
```

---

## ERD

```
+------------------+          +------------------+          +------------------+
|      User        |          |     Schedule     |          |     Comment      |
+------------------+          +------------------+          +------------------+
| PK  user_id      |    1:N   | PK  schedule_id  |    1:N   | PK  comment_id   |
|     username     | -------> |     title        | -------> |     content      |
|     email        |    |     |     content      |          | FK  user_id      |
|     password     |    |     | FK  user_id      |          | FK  schedule_id  |
|     created_at   |    |     |     created_at   |          |     created_at   |
|     updated_at   |    |     |     updated_at   |          |     updated_at   |
+------------------+    |     +------------------+          +------------------+
                        |                                          |
                        +------------------------------------------+
                                          1:N
                              +------------------+          +------------------+
```

### User 테이블

| 컬럼명 | 데이터 타입 | 제약조건 | 설명 |
|--------|-------------|----------|------|
| user_id | BIGINT | PK, AUTO_INCREMENT | 유저 고유 식별자 |
| username | VARCHAR(50) | NOT NULL | 유저명 |
| email | VARCHAR(255) | NOT NULL, UNIQUE | 이메일 |
| password | VARCHAR(255) | NOT NULL | 비밀번호 |
| created_at | DATETIME | NOT NULL | 작성일 |
| updated_at | DATETIME | NOT NULL | 수정일 |

### Schedule 테이블

| 컬럼명 | 데이터 타입 | 제약조건 | 설명 |
|--------|-------------|----------|------|
| schedule_id | BIGINT | PK, AUTO_INCREMENT | 일정 고유 식별자 |
| title | VARCHAR(30) | NOT NULL | 일정 제목 |
| content | VARCHAR(200) | NOT NULL | 일정 내용 |
| user_id | BIGINT | FK, NOT NULL | 작성 유저 ID |
| created_at | DATETIME | NOT NULL | 작성일 |
| updated_at | DATETIME | NOT NULL | 수정일 |

### Comment 테이블

| 컬럼명 | 데이터 타입 | 제약조건 | 설명 |
|--------|-------------|----------|------|
| comment_id | BIGINT | PK, AUTO_INCREMENT | 댓글 고유 식별자 |
| content | VARCHAR(100) | NOT NULL | 댓글 내용 |
| user_id | BIGINT | FK, NOT NULL | 작성 유저 ID |
| schedule_id | BIGINT | FK, NOT NULL | 일정 ID |
| created_at | DATETIME | NOT NULL | 작성일 |
| updated_at | DATETIME | NOT NULL | 수정일 |