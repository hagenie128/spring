import { useEffect, useState } from "react";
import { fetchStudies } from "../api/studyApi";
import Pagination from "../components/Pagination.jsx";
import StudyCard from "../components/StudyCard.jsx";

export default function StudyListPage({ onOpenDetail }) {
  const [keyword, setKeyword] = useState("");
  const [status, setStatus] = useState("");
  const [page, setPage] = useState(0);
  const [studies, setStudies] = useState([]);
  const [pageInfo, setPageInfo] = useState({ totalPages: 0 });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    // TODO frontend-3: fetchStudies({ keyword, status, page, size: 10 })를 호출한 뒤
    //   data.content → setStudies, data → setPageInfo 로 상태를 업데이트하세요.
    //   로딩/에러 상태도 함께 관리하세요.
  }, [keyword, status, page]);

  const handleKeywordChange = (e) => {
    setKeyword(e.target.value);
    setPage(0);
  };

  const handleStatusChange = (e) => {
    setStatus(e.target.value);
    setPage(0);
  };

  return (
    <section>
      <form className="toolbar" onSubmit={(e) => e.preventDefault()}>
        <input
          value={keyword}
          onChange={handleKeywordChange}
          placeholder="제목, 내용, 기술스택 검색"
        />
        <select value={status} onChange={handleStatusChange}>
          <option value="">전체</option>
          <option value="OPEN">모집중</option>
          <option value="CLOSED">마감</option>
        </select>
      </form>

      {error && <p className="error-text">{error}</p>}
      {loading && <p>불러오는 중...</p>}

      <div className="study-grid">
        {studies.length === 0 && !loading && <p>검색 결과가 없습니다.</p>}
        {studies.map((study) => (
          <StudyCard
            key={study.id}
            study={study}
            onClick={() => onOpenDetail(study.id)}
          />
        ))}
      </div>

      <Pagination page={page} pageInfo={pageInfo} onChange={setPage} />
    </section>
  );
}
