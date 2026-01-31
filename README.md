# spring-schedule

---

## API 명세서

### 일정 API

| 기능 | Method | URL | Request | Response | 상태코드 |
|------|--------|-----|---------|----------|----------|
| 일정 생성 | POST | /schedules | Body | 생성된 일정 정보 | 201 Created |
| 전체 일정 조회 | GET | /schedules | Query (선택) | 일정 목록 | 200 OK |
| 선택 일정 조회 | GET | /schedules/{id} | Path | 일정 정보 | 200 OK |
| 일정 수정 | PUT | /schedules/{id} | Path, Body | 수정된 일정 정보 | 200 OK |
| 일정 삭제 | DELETE | /schedules/{id} | Path, Body | - | 200 OK |

---

### 1. 일정 생성

**Request**
```json
{
  "title": "일정 제목",
  "content": "일정 내용",
  "author": "작성자명",
  "password": "비밀번호"
}
```

**Response**
```json
{
  "id": 1,
  "title": "일정 제목",
  "content": "일정 내용",
  "author": "작성자명",
  "createdAt": "2025-01-31T10:00:00",
  "updatedAt": "2025-01-31T10:00:00"
}
```

---

### 2. 전체 일정 조회

**Request**
- Query Parameter (선택): `author` - 작성자명으로 필터링

```
GET /schedules
GET /schedules?author=작성자명
```

**Response**
```json
[
  {
    "id": 1,
    "title": "일정 제목",
    "content": "일정 내용",
    "author": "작성자명",
    "createdAt": "2025-01-31T10:00:00",
    "updatedAt": "2025-01-31T10:00:00"
  }
]
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
  "id": 1,
  "title": "일정 제목",
  "content": "일정 내용",
  "author": "작성자명",
  "createdAt": "2025-01-31T10:00:00",
  "updatedAt": "2025-01-31T10:00:00"
}
```

---

### 4. 일정 수정

**Request**
```json
{
  "title": "수정된 제목",
  "author": "수정된 작성자명",
  "password": "비밀번호"
}
```

**Response**
```json
{
  "id": 1,
  "title": "수정된 제목",
  "content": "일정 내용",
  "author": "수정된 작성자명",
  "createdAt": "2025-01-31T10:00:00",
  "updatedAt": "2025-01-31T11:00:00"
}
```

---

### 5. 일정 삭제

**Request**
```json
{
  "password": "비밀번호"
}
```

**Response**
- 상태코드: 200 OK

---

## ERD

<img width="528" height="279" alt="스크린샷 2026-01-31 오후 12 59 14" src="https://github.com/user-attachments/assets/8b11e654-4a9f-496f-b9db-30cc1b635d26" />

### 테이블 설명

| 컬럼명 | 데이터 타입 | 제약조건 | 설명 |
|--------|-------------|----------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 일정 고유 식별자 |
| title | VARCHAR(30) | NOT NULL | 일정 제목 |
| content | VARCHAR(200) | NOT NULL | 일정 내용 |
| author | VARCHAR(50) | NOT NULL | 작성자명 |
| password | VARCHAR(100) | NOT NULL | 비밀번호 |
| created_at | DATETIME | NOT NULL | 작성일 |
| updated_at | DATETIME | NOT NULL | 수정일 |
