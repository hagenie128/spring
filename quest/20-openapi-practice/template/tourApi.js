import axios from "axios";

// TODO: .env 에 REACT_APP_TOUR_API_KEY 설정
const API_KEY = process.env.REACT_APP_TOUR_API_KEY;

// TODO: 시험 문제지 / API 문서의 URL로 수정
const BASE_URL =
  "https://apis.data.go.kr/B551011/KorService2/areaBasedList2";

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

/** 공공 API 응답 → 배열로 통일 */
export const parseTourItems = (data) => {
  const header = data?.response?.header;
  if (header?.resultCode !== "0000") {
    throw new Error(header?.resultMsg || "API 오류");
  }

  let items = data?.response?.body?.items?.item ?? [];
  if (!Array.isArray(items)) {
    items = items ? [items] : [];
  }
  return items;
};
