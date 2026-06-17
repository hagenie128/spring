export default function Pagination({ page, pageInfo, onChange }) {
  const total = pageInfo?.totalPages ?? 0;

  if (total <= 1) return null;

  return (
    <nav className="pagination">
      {/* TODO frontend-7: 이전/다음 버튼과 페이지 번호 버튼을 구현하세요.
          - 이전: page <= 0이면 disabled
          - 번호: Array.from({ length: total }, (_, i) => i)로 배열 생성
          - 현재 페이지 번호는 굵게(fontWeight: "bold") 표시
          - 다음: page + 1 >= total이면 disabled */}
    </nav>
  );
}
