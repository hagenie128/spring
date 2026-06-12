# 🥕 문제 2 — 회원·세션·판매자 권한 (★★★★)

**시작:** problem01 완성본을 복사한 뒤 아래 TODO 진행

| TODO | 열어볼 파일 | 할 일 |
|------|------------|-------|
| 1 | `User.java` | 회원 엔티티 (닉네임·비밀번호·동네) |
| 2 | `UserRepository.java` | JPA Repository |
| 3 | `UserService.java` | BCrypt 가입·로그인 검증 |
| 4 | `AuthController.java` | 회원가입·로그인·로그아웃 |
| 5 | `WebMvcConfig.java` | 로그인 인터셉터 (선택) |
| 6 | `Listing.java` | `@ManyToOne User seller` 로 교체 |
| 7 | `ListingController.java` | 세션 회원만 등록·본인만 수정 |
| 8~11 | `templates/auth/` · `form.html` | 로그인·가입 화면 |
| 12~15 | `form.html` · `detail.html` | Quill 상세설명·판매자 표시 |

**완성 기준:** 비로그인 시 등록 불가, 타인 글 수정 차단
