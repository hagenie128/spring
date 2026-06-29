# [보너스 연습] 기상청 — 단기예보 조회

> 같은 패턴으로 **다른 공공 API** 연습  
> 보유 API: **기상청_단기예보 조회서비스**

---

## 왜 이걸 연습하나?

시험 URL/파라미터 이름이 바뀌어도 **코드 구조는 동일**합니다.

```
.env 키 → axios.get + params → JSON 파싱 → map 출력 → loading/error
```

관광 API가 CORS로 막히면, 기상청 API로 **패턴만** 먼저 익혀 두세요.

---

## 문제

1. Service Key로 **단기예보** API 호출
2. 지역(또는 격자 좌표) 선택/입력 후 조회
3. 기온·날씨·강수 등을 **리스트/테이블**로 출력
4. loading / error 처리

---

## API 힌트

문서에서 정확한 URL을 확인하세요. 예시 형태:

```
GET https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst
```

자주 쓰는 파라미터:

| 파라미터 | 설명 |
|----------|------|
| serviceKey | 인증키 |
| pageNo | 1 |
| numOfRows | 10 |
| dataType | JSON |
| base_date | YYYYMMDD |
| base_time | HHmm (0200, 0500 등) |
| nx, ny | 격자 X, Y |

서울 시청 근처 예: `nx=60`, `ny=127`

---

## tourApi.js → weatherApi.js 로 바꾸기

```javascript
import axios from "axios";

const API_KEY = process.env.REACT_APP_WEATHER_API_KEY;
const BASE_URL =
  "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";

const today = new Date();
const base_date = today.toISOString().slice(0, 10).replace(/-/g, "");
const base_time = "0500"; // 문서/발표시각 확인

export const fetchWeather = (nx = 60, ny = 127) =>
  axios.get(BASE_URL, {
    params: {
      serviceKey: API_KEY,
      pageNo: 1,
      numOfRows: 20,
      dataType: "JSON",
      base_date,
      base_time,
      nx,
      ny,
    },
  });
```

---

## 응답 파싱

```javascript
export const parseWeatherItems = (data) => {
  const header = data?.response?.header;
  if (header?.resultCode !== "00" && header?.resultCode !== "0000") {
    throw new Error(header?.resultMsg || "기상 API 오류");
  }
  let items = data?.response?.body?.items?.item ?? [];
  if (!Array.isArray(items)) items = items ? [items] : [];
  return items;
};
```

---

## 화면에 보여줄 필드 예시

| API 필드 | 화면 |
|----------|------|
| fcstDate | 예보일 |
| fcstTime | 시각 |
| category | 항목(TMP, SKY 등) |
| fcstValue | 값 |

간단히: `category` + `fcstValue`만 테이블로 출력해도 연습 충분.

---

## 완료 기준

- [ ] GET 성공 (Network 200)
- [ ] 테이블에 5건 이상 표시
- [ ] loading / error 분기
- [ ] 파라미터(nx, ny 또는 base_date) 바꿔 재호출

---

## .env

```
REACT_APP_WEATHER_API_KEY=발급받은_키
```

관광 API와 **키 변수명을 분리**해 두면 시험 때 헷갈리지 않습니다.
