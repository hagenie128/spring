import { useState } from "react";
import { createStudy, updateStudy } from "../api/studyApi";

const CURRENT_LEADER_ID = 1; // 테스트용 리더 ID (DataInitializer의 leader1)

export default function StudyFormPage({ studyId, onDone }) {
  const [form, setForm] = useState({
    leaderId: CURRENT_LEADER_ID,
    title: "",
    description: "",
    techStack: "",
    method: "",
    capacity: 4,
  });
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const change = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const submit = async (e) => {
    e.preventDefault();
    if (submitting) return;
    setError("");
    setSubmitting(true);
    try {
      // TODO frontend-5: studyId가 있으면 updateStudy(studyId, {...}),
      //   없으면 createStudy({...})를 호출하세요.
      //   성공 시 onDone()을 호출하세요.
    } catch (e) {
      setError(e.response?.data?.message || "저장에 실패했습니다. 다시 시도해주세요.");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <form className="panel form-panel" onSubmit={submit}>
      <h1>{studyId ? "모집글 수정" : "스터디 모집 등록"}</h1>

      {error && <p className="error-text">{error}</p>}

      <label>
        제목 *
        <input
          name="title"
          value={form.title}
          onChange={change}
          placeholder="스터디 제목을 입력하세요"
          required
        />
      </label>

      <label>
        소개글 *
        <textarea
          name="description"
          value={form.description}
          onChange={change}
          placeholder="스터디 소개, 목표, 진행 방식 등을 자세히 적어주세요"
          rows={5}
          required
        />
      </label>

      <label>
        기술스택
        <input
          name="techStack"
          value={form.techStack}
          onChange={change}
          placeholder="예: React, TypeScript, Node.js"
        />
      </label>

      <label>
        진행방식
        <input
          name="method"
          value={form.method}
          onChange={change}
          placeholder="예: 온라인 (디스코드), 오프라인 (서울 강남)"
        />
      </label>

      <label>
        정원
        <input
          name="capacity"
          type="number"
          value={form.capacity}
          onChange={change}
          min={1}
          max={20}
        />
      </label>

      <div style={{ display: "flex", gap: "8px" }}>
        <button className="primary-button" type="submit" disabled={submitting}>
          {submitting ? "저장 중..." : "저장"}
        </button>
        <button type="button" className="secondary-button" onClick={onDone}>
          취소
        </button>
      </div>
    </form>
  );
}
