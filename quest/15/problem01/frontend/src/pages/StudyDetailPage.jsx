import { useEffect, useState } from "react";
import {
  acceptApplication,
  applyStudy,
  fetchStudy,
  rejectApplication,
  toggleReaction,
} from "../api/studyApi";
import FileUpload from "../components/FileUpload.jsx";

export default function StudyDetailPage({ studyId, onEdit }) {
  const [study, setStudy] = useState(null);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const reload = async () => {
    // TODO frontend-4: 상세 API를 호출하세요.
  };

  useEffect(() => {
    reload();
  }, [studyId]);

  const handleApply = async () => {
    // TODO frontend-4: 신청 API 호출 후 상세를 다시 불러오세요.
  };

  const handleReaction = async (type) => {
    // TODO frontend-4: 좋아요/싫어요 API 호출 후 상세를 다시 불러오세요.
  };

  if (!study) {
    return <p>스터디를 선택하세요.</p>;
  }

  return (
    <section className="detail-layout">
      {error && <p className="error-text">{error}</p>}
      <article className="panel">
        <div className="panel-header">
          <h1>{study.title}</h1>
          <button className="secondary-button" onClick={onEdit}>수정</button>
        </div>
        <p>{study.description}</p>
        <p className="muted">{study.techStack} · {study.method}</p>
        <p>확정 {study.acceptedCount} / {study.capacity}</p>
        <p>조회수 {study.viewCount}</p>
        <button onClick={() => handleReaction("LIKE")}>좋아요 {study.likeCount}</button>
        <button onClick={() => handleReaction("DISLIKE")}>싫어요 {study.dislikeCount}</button>
      </article>

      <article className="panel">
        <h2>스터디 신청</h2>
        <textarea value={message} onChange={(event) => setMessage(event.target.value)} />
        <button className="primary-button" onClick={handleApply}>신청</button>
      </article>

      <article className="panel">
        <h2>합격 멤버</h2>
        {/* TODO frontend-4: acceptedApplications 출력 */}
      </article>

      <article className="panel">
        <h2>대기 신청자</h2>
        {/* TODO frontend-4: waitingApplications 출력 + 합격/거절 버튼 */}
      </article>

      <FileUpload studyId={studyId} materials={study.materials ?? []} onUploaded={reload} />
    </section>
  );
}
