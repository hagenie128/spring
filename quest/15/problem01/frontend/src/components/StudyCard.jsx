export default function StudyCard({ study, onClick }) {
  return (
    <button className="study-card" onClick={onClick}>
      {/* TODO frontend-6: 카드에 모집글 정보를 표시하세요.
          study 객체의 title, status, techStack, method, acceptedCount, capacity, viewCount, leaderNickname을 활용하세요.
          status가 "OPEN"이면 "모집중", "CLOSED"이면 "마감" 배지를 표시하세요. */}
    </button>
  );
}
