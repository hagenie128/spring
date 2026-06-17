import { useEffect, useState } from "react";
import {
  acceptApplication,
  applyStudy,
  fetchStudy,
  rejectApplication,
  toggleReaction,
} from "../api/studyApi";
import FileUpload from "../components/FileUpload.jsx";

const CURRENT_USER_ID = 1; // 테스트용 고정 회원 ID

export default function StudyDetailPage({ studyId, onEdit }) {
  const [study, setStudy] = useState(null);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const reload = async () => {
    // TODO frontend-4: fetchStudy(studyId)를 호출해 study 상태를 업데이트하세요.
    //   로딩/에러 상태도 함께 관리하세요.
  };

  useEffect(() => {
    reload();
  }, [studyId]);

  const handleApply = async () => {
    // TODO frontend-4: applyStudy(studyId, { applicantId: CURRENT_USER_ID, message })를 호출하세요.
    //   성공 시 message 초기화, reload() 호출, alert("신청이 완료됐습니다!") 표시
  };

  const handleReaction = async (type) => {
    // TODO frontend-4: toggleReaction(studyId, { memberId: CURRENT_USER_ID, type })를 호출하고 reload()하세요.
  };

  const handleAccept = async (applicationId) => {
    // TODO frontend-4: acceptApplication(applicationId)를 호출하고 reload()하세요.
  };

  const handleReject = async (applicationId) => {
    // TODO frontend-4: rejectApplication(applicationId)를 호출하고 reload()하세요.
    //   window.confirm()으로 확인 후 진행하세요.
  };

  if (loading) return <p>불러오는 중...</p>;
  if (!study) return <p>스터디를 선택하세요.</p>;

  return (
    <section className="detail-layout">
      {error && <p className="error-text">{error}</p>}

      <article className="panel">
        <div className="panel-header">
          <h1>{study.title}</h1>
          <button className="secondary-button" onClick={onEdit}>수정</button>
        </div>
        <p style={{ whiteSpace: "pre-line" }}>{study.description}</p>
        <p className="muted">
          🛠 {study.techStack} &nbsp;|&nbsp; 📍 {study.method}
        </p>
        <p>
          👥 확정 {study.acceptedCount} / {study.capacity}명 &nbsp;
          <span className={study.status === "OPEN" ? "badge-open" : "badge-closed"}>
            {study.status === "OPEN" ? "모집중" : "마감"}
          </span>
        </p>
        <p>👁 조회수 {study.viewCount}</p>
        <div>
          <button onClick={() => handleReaction("LIKE")}>
            👍 좋아요 {study.likeCount}
          </button>
          &nbsp;
          <button onClick={() => handleReaction("DISLIKE")}>
            👎 싫어요 {study.dislikeCount}
          </button>
        </div>
      </article>

      {study.status === "OPEN" && (
        <article className="panel">
          <h2>스터디 신청</h2>
          <textarea
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            placeholder="신청 메시지를 입력하세요 (자기소개, 지원 동기 등)"
            rows={3}
          />
          <button className="primary-button" onClick={handleApply}>
            신청하기
          </button>
        </article>
      )}

      <article className="panel">
        <h2>✅ 합격 멤버 ({study.acceptedApplications?.length ?? 0}명)</h2>
        {study.acceptedApplications?.length === 0 && <p>합격한 멤버가 없습니다.</p>}
        {study.acceptedApplications?.map((app) => (
          <div key={app.id} className="application-item">
            <strong>{app.applicantNickname}</strong>
            <span className="muted"> — {app.message}</span>
          </div>
        ))}
      </article>

      <article className="panel">
        <h2>⏳ 대기 신청자 ({study.waitingApplications?.length ?? 0}명)</h2>
        {study.waitingApplications?.length === 0 && <p>대기 중인 신청자가 없습니다.</p>}
        {study.waitingApplications?.map((app) => (
          <div key={app.id} className="application-item">
            <strong>{app.applicantNickname}</strong>
            <span className="muted"> — {app.message}</span>
            <div>
              <button
                className="primary-button"
                onClick={() => handleAccept(app.id)}
                disabled={study.status === "CLOSED"}
              >
                합격
              </button>
              &nbsp;
              <button
                className="secondary-button"
                onClick={() => handleReject(app.id)}
              >
                거절
              </button>
            </div>
          </div>
        ))}
      </article>

      <FileUpload
        studyId={studyId}
        materials={study.materials ?? []}
        onUploaded={reload}
      />
    </section>
  );
}
