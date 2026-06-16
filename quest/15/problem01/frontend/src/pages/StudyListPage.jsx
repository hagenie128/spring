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

  useEffect(() => {
    // TODO frontend-3: fetchStudies 호출 후 studies/pageInfo 상태를 갱신하세요.
  }, [keyword, status, page]);

  return (
    <section>
      <form className="toolbar" onSubmit={(event) => event.preventDefault()}>
        <input
          value={keyword}
          onChange={(event) => setKeyword(event.target.value)}
          placeholder="제목, 내용, 기술스택 검색"
        />
        <select value={status} onChange={(event) => setStatus(event.target.value)}>
          <option value="">전체</option>
          <option value="OPEN">모집중</option>
          <option value="CLOSED">마감</option>
        </select>
      </form>

      {error && <p className="error-text">{error}</p>}

      <div className="study-grid">
        {/* TODO frontend-3: StudyCard 목록을 출력하세요. */}
        {studies.map((study) => (
          <StudyCard key={study.id} study={study} onClick={() => onOpenDetail(study.id)} />
        ))}
      </div>

      <Pagination page={page} pageInfo={pageInfo} onChange={setPage} />
    </section>
  );
}
