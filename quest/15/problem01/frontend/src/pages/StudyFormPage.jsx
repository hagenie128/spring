import { useState } from "react";
import { createStudy, updateStudy } from "../api/studyApi";

export default function StudyFormPage({ studyId, onDone }) {
  const [form, setForm] = useState({
    leaderId: 1,
    title: "",
    description: "",
    techStack: "",
    method: "",
    capacity: 4,
  });
  const [error, setError] = useState("");

  const change = (event) => {
    setForm({ ...form, [event.target.name]: event.target.value });
  };

  const submit = async (event) => {
    event.preventDefault();
    // TODO frontend-5: 등록/수정 API를 호출하세요.
  };

  return (
    <form className="panel form-panel" onSubmit={submit}>
      <h1>{studyId ? "모집 수정" : "모집 등록"}</h1>
      {error && <p className="error-text">{error}</p>}
      <input name="title" value={form.title} onChange={change} placeholder="제목" />
      <textarea name="description" value={form.description} onChange={change} placeholder="소개글" />
      <input name="techStack" value={form.techStack} onChange={change} placeholder="기술스택" />
      <input name="method" value={form.method} onChange={change} placeholder="진행방식" />
      <input name="capacity" type="number" value={form.capacity} onChange={change} placeholder="정원" />
      <button className="primary-button" type="submit">저장</button>
    </form>
  );
}
