# [보너스 연습] 한국관광공사 — 고캠핑 정보

> 보유 API: **한국관광공사_고캠핑 정보 조회서비스_GW**  
> 관광 GW와 거의 같은 패턴 — **검색 + 목록** 연습용

---

## 문제

1. 캠핑장 목록 API 호출
2. 지역/키워드로 검색
3. 캠핑장명, 주소, 전화 등 리스트 출력
4. loading / error

---

## 힌트

- Base URL은 [data.go.kr](https://www.data.go.kr) 문서에서 **GoCamping** 관련 경로 확인
- 파라미터: `serviceKey`, `numOfRows`, `pageNo`, `_type=json`, `keyword` 또는 `areaCode`
- 응답: `response.body.items.item` (1건이면 객체 → 배열 통일)

```javascript
// 관광 GW 연습 코드에서 URL과 필드명만 교체
<td>{item.facltNm}</td>   {/* 캠핑장명 — 문서 필드명 확인 */}
<td>{item.addr1}</td>
```

---

## 완료 기준

- [ ] 목록 조회 성공
- [ ] 검색 버튼 동작
- [ ] loading / error

패턴 익히기용 — **시험 본편은 exam-tourism-gw** 를 우선하세요.
