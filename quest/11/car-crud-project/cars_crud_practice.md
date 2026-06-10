# 🚗 Spring Boot MVC + Thymeleaf + MyBatis CRUD 실습

> **사용 기술**: Spring Boot, Spring MVC, Thymeleaf, MyBatis  
> **사용 테이블**: `cars`

---

## 📋 테이블 정보

```sql
CREATE TABLE cars (
    car_id       INT PRIMARY KEY AUTO_INCREMENT,
    brand        VARCHAR(50),
    model        VARCHAR(100),
    year         INT,
    mileage      INT,
    price        BIGINT,
    registered_at DATETIME
);
```

| 컬럼 | 타입 | 설명 |
|------|------|------|
| car_id | INT | 기본키, 자동증가 |
| brand | VARCHAR | 브랜드명 (예: BMW, 기아, 현대) |
| model | VARCHAR | 모델명 (예: 5시리즈, 쏘렌토) |
| year | INT | 연식 |
| mileage | INT | 주행거리 (km) |
| price | BIGINT | 가격 (원) |
| registered_at | DATETIME | 등록일시 |

---

## 📁 권장 프로젝트 구조

```
src/main/java/com/example/cars/
├── controller/
│   └── CarController.java
├── mapper/
│   └── CarMapper.java
├── model/
│   └── Car.java
└── CarsApplication.java

src/main/resources/
├── mapper/
│   └── CarMapper.xml
├── templates/
│   ├── car/
│   │   ├── list.html
│   │   ├── detail.html
│   │   ├── form.html
│   │   └── edit.html
│   └── fragments/
│       └── layout.html
└── application.properties
```

---

## 문제 1 — 전체 목록 조회 (Read All)

### 요구사항

- URL: `GET /cars`
- `cars` 테이블의 **모든 차량**을 조회하여 화면에 표시한다.
- 출력할 컬럼: `car_id`, `brand`, `model`, `year`, `mileage`, `price`, `registered_at`

### 출력 화면 예시

```
┌────────────────────────────────────────────────┐
│              🚗 중고차 목록                      │
│  [+ 새 차량 등록]                                │
├────┬────────┬──────────┬──────┬────────┬──────┤
│ ID │ 브랜드 │  모델    │ 연식 │주행거리│  가격 │
├────┼────────┼──────────┼──────┼────────┼──────┤
│  1 │Genesis │  G80     │ 2022 │ 15,000 │ 6,500만│
│  2 │  기아  │쏘렌토MQ4 │ 2021 │ 48,000 │ 3,550만│
│  ...                                           │
└────────────────────────────────────────────────┘
```

### 구현 힌트

```java
// CarMapper.java
List<Car> findAll();
```

```xml
<!-- CarMapper.xml -->
<select id="findAll" resultType="Car">
    SELECT * FROM cars ORDER BY car_id DESC
</select>
```

```java
// CarController.java
@GetMapping("/cars")
public String list(Model model) {
    // TODO: mapper로 전체 목록 조회 후 model에 담아 list.html로 이동
}
```

```html
<!-- list.html - Thymeleaf 반복 출력 -->
<tr th:each="car : ${cars}">
    <td th:text="${car.carId}"></td>
    <!-- TODO: 나머지 컬럼도 출력 -->
</tr>
```

---

## 문제 2 — 상세 조회 (Read One)

### 요구사항

- URL: `GET /cars/{id}`
- `car_id`로 특정 차량 1건을 조회하여 상세 페이지를 표시한다.
- 목록 페이지의 각 행에서 상세 페이지로 이동하는 링크를 추가한다.

### 구현 힌트

```java
// CarMapper.java
Car findById(int carId);
```

```xml
<!-- CarMapper.xml -->
<select id="findById" parameterType="int" resultType="Car">
    SELECT * FROM cars WHERE car_id = #{carId}
</select>
```

```java
// CarController.java
@GetMapping("/cars/{id}")
public String detail(@PathVariable int id, Model model) {
    // TODO
}
```

```html
<!-- list.html - 상세 링크 -->
<a th:href="@{/cars/{id}(id=${car.carId})}">상세보기</a>
```

---

## 문제 3 — 차량 등록 (Create)

### 요구사항

- `GET /cars/new` → 등록 폼 페이지 출력
- `POST /cars` → 폼 데이터를 받아 DB에 INSERT 후 목록 페이지로 리다이렉트
- `registered_at`은 현재 시각(`LocalDateTime.now()`)으로 자동 설정한다.

### 구현 힌트

```java
// CarMapper.java
void insert(Car car);
```

```xml
<!-- CarMapper.xml -->
<insert id="insert" parameterType="Car" useGeneratedKeys="true" keyProperty="carId">
    INSERT INTO cars (brand, model, year, mileage, price, registered_at)
    VALUES (#{brand}, #{model}, #{year}, #{mileage}, #{price}, #{registeredAt})
</insert>
```

```java
// CarController.java
@GetMapping("/cars/new")
public String newForm(Model model) {
    // TODO: 빈 Car 객체를 model에 담아 form.html로 이동
}

@PostMapping("/cars")
public String insert(Car car) {
    // TODO: registeredAt 설정 후 insert, 목록으로 redirect
}
```

