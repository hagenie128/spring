# JPA 쿼리 메서드 레퍼런스 메뉴얼

> Spring Data JPA — Query Method 완전 정리  
> Spring Boot 3.x | Java 17+ | Hibernate 6.x

---

## 목차

1. [쿼리 메서드 기본 구조](#1-쿼리-메서드-기본-구조)
2. [조건 키워드 전체 목록](#2-조건-키워드-전체-목록)
3. [String 타입](#3-string-타입)
4. [숫자 타입 (int / Integer / Long / double)](#4-숫자-타입)
5. [boolean / Boolean 타입](#5-boolean--boolean-타입)
6. [Enum 타입](#6-enum-타입)
7. [날짜/시간 타입 (LocalDate / LocalDateTime)](#7-날짜시간-타입)
8. [복합 조건 조합 (And / Or)](#8-복합-조건-조합)
9. [정렬 (OrderBy)](#9-정렬-orderby)
10. [결과 제한 (Top / First / Distinct)](#10-결과-제한)
11. [반환 타입 변형](#11-반환-타입-변형)
12. [@Query — JPQL 직접 작성](#12-query--jpql-직접-작성)
13. [실전 예제 — 키오스크 MenuItem](#13-실전-예제--키오스크-menuitem)

---

## 1. 쿼리 메서드 기본 구조

```
[동사] + [By] + [필드명] + [키워드] + [And/Or] + [필드명] + [키워드] ...
```

| 부분 | 설명 | 예시 |
|------|------|------|
| **동사** | find / count / exists / delete | `findBy`, `countBy`, `existsBy`, `deleteBy` |
| **By** | 조건 시작 구분자 | `findBy` |
| **필드명** | Entity의 Java 필드명 (대소문자 구분) | `Name`, `Price`, `Category` |
| **키워드** | 비교 방식 지정 | `Containing`, `Between`, `GreaterThan` |
| **And / Or** | 조건 연결 | `findByNameAndCategory` |

> **⚠️ 주의:** 필드명은 Entity의 **Java 필드명** 기준 (DB 컬럼명 아님)  
> DB: `created_at` → Entity 필드: `createdAt` → 메서드: `findByCreatedAt`

---

## 2. 조건 키워드 전체 목록

| 키워드 | SQL 변환 | 지원 타입 |
|--------|----------|-----------|
| `Is` / `Equals` | `= ?` | 모든 타입 |
| `Not` | `!= ?` | 모든 타입 |
| `IsNull` | `IS NULL` | 모든 타입 |
| `IsNotNull` | `IS NOT NULL` | 모든 타입 |
| `Like` | `LIKE ?` | String |
| `NotLike` | `NOT LIKE ?` | String |
| `StartingWith` | `LIKE '?%'` | String |
| `EndingWith` | `LIKE '%?'` | String |
| `Containing` | `LIKE '%?%'` | String |
| `IgnoreCase` | `LOWER(col) = LOWER(?)` | String |
| `ContainingIgnoreCase` | `LIKE '%?%' (대소문자 무시)` | String |
| `GreaterThan` | `> ?` | 숫자, 날짜 |
| `GreaterThanEqual` | `>= ?` | 숫자, 날짜 |
| `LessThan` | `< ?` | 숫자, 날짜 |
| `LessThanEqual` | `<= ?` | 숫자, 날짜 |
| `Between` | `BETWEEN ? AND ?` | 숫자, 날짜 |
| `In` | `IN (?, ?, ...)` | Collection |
| `NotIn` | `NOT IN (?, ?, ...)` | Collection |
| `True` | `= true` | boolean |
| `False` | `= false` | boolean |
| `Before` | `< ?` | 날짜 |
| `After` | `> ?` | 날짜 |
| `OrderBy...Asc` | `ORDER BY ... ASC` | 모든 타입 |
| `OrderBy...Desc` | `ORDER BY ... DESC` | 모든 타입 |

---

## 3. String 타입

### Entity 예시
```java
@Entity
public class Product {
    private String name;       // 상품명
    private String category;   // 카테고리
    private String description;// 설명
    private String code;       // 상품코드 (대소문자 구분 필요)
}
```

### 3-1. 완전 일치 (Equals / Is)

```java
// WHERE name = ?
Optional<Product> findByName(String name);

// WHERE name = ? (Is 생략 가능, 동일한 동작)
Optional<Product> findByNameIs(String name);

// WHERE name != ?
List<Product> findByNameNot(String name);
```

### 3-2. 포함 검색 (Like / Containing)

```java
// WHERE name LIKE '%?%'
List<Product> findByNameContaining(String keyword);

// WHERE name LIKE '?%'  (앞에서 시작)
List<Product> findByNameStartingWith(String prefix);

// WHERE name LIKE '%?'  (뒤에서 끝)
List<Product> findByNameEndingWith(String suffix);

// WHERE name LIKE ?  (직접 % 포함해서 전달해야 함)
List<Product> findByNameLike(String pattern);
// 사용: findByNameLike("%버거%")
```

### 3-3. 대소문자 무시 (IgnoreCase)

```java
// WHERE LOWER(name) = LOWER(?)
Optional<Product> findByNameIgnoreCase(String name);

// WHERE name LIKE '%?%' (대소문자 무시)
List<Product> findByNameContainingIgnoreCase(String keyword);

// WHERE LOWER(category) = LOWER(?)
List<Product> findByCategoryIgnoreCase(String category);
```

### 3-4. Null 체크

```java
// WHERE description IS NULL
List<Product> findByDescriptionIsNull();

// WHERE description IS NOT NULL
List<Product> findByDescriptionIsNotNull();
```

### 3-5. 목록 포함 (In)

```java
// WHERE category IN ('버거', '음료', '사이드')
List<Product> findByCategoryIn(List<String> categories);

// WHERE category NOT IN (...)
List<Product> findByCategoryNotIn(List<String> categories);
```

**사용 예:**
```java
List<String> cats = List.of("버거", "음료");
List<Product> result = repository.findByCategoryIn(cats);
```

---

## 4. 숫자 타입

### Entity 예시
```java
@Entity
public class Product {
    private Integer price;   // 가격
    private int stock;       // 재고
    private Long viewCount;  // 조회수
    private double rating;   // 평점
}
```

### 4-1. 동등 비교

```java
// WHERE price = ?
List<Product> findByPrice(Integer price);

// WHERE price != ?
List<Product> findByPriceNot(Integer price);
```

### 4-2. 범위 비교

```java
// WHERE price > ?
List<Product> findByPriceGreaterThan(int price);

// WHERE price >= ?
List<Product> findByPriceGreaterThanEqual(int price);

// WHERE price < ?
List<Product> findByPriceLessThan(int price);

// WHERE price <= ?
List<Product> findByPriceLessThanEqual(int price);

// WHERE price BETWEEN ? AND ?
List<Product> findByPriceBetween(int min, int max);
```

### 4-3. 목록 포함

```java
// WHERE price IN (1000, 2000, 3000)
List<Product> findByPriceIn(List<Integer> prices);

// WHERE stock NOT IN (0, -1)
List<Product> findByStockNotIn(List<Integer> values);
```

### 4-4. Null 체크 (Integer / Long — wrapper 타입만 가능)

```java
// WHERE price IS NULL
List<Product> findByPriceIsNull();

// WHERE price IS NOT NULL
List<Product> findByPriceIsNotNull();
```

### 4-5. 집계성 메서드

```java
// SELECT COUNT(*) WHERE category = ?
long countByCategory(String category);

// SELECT COUNT(*) WHERE price <= ?
long countByPriceLessThanEqual(int maxPrice);
```

---

## 5. boolean / Boolean 타입

### Entity 예시
```java
@Entity
public class MenuItem {
    private boolean available; // 판매 여부
    private Boolean featured;  // 추천 여부 (null 가능)
}
```

### 5-1. True / False 키워드

```java
// WHERE available = true
List<MenuItem> findByAvailableTrue();

// WHERE available = false
List<MenuItem> findByAvailableFalse();
```

### 5-2. 파라미터로 전달

```java
// WHERE available = ?
List<MenuItem> findByAvailable(boolean available);

// 사용:
repository.findByAvailable(true);
repository.findByAvailable(false);
```

### 5-3. Null 체크 (Boolean wrapper 타입)

```java
// WHERE featured IS NULL
List<MenuItem> findByFeaturedIsNull();

// WHERE featured IS NOT NULL
List<MenuItem> findByFeaturedIsNotNull();
```

### 5-4. 복합 조건

```java
// WHERE available = true AND category = ?
List<MenuItem> findByAvailableTrueAndCategory(String category);

// WHERE available = false OR featured = true
List<MenuItem> findByAvailableFalseOrFeaturedTrue();
```

---

## 6. Enum 타입

### Entity 예시
```java
public enum OrderStatus {
    PENDING, CONFIRMED, COOKING, READY, DELIVERED, CANCELLED
}

@Entity
public class Order {
    @Enumerated(EnumType.STRING) // DB에 문자열로 저장 (권장)
    private OrderStatus status;

    @Enumerated(EnumType.ORDINAL) // DB에 숫자로 저장 (비권장)
    private OrderStatus statusOrdinal;
}
```

### 6-1. 동등 비교

```java
// WHERE status = 'PENDING'
List<Order> findByStatus(OrderStatus status);

// WHERE status != 'CANCELLED'
List<Order> findByStatusNot(OrderStatus status);
```

### 6-2. 목록 포함

```java
// WHERE status IN ('PENDING', 'CONFIRMED', 'COOKING')
List<Order> findByStatusIn(List<OrderStatus> statuses);

// 사용:
repository.findByStatusIn(List.of(
    OrderStatus.PENDING,
    OrderStatus.CONFIRMED,
    OrderStatus.COOKING
));
```

### 6-3. Null 체크

```java
// WHERE status IS NULL
List<Order> findByStatusIsNull();
```

**사용 예:**
```java
// 주방에서 처리해야 할 주문 조회
List<Order> activeOrders = repository.findByStatusIn(
    List.of(OrderStatus.CONFIRMED, OrderStatus.COOKING)
);
```

---

## 7. 날짜/시간 타입

### Entity 예시
```java
@Entity
public class Order {
    private LocalDate orderDate;      // 주문 날짜 (날짜만)
    private LocalDateTime createdAt;  // 생성 시각 (날짜+시간)
    private LocalDateTime updatedAt;  // 수정 시각
}
```

### 7-1. 동등 비교

```java
// WHERE order_date = ?
List<Order> findByOrderDate(LocalDate date);

// WHERE created_at = ?
List<Order> findByCreatedAt(LocalDateTime dateTime);
```

### 7-2. Before / After (날짜 전후)

```java
// WHERE order_date < ?  (해당 날짜 이전)
List<Order> findByOrderDateBefore(LocalDate date);

// WHERE order_date > ?  (해당 날짜 이후)
List<Order> findByOrderDateAfter(LocalDate date);

// WHERE created_at < ?
List<Order> findByCreatedAtBefore(LocalDateTime dateTime);

// WHERE created_at > ?
List<Order> findByCreatedAtAfter(LocalDateTime dateTime);
```

### 7-3. Between (날짜 범위)

```java
// WHERE order_date BETWEEN ? AND ?
List<Order> findByOrderDateBetween(LocalDate start, LocalDate end);

// WHERE created_at BETWEEN ? AND ?
List<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
```

**사용 예:**
```java
// 오늘 주문 조회
LocalDate today = LocalDate.now();
List<Order> todayOrders = repository.findByOrderDateBetween(
    today.atStartOfDay().toLocalDate(),
    today
);

// 이번 달 주문 조회
LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
LocalDateTime now = LocalDateTime.now();
List<Order> thisMonthOrders = repository.findByCreatedAtBetween(startOfMonth, now);
```

### 7-4. GreaterThan / LessThan (날짜 비교)

```java
// WHERE created_at > ?  (After와 동일 효과)
List<Order> findByCreatedAtGreaterThan(LocalDateTime dateTime);

// WHERE created_at >= ?
List<Order> findByCreatedAtGreaterThanEqual(LocalDateTime dateTime);

// WHERE created_at < ?
List<Order> findByCreatedAtLessThan(LocalDateTime dateTime);

// WHERE created_at <= ?
List<Order> findByCreatedAtLessThanEqual(LocalDateTime dateTime);
```

### 7-5. Null 체크

```java
// WHERE updated_at IS NULL  (수정된 적 없는 항목)
List<Order> findByUpdatedAtIsNull();

// WHERE updated_at IS NOT NULL
List<Order> findByUpdatedAtIsNotNull();
```

---

## 8. 복합 조건 조합

### 8-1. And — 모든 조건 충족

```java
// WHERE category = ? AND available = ?
List<MenuItem> findByCategoryAndAvailable(String category, boolean available);

// WHERE price >= ? AND price <= ? AND available = true
List<MenuItem> findByPriceGreaterThanEqualAndPriceLessThanEqualAndAvailableTrue(
    int minPrice, int maxPrice
);

// WHERE name LIKE '%?%' AND category = ?
List<MenuItem> findByNameContainingAndCategory(String keyword, String category);
```

### 8-2. Or — 하나 이상 충족

```java
// WHERE name LIKE '%?%' OR description LIKE '%?%'
List<MenuItem> findByNameContainingOrDescriptionContaining(
    String nameKeyword, String descKeyword
);

// WHERE category = ? OR category = ?  (보통 In이 더 적합)
List<MenuItem> findByCategoryOrCategory(String cat1, String cat2);

// WHERE available = false OR stock = 0
List<MenuItem> findByAvailableFalseOrStockIs(int stock);
```

### 8-3. And + Or 혼합 주의사항

> ⚠️ And와 Or를 섞으면 예상과 다른 우선순위가 적용될 수 있다.  
> 복잡한 조건은 `@Query` JPQL을 사용하는 것이 명확하다.

```java
// 이 메서드의 실제 SQL:
// WHERE name LIKE '%?%' AND category = ? OR available = true
// 의도: (name LIKE AND category) OR available → 실제론 name AND (category OR available) 가능
// → 복잡한 경우 @Query 사용 권장
List<MenuItem> findByNameContainingAndCategoryOrAvailableTrue(
    String name, String category
);
```

---

## 9. 정렬 (OrderBy)

### 9-1. 단일 정렬

```java
// ORDER BY price ASC
List<MenuItem> findAllByOrderByPriceAsc();

// ORDER BY price DESC
List<MenuItem> findAllByOrderByPriceDesc();

// ORDER BY created_at DESC
List<MenuItem> findAllByOrderByCreatedAtDesc();

// ORDER BY name ASC
List<MenuItem> findAllByOrderByNameAsc();
```

### 9-2. 조건 + 정렬

```java
// WHERE category = ? ORDER BY price ASC
List<MenuItem> findByCategoryOrderByPriceAsc(String category);

// WHERE available = true ORDER BY created_at DESC
List<MenuItem> findByAvailableTrueOrderByCreatedAtDesc();

// WHERE name LIKE '%?%' ORDER BY name ASC
List<MenuItem> findByNameContainingOrderByNameAsc(String keyword);
```

### 9-3. 다중 정렬

```java
// ORDER BY category ASC, price DESC
List<MenuItem> findAllByOrderByCategoryAscPriceDesc();

// WHERE available = true ORDER BY category ASC, price ASC
List<MenuItem> findByAvailableTrueOrderByCategoryAscPriceAsc();
```

### 9-4. Sort 파라미터로 동적 정렬

```java
// 반환 타입 변화 없이 정렬만 동적 변경
List<MenuItem> findByCategory(String category, Sort sort);

// 사용:
Sort sort = Sort.by(Sort.Direction.DESC, "price");
repository.findByCategory("버거", sort);

// 다중 필드 정렬:
Sort sort = Sort.by("category").ascending().and(Sort.by("price").descending());
repository.findByAvailable(true, sort);
```

---

## 10. 결과 제한

### 10-1. Top / First — 상위 N개

```java
// LIMIT 1 — 가장 비싼 메뉴 1개
Optional<MenuItem> findTopByOrderByPriceDesc();

// LIMIT 1 — 가장 최근 등록된 메뉴
Optional<MenuItem> findFirstByOrderByCreatedAtDesc();

// LIMIT 3 — 가격 상위 3개
List<MenuItem> findTop3ByOrderByPriceDesc();

// LIMIT 5 — 카테고리 내 최근 5개
List<MenuItem> findTop5ByCategoryOrderByCreatedAtDesc(String category);

// LIMIT 10 — 판매중인 메뉴 중 저렴한 순 10개
List<MenuItem> findTop10ByAvailableTrueOrderByPriceAsc();
```

### 10-2. Distinct — 중복 제거

```java
// SELECT DISTINCT category FROM menu_item
List<String> findDistinctCategoryBy();

// 이름 중복 없이 검색
List<MenuItem> findDistinctByNameContaining(String keyword);
```

---

## 11. 반환 타입 변형

### 11-1. 단건 조회

```java
// 결과 없으면 null (비권장 — NPE 위험)
MenuItem findByName(String name);

// 결과 없으면 Optional.empty() (권장)
Optional<MenuItem> findByName(String name);

// 결과 없으면 null, 2개 이상이면 예외
@Nullable MenuItem findByCode(String code);
```

### 11-2. 다건 조회

```java
// List — 가장 일반적
List<MenuItem> findByCategory(String category);

// Stream — 대용량 처리 (반드시 try-with-resources 사용)
Stream<MenuItem> findByAvailableTrue();

// Page — 페이징 처리
Page<MenuItem> findByCategory(String category, Pageable pageable);

// Slice — 다음 페이지 존재 여부만 (COUNT 쿼리 없음)
Slice<MenuItem> findByCategory(String category, Pageable pageable);
```

**Page 사용 예:**
```java
// 페이지 0, 페이지당 10개, 가격 내림차순
Pageable pageable = PageRequest.of(0, 10, Sort.by("price").descending());
Page<MenuItem> page = repository.findByCategory("버거", pageable);

page.getContent();        // 현재 페이지 데이터 List
page.getTotalElements();  // 전체 데이터 수
page.getTotalPages();     // 전체 페이지 수
page.getNumber();         // 현재 페이지 번호
page.hasNext();           // 다음 페이지 존재 여부
```

### 11-3. 집계

```java
// SELECT COUNT(*) WHERE available = true
long countByAvailableTrue();

// SELECT COUNT(*) WHERE category = ?
long countByCategory(String category);

// SELECT EXISTS WHERE name = ?
boolean existsByName(String name);

// SELECT EXISTS WHERE id = ? AND available = true
boolean existsByIdAndAvailableTrue(Long id);
```

### 11-4. 삭제 후 반환

```java
// DELETE WHERE available = false, 삭제된 수 반환
long deleteByAvailableFalse();

// DELETE WHERE category = ?, 삭제된 엔티티 목록 반환
List<MenuItem> deleteByCategory(String category);
```

---

## 12. @Query — JPQL 직접 작성

쿼리 메서드로 표현하기 어려운 복잡한 조건은 `@Query`로 JPQL을 직접 작성한다.

> **JPQL vs SQL 차이:**  
> - 테이블명 → **엔티티 클래스명** (MenuItem)  
> - 컬럼명 → **Java 필드명** (createdAt, not created_at)  
> - alias는 관례적으로 클래스명 첫 글자 소문자 (m)

### 12-1. 기본 JPQL

```java
// 전체 조회 — 등록일 내림차순
@Query("SELECT m FROM MenuItem m ORDER BY m.createdAt DESC")
List<MenuItem> findAllSortedByCreatedAt();

// 특정 필드만 조회 (DTO Projection)
@Query("SELECT m.name, m.price FROM MenuItem m WHERE m.available = true")
List<Object[]> findNameAndPriceOfAvailable();
```

### 12-2. 파라미터 바인딩

```java
// 위치 기반 바인딩 (?1, ?2)
@Query("SELECT m FROM MenuItem m WHERE m.category = ?1 AND m.price <= ?2")
List<MenuItem> findByCategoryAndMaxPrice(String category, int maxPrice);

// 이름 기반 바인딩 (:name) — 권장
@Query("SELECT m FROM MenuItem m WHERE m.name LIKE %:keyword% OR m.description LIKE %:keyword%")
List<MenuItem> searchByKeyword(@Param("keyword") String keyword);
```

### 12-3. 조건 분기 (null 처리)

```java
// :category가 null이면 category 조건 무시
@Query("SELECT m FROM MenuItem m " +
       "WHERE (:category IS NULL OR m.category = :category) " +
       "AND (:available IS NULL OR m.available = :available) " +
       "ORDER BY m.createdAt DESC")
List<MenuItem> search(@Param("category") String category,
                      @Param("available") Boolean available);
```

### 12-4. 집계 JPQL

```java
// 카테고리별 평균 가격
@Query("SELECT m.category, AVG(m.price) FROM MenuItem m GROUP BY m.category")
List<Object[]> findAvgPriceByCategory();

// 카테고리별 메뉴 수
@Query("SELECT m.category, COUNT(m) FROM MenuItem m GROUP BY m.category ORDER BY COUNT(m) DESC")
List<Object[]> findCountByCategory();
```

### 12-5. 수정 쿼리 (@Modifying)

```java
// UPDATE — 반드시 @Modifying + @Transactional 함께 선언
@Modifying
@Transactional
@Query("UPDATE MenuItem m SET m.available = false WHERE m.stock = 0")
int markUnavailableWhenOutOfStock();

// DELETE
@Modifying
@Transactional
@Query("DELETE FROM MenuItem m WHERE m.available = false AND m.createdAt < :cutoff")
int deleteOldUnavailableMenus(@Param("cutoff") LocalDateTime cutoff);
```

### 12-6. 네이티브 쿼리 (nativeQuery = true)

```java
// 실제 SQL 사용 — DB 종속적이므로 주의
@Query(value = "SELECT * FROM menu_item WHERE price <= :maxPrice ORDER BY RAND() LIMIT :limit",
       nativeQuery = true)
List<MenuItem> findRandomCheapMenus(@Param("maxPrice") int maxPrice,
                                    @Param("limit") int limit);
```

---

## 13. 실전 예제 — 키오스크 MenuItem

### Entity 구조

```java
@Entity
@Table(name = "menu_item")
public class MenuItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // 메뉴명
    private Integer price;      // 가격
    private String category;    // 카테고리 (버거/음료/사이드)
    private String description; // 설명
    private boolean available;  // 판매 여부

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
```

### 실전 Repository

```java
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    // ── 목록 조회 ─────────────────────────────────────────────

    // 전체 목록 (최신순)
    List<MenuItem> findAllByOrderByCreatedAtDesc();

    // 전체 목록 (가격 오름차순)
    List<MenuItem> findAllByOrderByPriceAsc();

    // ── 카테고리 ─────────────────────────────────────────────

    // 카테고리로 조회
    List<MenuItem> findByCategory(String category);

    // 카테고리로 조회 (가격 오름차순)
    List<MenuItem> findByCategoryOrderByPriceAsc(String category);

    // 여러 카테고리 한번에 조회
    List<MenuItem> findByCategoryIn(List<String> categories);

    // ── 판매 여부 ─────────────────────────────────────────────

    // 판매 중인 메뉴만
    List<MenuItem> findByAvailableTrue();

    // 품절 메뉴만
    List<MenuItem> findByAvailableFalse();

    // 카테고리 + 판매중
    List<MenuItem> findByCategoryAndAvailableTrue(String category);

    // ── 검색 ─────────────────────────────────────────────────

    // 메뉴명 포함 검색
    List<MenuItem> findByNameContaining(String keyword);

    // 메뉴명 포함 검색 (대소문자 무시)
    List<MenuItem> findByNameContainingIgnoreCase(String keyword);

    // ── 가격 ─────────────────────────────────────────────────

    // 가격 범위 검색
    List<MenuItem> findByPriceBetween(int min, int max);

    // 특정 가격 이하 (저렴한순)
    List<MenuItem> findByPriceLessThanEqualOrderByPriceAsc(int maxPrice);

    // ── 날짜 ─────────────────────────────────────────────────

    // 특정 날짜 이후 등록된 메뉴
    List<MenuItem> findByCreatedAtAfter(LocalDateTime dateTime);

    // 이번 달 등록된 메뉴
    List<MenuItem> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    // ── 집계 ─────────────────────────────────────────────────

    // 전체 메뉴 수
    // count() — JpaRepository 기본 제공

    // 카테고리별 메뉴 수
    long countByCategory(String category);

    // 판매 중인 메뉴 수
    long countByAvailableTrue();

    // 이름 중복 체크
    boolean existsByName(String name);

    // ── 결과 제한 ─────────────────────────────────────────────

    // 가장 최근 등록된 메뉴 5개
    List<MenuItem> findTop5ByOrderByCreatedAtDesc();

    // 카테고리별 가장 저렴한 메뉴 1개
    Optional<MenuItem> findTopByCategoryOrderByPriceAsc(String category);

    // ── @Query — 복합 검색 ────────────────────────────────────

    // 키워드로 이름 또는 설명 검색
    @Query("SELECT m FROM MenuItem m " +
           "WHERE m.name LIKE %:keyword% OR m.description LIKE %:keyword% " +
           "ORDER BY m.createdAt DESC")
    List<MenuItem> searchByKeyword(@Param("keyword") String keyword);

    // 복합 필터 (null이면 해당 조건 무시)
    @Query("SELECT m FROM MenuItem m " +
           "WHERE (:keyword IS NULL OR m.name LIKE %:keyword%) " +
           "AND (:category IS NULL OR m.category = :category) " +
           "AND (:available IS NULL OR m.available = :available) " +
           "ORDER BY m.createdAt DESC")
    List<MenuItem> search(@Param("keyword") String keyword,
                          @Param("category") String category,
                          @Param("available") Boolean available);

    // 가격 범위 + 카테고리 + 판매여부
    @Query("SELECT m FROM MenuItem m " +
           "WHERE m.price BETWEEN :minPrice AND :maxPrice " +
           "AND (:category IS NULL OR m.category = :category) " +
           "AND m.available = true " +
           "ORDER BY m.price ASC")
    List<MenuItem> findAvailableInPriceRange(@Param("minPrice") int minPrice,
                                             @Param("maxPrice") int maxPrice,
                                             @Param("category") String category);
}
```

---

## 빠른 참조표

| 목적 | 메서드 패턴 | 예시 |
|------|-----------|------|
| 전체 조회 | `findAll()` | 기본 제공 |
| ID 조회 | `findById(id)` | 기본 제공 |
| 필드 일치 | `findBy필드명` | `findByCategory` |
| 포함 검색 | `findBy필드명Containing` | `findByNameContaining` |
| 시작 검색 | `findBy필드명StartingWith` | `findByNameStartingWith` |
| 범위 검색 | `findBy필드명Between` | `findByPriceBetween` |
| 초과/미만 | `findBy필드명GreaterThan` | `findByPriceGreaterThan` |
| 이상/이하 | `findBy필드명GreaterThanEqual` | `findByPriceGreaterThanEqual` |
| 날짜 이전 | `findBy필드명Before` | `findByCreatedAtBefore` |
| 날짜 이후 | `findBy필드명After` | `findByCreatedAtAfter` |
| true 조건 | `findBy필드명True` | `findByAvailableTrue` |
| false 조건 | `findBy필드명False` | `findByAvailableFalse` |
| null 체크 | `findBy필드명IsNull` | `findByDescriptionIsNull` |
| 목록 포함 | `findBy필드명In` | `findByCategoryIn` |
| 정렬 | `OrderBy필드명Asc/Desc` | `findAllByOrderByPriceDesc` |
| 상위 N개 | `findTopN` / `findFirstN` | `findTop5ByOrderByPriceAsc` |
| 개수 집계 | `countBy필드명` | `countByCategory` |
| 존재 여부 | `existsBy필드명` | `existsByName` |
| 삭제 | `deleteBy필드명` | `deleteByAvailableFalse` |
| 복잡한 조건 | `@Query` JPQL | 위 예시 참고 |
