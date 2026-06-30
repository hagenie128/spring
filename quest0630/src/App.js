import { useEffect, useState } from 'react';
import './App.css';
import {
  fetchLdongRegions,
  fetchLdongSigungu,
  parseItems,
  searchByKeyword,
} from './api/tourApi';

const sortByTitle = (items) =>
  [...items].sort((a, b) => (a.title || '').localeCompare(b.title || '', 'ko'));

const DEFAULT_KEYWORD = '숭례문';

function App() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [list, setList] = useState([]);
  const [keyword, setKeyword] = useState('');
  const [lDongRegnCd, setLDongRegnCd] = useState('');
  const [lDongSignguCd, setLDongSignguCd] = useState('');
  const [regions, setRegions] = useState([]);
  const [sigunguList, setSigunguList] = useState([]);

  useEffect(() => {
    const fetchRegionList = async () => {
      try {
        const res = await fetchLdongRegions();
        const items = parseItems(res.data);
        const regionMap = new Map();
        items.forEach((item) => {
          if (item.lDongRegnCd && !regionMap.has(item.lDongRegnCd)) {
            regionMap.set(item.lDongRegnCd, item.lDongRegnNm);
          }
        });
        const regionList = [...regionMap.entries()]
          .map(([code, name]) => ({ code, name }))
          .sort((a, b) => a.name.localeCompare(b.name, 'ko'));
        setRegions(regionList);
      } catch (e) {
        console.error('지역 목록 로드 실패', e);
      }
    };

    fetchRegionList();
  }, []);

  useEffect(() => {
    const fetchSigunguList = async () => {
      if (!lDongRegnCd) {
        setSigunguList([]);
        return;
      }

      try {
        const res = await fetchLdongSigungu(lDongRegnCd);
        const items = parseItems(res.data)
          .map((item) => ({ code: item.code, name: item.name }))
          .sort((a, b) => a.name.localeCompare(b.name, 'ko'));
        setSigunguList(items);
      } catch (e) {
        console.error('시군구 목록 로드 실패', e);
        setSigunguList([]);
      }
    };

    fetchSigunguList();
  }, [lDongRegnCd]);

  const handleRegionChange = (value) => {
    setLDongRegnCd(value);
    setLDongSignguCd('');
  };

  const handleSearch = async () => {
    setLoading(true);
    setError(null);
    try {
      const searchKeyword = keyword.trim() || DEFAULT_KEYWORD;
      const res = await searchByKeyword(searchKeyword, lDongRegnCd, lDongSignguCd);
      const items = parseItems(res.data);
      setList(sortByTitle(items));
    } catch (e) {
      setError(e.message || 'API 요청 실패');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app">
      <header className="app-header">
        <h1>관광정보 검색 서비스</h1>
        <p>키워드로 전국 관광지를 검색해 보세요</p>
      </header>

      <section className="search-section">
        <h2>키워드 검색</h2>
        <form className="search-form" onSubmit={(e) => e.preventDefault()}>
          <div className="form-row">
            <label htmlFor="keyword">검색어</label>
            <input
              id="keyword"
              type="text"
              name="keyword"
              placeholder="예) 시장, 경복궁, 부산"
              onChange={(e) => setKeyword(e.currentTarget.value)}
              value={keyword}
            />
          </div>
          <div className="form-row form-row--half">
            <div>
              <label htmlFor="lDongRegnCd">지역</label>
              <select
                id="lDongRegnCd"
                name="lDongRegnCd"
                value={lDongRegnCd}
                onChange={(e) => handleRegionChange(e.target.value)}
              >
                <option value="">전체</option>
                {regions.map((region) => (
                  <option key={region.code} value={region.code}>
                    {region.name}
                  </option>
                ))}
              </select>
            </div>
            <div>
              <label htmlFor="lDongSignguCd">시군구</label>
              <select
                id="lDongSignguCd"
                name="lDongSignguCd"
                value={lDongSignguCd}
                onChange={(e) => setLDongSignguCd(e.target.value)}
                disabled={!lDongRegnCd}
              >
                <option value="">전체</option>
                {sigunguList.map((sigungu) => (
                  <option key={sigungu.code} value={sigungu.code}>
                    {sigungu.name}
                  </option>
                ))}
              </select>
            </div>
          </div>
          <button type="button" className="search-btn" onClick={handleSearch}>
            검색
          </button>
        </form>
      </section>

      <section className="results-section">
        <h2>
          검색결과
          {list.length > 0 && <span className="result-count">{list.length}건</span>}
        </h2>

        {loading && (
          <div className="status-message loading">
            <span className="spinner" />
            불러오는 중...
          </div>
        )}

        {error != null && (
          <div className="status-message error">{error}</div>
        )}

        {!loading && !error && list.length === 0 && (
          <div className="status-message empty">검색결과가 없습니다.</div>
        )}

        <div className="result-grid">
          {list.map((item) => (
            <article key={item.contentid} className="tour-card">
              <div className="tour-card__body">
                <dl className="tour-card__info">
                  <div className="info-row">
                    <dt>제목</dt>
                    <dd>{item.title || '-'}</dd>
                  </div>
                  <div className="info-row">
                    <dt>주소</dt>
                    <dd>{item.addr1 || '-'}</dd>
                  </div>
                  <div className="info-row">
                    <dt>대표 이미지</dt>
                    <dd>
                      {item.firstimage ? (
                        <img
                          src={item.firstimage}
                          alt={item.title}
                          className="info-image"
                          loading="lazy"
                        />
                      ) : (
                        <span className="info-empty">이미지 없음</span>
                      )}
                    </dd>
                  </div>
                  <div className="info-row">
                    <dt>콘텐츠 ID</dt>
                    <dd>{item.contentid || '-'}</dd>
                  </div>
                  <div className="info-row">
                    <dt>전화번호</dt>
                    <dd>{item.tel || '-'}</dd>
                  </div>
                </dl>
              </div>
            </article>
          ))}
        </div>
      </section>
    </div>
  );
}

export default App;
