# quest/13 — 🥕 동네 마켓 (수업 전 연습)

> **📖 통합 가이드:** [../PRACTICE-GUIDE-12-17.html](../PRACTICE-GUIDE-12-17.html) — 문제·힌트·전체코드 (클릭해서 열기)

> **다음 주 수업:** `spring_board_jpa.pptx` — 게시판  
> **이 퀘스트:** **당근마켓·중고거래 앱** 느낌 — 글 쓰기 게시판이 아니라 **판매 목록 피드**

## 도메인 매핑

| 게시판 수업 | 동네 마켓 |
|------------|----------|
| Post | Listing (판매글) |
| Member | User (2교시~) |
| Comment | ListingComment (구매 문의 댓글) |
| Attachment | ListingImage (상품 사진) |
| Like | ListingWish (찜 ❤️) |
| URL `/board` | `/listings` |

---

## 문제 목록

| problem | PPT | 난이도 | 주제 |
|---------|-----|--------|------|
| [01](problem01/) | 1교시 | ★★★☆ | 판매글 CRUD + **카테고리·상태·가격·페이징** |
| [02](problem02/) | 2교시 | ★★★★ | 회원·세션·**판매자만 수정**·Quill 상세설명 |
| [03](problem03/) | 3교시 | ★★★★ | **댓글**(구매 문의) + **사진** 업로드 |
| [04](problem04/) | 4교시 | ★★★★★ | **찜 AJAX** 토글 |

**실행:** `gradlew.bat bootRun` → http://localhost:8080/listings

---

## 문제 1

| TODO | 파일 | 할 일 |
|------|------|-------|
| 1 | [Listing.java](problem01/src/main/java/com/spring/entity/Listing.java) | 엔티티 매핑 |
| 2 | [Listing.java](problem01/src/main/java/com/spring/entity/Listing.java) | 등록일·기본 상태 |
| 3 | [ListingRepository.java](problem01/src/main/java/com/spring/repository/ListingRepository.java) | 검색 + Page |
| 4 | [ListingService.java](problem01/src/main/java/com/spring/service/ListingService.java) | search · CRUD |
| 5~9 | [ListingController.java](problem01/src/main/java/com/spring/controller/ListingController.java) | 핸들러 |
| 10~12 | [list.html](problem01/src/main/resources/templates/listing/list.html) | **피드형** 목록 |
| 13~14 | [form.html](problem01/src/main/resources/templates/listing/form.html) | 판매 등록 |
| 15 | [detail.html](problem01/src/main/resources/templates/listing/detail.html) | 상품 상세 |

---

## 문제 2 — 회원·세션

| TODO | 파일 | 할 일 |
|------|------|-------|
| 1~7 | `User` · `AuthController` · `UserService` | 가입·로그인·BCrypt |
| 8~11 | `Listing` · `ListingController` | 판매자 연관·본인만 수정 |
| 12~15 | `auth/` · `form.html` | 로그인·Quill |

---

## 문제 3 — 댓글·사진

| TODO | 파일 | 할 일 |
|------|------|-------|
| 1~4 | `ListingComment` | "아직 있나요?" 댓글 |
| 5~8 | `ListingImage` | 상품 사진 업·다운 |
| 9~11 | `detail.html` | 댓글·갤러리 |

---

## 문제 4 — 찜 AJAX

| TODO | 파일 | 할 일 |
|------|------|-------|
| 1~4 | `ListingWish` · `WishController` | 찜 토글 API |
| 5~7 | `detail.html` | 하트 버튼 fetch |

### ✅ 완료 체크

- [ ] 판매중 → 예약중 → 판매완료 상태 변경
- [ ] 찜 수 실시간 갱신
- [ ] 판매글 삭제 시 사진·댓글 cascade

**진행:** [problem01](problem01/) → 02 → 03 → 04
