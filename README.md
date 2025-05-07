# [Spring 6기] 플러스 주차 과제 (걷기반)

# 테이블

- **Schedules (일정) 테이블**
    - scheduleId: 일정 ID (PK)
    - title: 제목
    - content: 내용
    - writerId: 작성자 ID
    - createdAt: 작성 시간
    - updatedAt: 수정 시간

- **Comments (댓글) 테이블**
    - commentId: 댓글 ID (PK)
    - scheduleId: 일정 ID (FK)
    - parentCommentID: 부모 댓글 ID (자기참조, NULL 허용)
    - content: 내용
    - writerId: 작성자 ID
    - createdAt: 작성 시간
    - updatedAt: 수정 시간
    

---

# ERD 작성하기.

![image](https://github.com/user-attachments/assets/b01d8a65-105a-499b-a38e-016a673ff3cf)


---

# API 명세서 작성하기.

## 일정
![image](https://github.com/user-attachments/assets/acf47ec5-f445-442e-9b68-0fad40971242)

## 댓글
![image](https://github.com/user-attachments/assets/7459766d-4b0d-44a8-8a7a-1f11655977fa)

---

# Postman 작성(링크)

https://documenter.getpostman.com/view/43244231/2sB2j7dV4P

---
