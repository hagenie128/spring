# [실기 연습] 한국관광공사 — 지역기반 관광정보 조회

> **시험 주제와 동일한 유형**  
> API: [한국관광공사_국문 관광정보 서비스_GW](https://www.data.go.kr/data/15101578/openapi.do)

---

## 문제

React + Axios로 **관광지(또는 상가) 목록**을 검색·조회하는 페이지를 만드세요.

### 요구사항

1. **API 연동** — 발급받은 Service Key로 Axios GET 요청
2. **검색** — 검색어 입력 후 버튼 클릭 시 해당 조건으로 API 재호출 (`params` 처리)
3. **목록 출력** — JSON을 가공해 테이블(또는 리스트)로 표시
4. **상태 처리** — 로딩 중 `Loading...`, 에러 시 메시지, 결과 없음 안내

---

## API 힌트 (문서에서 정확한 URL 확인!)

관광공사 GW는 보통 아래 형태입니다. **시험 문제지 URL을 최우선**으로 따르세요.

```
GET https://apis.data.go.kr/B551011/KorService2/areaBasedList2
```

자주 쓰는 파라미터:

| 파라미터 | 설명 | 예시 |
|----------|------|------|
| serviceKey | 인증키 | `.env`에서 |
| numOfRows | 한 페이지 건수 | 10 |
| pageNo | 페이지 | 1 |
| MobileOS | OS | ETC |
| MobileApp | 앱명 | AppTest |
| _type | 응답형식 | json |
| areaCode | 지역코드 | 1 (서울) |
| sigunguCode | 시군구 | 선택 |
| keyword | 검색어 | 문제지 확인 |

---

## 구현 순서

### 1. `.env`

```
REACT_APP_TOUR_API_KEY=여기에_키
```

### 2. `src/api/tourApi.js`

```javascript
import axios from "axios";

const API_KEY = process.env.REACT_APP_TOUR_API_KEY;

// 문제지 URL로 바꿔도 됨
const BASE_URL = "https://apis.data.go.kr/B551011/KorService2/areaBasedList2";

export const fetchTourList = (params = {}) =>
  axios.get(BASE_URL, {
    params: {
      serviceKey: API_KEY,
      MobileOS: "ETC",
      MobileApp: "AppTest",
      _type: "json",
      numOfRows: 10,
      pageNo: 1,
      ...params,
    },
  });
```

### 3. 응답 파싱 함수

```javascript
export const parseTourItems = (data) => {
  const header = data?.response?.header;
  if (header?.resultCode !== "0000") {
    throw new Error(header?.resultMsg || "API 오류");
  }

  let items = data?.response?.body?.items?.item ?? [];
  if (!Array.isArray(items)) items = items ? [items] : [];
  return items;
};
```

### 4. `App.jsx` 핵심

```javascript
const [keyword, setKeyword] = useState("");
const [list, setList] = useState([]);
const [loading, setLoading] = useState(false);
const [error, setError] = useState("");

const loadData = async (search = {}) => {
  setLoading(true);
  setError("");
  try {
    const res = await fetchTourList({
      keyword: search.keyword || undefined,
      areaCode: search.areaCode || 1,
    });
    setList(parseTourItems(res.data));
  } catch (e) {
    setError(e.message || "데이터를 불러오지 못했습니다.");
    setList([]);
  } finally {
    setLoading(false);
  }
};

useEffect(() => { loadData(); }, []);

const handleSearch = () => loadData({ keyword });
```

### 5. 화면 출력

```jsx
{loading && <p>Loading...</p>}
{error && <p style={{ color: "red" }}>{error}</p>}
{!loading && !error && list.length === 0 && <p>검색 결과가 없습니다.</p>}

<table>
  <thead>
    <tr><th>제목</th><th>주소</th><th>전화</th></tr>
  </thead>
  <tbody>
    {list.map(item => (
      <tr key={item.contentid}>
        <td>{item.title}</td>
        <td>{item.addr1}</td>
        <td>{item.tel}</td>
      </tr>
    ))}
  </tbody>
</table>
```

---

## 완료 기준 (자가 채점)

- [ ] 최초 진입 시 목록 1건 이상 표시
- [ ] 검색어 입력 후 버튼 → 다른 결과(또는 필터) 반영
- [ ] 요청 중 `Loading...` 표시
- [ ] 키 오류/CORS 시 에러 메시지
- [ ] Network 탭에서 GET + params 확인

---

## 시험장 팁

| 상황 | 대처 |
|------|------|
| CORS | proxy 또는 문제지 안내 따르기 |
| 키 오류 | 인코딩/디코딩 키 구분, `.env` 재시작 |
| 빈 화면 | `response.data.response.body.items.item` 경로 재확인 |
| 1건만 올 때 | 배열 통일 코드 빠뜨림 |

---

## 템플릿

[../template/tourApi.js](../template/tourApi.js) · [../template/App.jsx](../template/App.jsx)