```html
<!-- form.html -->
<form th:action="@{/cars}" th:object="${car}" method="post">
    <input type="text" th:field="*{brand}" placeholder="브랜드"/>
    <!-- TODO: 나머지 입력 필드 추가 -->
    <button type="submit">등록</button>
</form>
```

---

## 문제 4 — 차량 수정 (Update)

### 요구사항

- `GET /cars/{id}/edit` → 기존 데이터가 채워진 수정 폼 출력
- `POST /cars/{id}/edit` → 수정 데이터를 받아 DB에 UPDATE 후 상세 페이지로 리다이렉트
- 수정 가능한 필드: `brand`, `model`, `year`, `mileage`, `price`
- `registered_at`은 **수정하지 않는다.**

### 구현 힌트

```java
// CarMapper.java
void update(Car car);
```

```xml
<!-- CarMapper.xml -->
<update id="update" parameterType="Car">
    UPDATE cars
    SET brand = #{brand},
        model = #{model},
        year  = #{year},
        mileage = #{mileage},
        price = #{price}
    WHERE car_id = #{carId}
</update>
```

```java
// CarController.java
@GetMapping("/cars/{id}/edit")
public String editForm(@PathVariable int id, Model model) {
    // TODO
}

@PostMapping("/cars/{id}/edit")
public String update(@PathVariable int id, Car car) {
    // TODO: car_id 세팅 후 update, 상세 페이지로 redirect
}
```

---

## 문제 5 — 차량 삭제 (Delete)

### 요구사항

- `POST /cars/{id}/delete` → 해당 차량을 DB에서 DELETE 후 목록으로 리다이렉트
- 상세 페이지에 **[삭제] 버튼**을 추가한다.
- 삭제 전 JavaScript `confirm()` 창으로 확인을 받는다.

### 구현 힌트

```java
// CarMapper.java
void delete(int carId);
```

```xml
<!-- CarMapper.xml -->
<delete id="delete" parameterType="int">
    DELETE FROM cars WHERE car_id = #{carId}
</delete>
```

```java
// CarController.java
@PostMapping("/cars/{id}/delete")
public String delete(@PathVariable int id) {
    // TODO
}
```

```html
<!-- detail.html - 삭제 버튼 -->
<form th:action="@{/cars/{id}/delete(id=${car.carId})}" method="post"
      onsubmit="return confirm('정말 삭제하시겠습니까?')">
    <button type="submit">삭제</button>
</form>
```

---

## 🌟 도전 문제

기본 CRUD를 완성했다면 아래 기능도 추가해보세요!

### 도전 1 — 브랜드별 필터 검색

- URL: `GET /cars?brand=BMW`
- 브랜드명으로 필터링된 목록을 출력한다.

```xml
<select id="findByBrand" resultType="Car">
    SELECT * FROM cars
    <where>
        <if test="brand != null and brand != ''">
            brand = #{brand}
        </if>
    </where>
    ORDER BY car_id DESC
</select>
```

### 도전 2 — 가격 범위 검색

- URL: `GET /cars?minPrice=30000000&maxPrice=60000000`
- 최소/최대 가격 범위로 차량을 검색한다.
- MyBatis `<if>` 태그를 활용한다.

### 도전 3 — 페이징 처리

- 목록 페이지에 페이징을 적용한다. (한 페이지 5건)
- URL: `GET /cars?page=1`
- MyBatis에서 `LIMIT`, `OFFSET`을 사용한다.

---

## ✅ 체크리스트

| 번호 | 기능 | URL | 완료 |
|------|------|-----|------|
| 1 | 전체 목록 조회 | GET /cars | ☐ |
| 2 | 상세 조회 | GET /cars/{id} | ☐ |
| 3 | 등록 폼 | GET /cars/new | ☐ |
| 4 | 등록 처리 | POST /cars | ☐ |
| 5 | 수정 폼 | GET /cars/{id}/edit | ☐ |
| 6 | 수정 처리 | POST /cars/{id}/edit | ☐ |
| 7 | 삭제 처리 | POST /cars/{id}/delete | ☐ |
| 도전1 | 브랜드 검색 | GET /cars?brand= | ☐ |
| 도전2 | 가격 범위 검색 | GET /cars?minPrice=&maxPrice= | ☐ |
| 도전3 | 페이징 | GET /cars?page= | ☐ |

---

## 📌 참고 사항

- `application.properties`에 DB 연결 정보와 MyBatis mapper 경로 설정 필요
  ```properties
  mybatis.mapper-locations=classpath:mapper/*.xml
  mybatis.type-aliases-package=com.example.cars.model
  ```
- `Car.java`의 필드명은 **camelCase**, DB 컬럼은 **snake_case** → `mapUnderscoreToCamelCase=true` 설정 권장
  ```properties
  mybatis.configuration.map-underscore-to-camel-case=true
  ```
- PRG 패턴(Post-Redirect-Get)을 반드시 적용하여 중복 등록/수정 방지
