# 🥕 문제 1 — 판매글 피드 + 페이징·검색 (★★★☆)

**실행:** [Problem01Application](src/main/java/com/spring/Problem01Application.java)  
**URL:** http://localhost:8080/listings

| TODO | 열어볼 파일 | 할 일 |
|------|------------|-------|
| 1 | [Listing.java](src/main/java/com/spring/entity/Listing.java) | 필드 JPA 매핑 |
| 2 | [Listing.java](src/main/java/com/spring/entity/Listing.java) | 등록일·기본 판매상태 |
| 3 | [ListingRepository.java](src/main/java/com/spring/repository/ListingRepository.java) | 검색 + Page 쿼리 |
| 4 | [ListingService.java](src/main/java/com/spring/service/ListingService.java) | search · CRUD |
| 5 | [ListingController.java](src/main/java/com/spring/controller/ListingController.java) | 목록 + 필터 |
| 6 | [ListingController.java](src/main/java/com/spring/controller/ListingController.java) | 상세 |
| 7~9 | [ListingController.java](src/main/java/com/spring/controller/ListingController.java) | 등록·수정·삭제 |
| 10~12 | [list.html](src/main/resources/templates/listing/list.html) | **피드형** 목록·페이징 |
| 13~14 | [form.html](src/main/resources/templates/listing/form.html) | 판매 등록 폼 |
| 15 | [detail.html](src/main/resources/templates/listing/detail.html) | 상품 상세 |

**샘플 데이터:** 기동 시 [SampleListingGenerator](src/main/java/com/spring/init/SampleListingGenerator.java)가 **500건** 생성 (이모지 썸네일). 건수는 `application.properties`의 `app.sample-data.count`로 조절.

**완성 기준:** 카테고리·동네·가격대 검색, 페이지 이동, CRUD 전체 동작
