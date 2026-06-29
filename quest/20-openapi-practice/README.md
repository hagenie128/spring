# Quest 20 — 공공 API + React + Axios (실기 시험 대비)

> **내일 과제 2와 같은 패턴**을 연습합니다.  
> JWT 없음! Axios GET + 검색 + 목록 + loading/error 만 집중.

| 연습 | 폴더 | API | 난이도 |
|------|------|-----|--------|
| **본시험 유사** | [exam-tourism-gw](exam-tourism-gw/) | 한국관광공사_국문 관광정보 GW | ★★★ |
| **보너스 1** | [exam-weather](exam-weather/) | 기상청_단기예보 | ★★☆ |
| **보너스 2** | [exam-camping](exam-camping/) | 한국관광공사_고캠핑 | ★★☆ |

> HTML 가이드: [../PRACTICE-GUIDE-EXAM.html](../PRACTICE-GUIDE-EXAM.html)

---

## 시험에서 꼭 나오는 4가지

1. **Axios GET** + `params` (serviceKey 포함)
2. **검색** — input → 버튼 → API 재호출
3. **목록 출력** — `map`으로 table/list
4. **상태 처리** — `loading` / `error` / `empty`

---

## 공통 프로젝트 만들기 (10분)

```powershell
npx create-react-app openapi-lab
cd openapi-lab
npm install axios
```

`.env` (Git에 올리지 않기):

```
REACT_APP_TOUR_API_KEY=발급받은_서비스키
```

`.env.example`만 repo에 두세요.

---

## 공공 API 응답 구조 (외우기)

대부분 data.go.kr API는 이런 형태입니다.

```json
{
  "response": {
    "header": { "resultCode": "0000", "resultMsg": "OK" },
    "body": {
      "items": {
        "item": [ { ... }, { ... } ]
      },
      "numOfRows": 10,
      "pageNo": 1,
      "totalCount": 100
    }
  }
}
```

⚠️ **주의:** 결과가 1건이면 `item`이 배열이 아니라 **객체 1개**로 옵니다.

```javascript
let items = body.items?.item ?? [];
if (!Array.isArray(items)) items = items ? [items] : [];
```

---

## 시험 당일 순서 (90분 기준)

| 시간 | 할 일 |
|------|--------|
| 0~10분 | CRA + axios + `.env` + API 문서에서 URL/파라미터 확인 |
| 10~25분 | Postman 또는 브라우저로 GET 1번 성공 |
| 25~45분 | 검색 input + 버튼 + params 연결 |
| 45~70분 | table/list 출력 |
| 70~85분 | loading / error / empty 분기 |
| 85~90분 | CORS·키 인코딩·빈 검색 예외 확인 |

---

## CORS 에러가 나면?

브라우저에서 공공 API를 직접 호출하면 CORS가 막힐 수 있습니다.

1. 시험 PC에서 되는지 먼저 확인
2. 안 되면 `package.json`에 `"proxy": "https://apis.data.go.kr"` (시험 허용 시)
3. 또는 문제지에 적힌 Base URL 그대로 사용

---

## 연습 체크리스트

- [ ] `.env`에 Service Key 넣고 `process.env.REACT_APP_...`로 읽기
- [ ] `axios.get(url, { params: { serviceKey, ... } })`
- [ ] 검색 버튼 클릭 시 API 재호출
- [ ] `loading` true/false
- [ ] `catch`에서 error 메시지
- [ ] `item` 배열 통일 처리
- [ ] `resultCode !== '0000'` 도 에러 처리

---

## 참고 — 보유 API 목록

| API | 연습 폴더 |
|-----|-----------|
| 한국관광공사_국문 관광정보 GW | exam-tourism-gw |
| 기상청_단기예보 | exam-weather |
| 한국관광공사_고캠핑 | exam-camping |
| 한국천문연구원_천문현상 | (패턴 동일 — URL만 바꿔 연습) |
| 관광공사_반려동물 동반여행 | (패턴 동일) |
| 건강보험심사평가원_병원정보 | (패턴 동일) |

**JWT 백엔드(quest/19)와 섞지 마세요.** 공공 API는 키만 있으면 됩니다.
