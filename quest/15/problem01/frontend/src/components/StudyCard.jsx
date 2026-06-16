export default function StudyCard({ study, onClick }) {
  return (
    <button className="study-card" onClick={onClick}>
      {/* TODO frontend-6: 카드에 모집글 정보를 보기 좋게 출력하세요. */}
      <strong>{study.title}</strong>
      <span>{study.techStack}</span>
    </button>
  );
}
