# 🍀 **일상스위터**
- 소개 : 하루 일상을 가볍게 적어봐요.

- 프로젝트 기간 : 2024.06.19 ~ 2024.06.25
- Teck Stack :  <img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"> <img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white"/> <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>
- 버전: JDK 17
- 개발 환경: IntelliJ

## 팀 소개 및 역할 분담

1. 팀명 : 4leaf

   팀소개 : 일상 기록을 쉽고 즐겁게 만드는 프로젝트, 일상스위터를 개발하는 팀입니다.

2. 구현기능

   필수 구현
    <details>
       <summary>사용자 인증 및 회원 관리</summary> 
            - 회원가입 기능<br>
            - username, password 조건 검증<br>
            - DB 중복 검사회원 저장<br>
            - 성공 및 예외처리 응답<br>
            - 회원 권한 부여 (ADMIN, USER) 로그인 기능<br>
            - DB에서 사용자 확인<br>
            - password 비교<br>
            - JWT 토큰 발행 및 응답<br>
            - 유효하지 않은 사용자 정보 예외처리<br>
            - 로그아웃 기능<br>
            - 토큰 초기화<br>
            - 초기화된 Refresh Token 재사용 방지
    </details>

      <details>
         <summary>프로필 관리 및 비밀번호 관리</summary> 
            - 비밀번호 수정 기능<br>
            - 현재 비밀번호 확인<br>
            - 새로운 비밀번호 조건 검증 및 비교<br>
            - 최근 3번 사용한 비밀번호 확인<br>
            - 프로필 수정 기능<br>
            - 이름, 한 줄 소개 수정 기능<br>
            - 프로필 정보 조회 기능
    </details>

    <details>
         <summary>게시물 CRUD 기능</summary> 
            - 게시물 작성, 수정, 삭제 기능<br>
            - JWT 토큰 검증 및 인가<br>
            - 작성, 수정, 삭제 기능 구현<br>
            - 예외처리 (작성자가 아닌 다른 사용자가 시도하는 경우)<br>
            - 게시물 조회 기능<br>
            - 전체 게시물 조회<br>
            - 생성일자 기준 최신순 정렬<br>
            - 페이지네이션 (페이지 당 5개씩)
    </details>

    <details>
         <summary>댓글 CRUD 기능</summary> 
            - 댓글 작성, 수정, 삭제 기능<br>
            - JWT 토큰 검증 및 인가<br>
            - 댓글 작성, 수정, 삭제 기능 구현<br>
            - 예외처리 (작성자가 아닌 다른 사용자가 시도하는 경우)<br>
            - 댓글 조회 기능<br>
            - 특정 게시물에 대한 댓글 조회
    </details>

   추가 구현
    <details>
       <summary>백오피스 기능</summary> 
            - 전체 회원 조회<br>
            - 특정 회원 정보 수정<br>
            - 특정 회원 삭제<br>
            - 특정 회원 관리자로 권한 변경<br>
            - 특정 회원 차단<br>
            - 전체 게시글 조회<br>
            - 특정 게시글 정보 수정<br>
            - 특정 게시글 삭제<br>
            - 특정 게시글 상단에 고정<br>
            - 전체 댓글 조회<br>
            - 특정 댓글 정보 수정<br>
            - 특정 댓글 삭제
    </details>

      <details>
         <summary>게시물 및 댓글 좋아요 / 좋아요 취소 기능</summary> 
            - 게시물 및 댓글 좋아요, 좋아요 취소<br>
            - 자신의 게시물과 댓글은 좋아요 불가능<br>
            - 같은 게시물에는 사용자당 한 번만 좋아요
    </details>

    <details>
         <summary>팔로우 기능</summary> 
            - 특정 사용자 팔로우 및 언팔로우<br>
            - 팔로우하는 사용자 게시물 조회<br>
            - 팔로우하는 사용자 게시물 조회 시 최신순
    </details>

    <details>
         <summary>소셜로그인</summary> 
            - 네이버 소셜 로그인 구현 
    </details>

3. 역할분담
   - 최현진 [팀장]
      - 프로필 관리 및 비밀번호 관리, 백오피스
   - 문수정
      - 댓글 CRUD, 좋아요
   - 박태순
      - 사용자 인증 및 회원 관리, 소셜 로그인
   - 조성훈
      - 게시물 CRUD, 팔로우
## API 명세서
![image](https://github.com/HyeonjinChoi/DailySwitter/assets/63872787/fda006a1-bf15-4571-8a3e-f35cc05db21f)
![image](https://github.com/HyeonjinChoi/DailySwitter/assets/63872787/edbd89b3-7a71-4966-94a1-437818c6d801)



## ERD Diagram
![image](https://github.com/HyeonjinChoi/DailySwitter/assets/63872787/27ad7e03-2c97-4f55-a452-3eae1530d2f6)

