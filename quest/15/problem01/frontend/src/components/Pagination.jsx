export default function Pagination({ page, pageInfo, onChange }) {
  return (
    <nav className="pagination">
      {/* TODO frontend-7: 이전/다음/페이지 번호 버튼을 작성하세요. */}
      <button disabled={page <= 0} onClick={() => onChange(page - 1)}>이전</button>
      <span>{page + 1}</span>
      <button disabled={page + 1 >= pageInfo.totalPages} onClick={() => onChange(page + 1)}>다음</button>
    </nav>
  );
}
