import axios from "axios";

const BASE_URL = "https://apis.data.go.kr/B551011/KorService2";

const defaultParams = {
  serviceKey: process.env.REACT_APP_TOUR_API_KEY,
  MobileOS: "ETC",
  MobileApp: "AppTest",
  _type: "json",
};

export const parseItems = (data) => {
  const raw = data?.response?.body?.items;
  if (!raw || raw === "") {
    return [];
  }

  let items = raw.item ?? [];
  if (!Array.isArray(items)) {
    items = items ? [items] : [];
  }
  return items;
};

export const searchByKeyword = (keyword, lDongRegnCd, lDongSignguCd) => {
  const params = {
    ...defaultParams,
    numOfRows: 10,
    pageNo: 1,
    arrange: "A",
    keyword: keyword || "",
  };

  if (lDongRegnCd) {
    params.lDongRegnCd = lDongRegnCd;
  }
  if (lDongSignguCd) {
    params.lDongSignguCd = lDongSignguCd;
  }

  return axios.get(`${BASE_URL}/searchKeyword2`, { params });
};

/** 법정동 시도 목록 (ldongCode2, lDongListYn=Y) */
export const fetchLdongRegions = () =>
  axios.get(`${BASE_URL}/ldongCode2`, {
    params: {
      ...defaultParams,
      numOfRows: 300,
      pageNo: 1,
      lDongListYn: "Y",
    },
  });

/** 법정동 시군구 목록 (ldongCode2, lDongRegnCd + lDongListYn=N) */
export const fetchLdongSigungu = (lDongRegnCd) =>
  axios.get(`${BASE_URL}/ldongCode2`, {
    params: {
      ...defaultParams,
      numOfRows: 100,
      pageNo: 1,
      lDongRegnCd,
      lDongListYn: "N",
    },
  });
