# 8일차 실습 문제

> 실습 예제 코드 참고 경로: `../practice/spring-mvc-rest/`

## 문제 목록

| 문제 | 폴더 | 난이도 | 핵심 주제 |
|------|------|--------|-----------|
| 문제 1 | `problem01/` | ★★☆☆ | `@RestController`, `@GetMapping`, JSON 응답 |
| 문제 2 | `problem02/` | ★★★☆ | `HandlerInterceptor` — 세션 기반 관리자 접근 제어 |
| 문제 3 | `problem03/` | ★★★☆ | 게시판 CRUD — `redirect:`, in-memory 저장소 |
| 문제 4 (도전) | `problem04/` | ★★★★ | `@RestController` + `@RequestBody` 메뉴 API |

---

## 문제 1 — `@RestController`로 주문 API 구현 (★★☆☆)

**수정 파일**: `problem01/src/main/java/com/spring/problem01/OrderApiController.java`

`web.xml`, `dispatcher-context.xml`, `OrderDto.java`는 완성된 상태다.  
`OrderApiController.java`의 TODO를 채워 주문 목록 및 단건 조회 REST API를 완성하라.

**요구사항**:
1. `@RestController` 어노테이션을 추가하라.
2. `@RequestMapping("/api/orders")`를 추가하라.
3. `GET /api/orders` 요청 시 `orders` 목록 전체를 JSON으로 반환하라.
4. `GET /api/orders/{id}` 요청 시 해당 id의 `OrderDto`를 JSON으로 반환하라.  
   (없으면 `null` 반환)

**예상 결과**:
- `GET http://localhost:8080/problem01/api/orders` → 주문 목록 JSON 배열
- `GET http://localhost:8080/problem01/api/orders/1` → `{"id":1,"menuName":"아메리카노","quantity":2,"price":8000}`

---

## 문제 2 — `HandlerInterceptor`로 관리자 접근 제어 (★★★☆)

**수정 파일**: `problem02/src/main/java/com/spring/problem02/AdminCheckInterceptor.java`

로그인 컨트롤러, 대시보드 뷰, `dispatcher-context.xml`은 완성된 상태다.  
`AdminCheckInterceptor.java`의 `preHandle` 메서드를 완성하여  
세션에 `adminUser`가 없으면 `/login`으로 리다이렉트하게 하라.

**요구사항**:
1. `preHandle` 메서드를 오버라이드하라.
2. 세션에서 `"adminUser"` 속성을 꺼낸다.
3. `adminUser`가 `null`이면:
   - `System.out.println("[AdminCheckInterceptor] 미인증 접근 차단")` 출력
   - `response.sendRedirect(request.getContextPath() + "/login")` 호출
   - `return false`
4. `adminUser`가 있으면:
   - `System.out.println("[AdminCheckInterceptor] 인증 확인: " + adminUser)` 출력
   - `return true`

**예상 결과**:
- 비로그인 상태에서 `/dashboard` 접근 → `/login` 리다이렉트
- `admin` / `1234`로 로그인 후 `/dashboard` 접근 → 대시보드 화면 출력

---

## 문제 3 — 게시판 CRUD 완성 (★★★☆)

**수정 파일**: `problem03/src/main/java/com/spring/problem03/PostController.java`

`Post.java`, `PostRepository.java`, JSP 뷰 3개(`list.jsp`, `write.jsp`, `detail.jsp`)는 완성된 상태다.  
`PostController.java`의 TODO를 채워 게시글 목록·작성·상세·삭제를 완성하라.

**요구사항**:
1. `GET /post/list` → `"posts"` model attribute에 전체 게시글 목록 추가, `"post/list"` 반환
2. `GET /post/write` → `"post/write"` 반환
3. `POST /post/write` → `@RequestParam`으로 `title`, `content`, `author` 수신  
   → `postRepository.save(new Post(null, title, content, author))` 저장  
   → `"redirect:/post/list"` 반환
4. `GET /post/detail?id=N` → `"post"` model attribute에 해당 게시글 추가, `"post/detail"` 반환
5. `POST /post/delete?id=N` → `postRepository.deleteById(id)` 후 `"redirect:/post/list"` 반환

**예상 결과**:
- `http://localhost:8080/problem03/post/list` → 게시글 목록 출력
- 작성 폼 제출 → 목록으로 리다이렉트, 새 게시글 추가 확인
- 상세 페이지에서 삭제 → 목록에서 제거 확인

---

## 문제 4 — 도전: 메뉴 관리 REST API 완성 (★★★★)

**수정 파일**: `problem04/src/main/java/com/spring/problem04/MenuApiController.java`

`MenuDto.java`, `ApiLogInterceptor.java`는 완성된 상태다.  
`MenuApiController.java`의 TODO를 채워 메뉴 조회·필터·등록 API를 완성하라.

**요구사항**:
1. `@RestController`, `@RequestMapping("/api/menus")`를 추가하라.
2. `GET /api/menus` → 전체 메뉴 목록(`List<MenuDto>`) 반환
3. `GET /api/menus/available` → `available == true`인 메뉴만 필터링하여 반환
4. `POST /api/menus` → `@RequestBody MenuDto`로 새 메뉴를 받아 리스트에 추가 후 반환  
   (새 id는 `menus.size() + 1L`)

**예상 결과**:
- `GET /api/menus` → 전체 3개 메뉴 JSON 배열
- `GET /api/menus/available` → `available: true`인 2개 메뉴만 반환
- `POST /api/menus` (body: `{"menuName":"바닐라라떼","price":5000,"available":true}`) → 추가된 메뉴 반환, 이후 목록에 4개 확인
