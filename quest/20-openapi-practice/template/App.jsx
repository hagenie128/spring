import { useEffect, useState } from "react";
import { fetchTourList, parseTourItems } from "./api/tourApi";

export default function App() {
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
        areaCode: 1,
      });
      setList(parseTourItems(res.data));
    } catch (e) {
      setError(e.message || "데이터를 불러오지 못했습니다.");
      setList([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleSearch = () => {
    loadData({ keyword });
  };

  return (
    <div style={{ padding: 24 }}>
      <h1>관광지 검색</h1>

      <div>
        <input
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          placeholder="검색어"
        />
        <button onClick={handleSearch}>검색</button>
      </div>

      {loading && <p>Loading...</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}
      {!loading && !error && list.length === 0 && (
        <p>검색 결과가 없습니다.</p>
      )}

      {!loading && !error && list.length > 0 && (
        <table border="1" cellPadding="8" style={{ marginTop: 16, width: "100%" }}>
          <thead>
            <tr>
              <th>제목</th>
              <th>주소</th>
              <th>전화</th>
            </tr>
          </thead>
          <tbody>
            {list.map((item) => (
              <tr key={item.contentid}>
                <td>{item.title}</td>
                <td>{item.addr1}</td>
                <td>{item.tel}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
