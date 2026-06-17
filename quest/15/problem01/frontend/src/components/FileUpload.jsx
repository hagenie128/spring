import { useRef } from "react";
import { materialDownloadUrl, uploadMaterial } from "../api/studyApi";

export default function FileUpload({ studyId, materials, onUploaded }) {
  const fileInputRef = useRef(null);

  const submit = async (e) => {
    e.preventDefault();
    const file = fileInputRef.current?.files[0];
    if (!file) {
      alert("파일을 선택해주세요.");
      return;
    }
    try {
      // TODO frontend-8: FormData로 파일 업로드를 구현하세요.
      //   uploadMaterial(studyId, file)을 호출하고,
      //   성공 시 파일 input을 초기화(fileInputRef.current.value = "")하고 onUploaded()를 호출하세요.
    } catch (e) {
      alert("파일 업로드에 실패했습니다: " + (e.response?.data?.message || e.message));
    }
  };

  return (
    <article className="panel">
      <h2>📎 자료 파일</h2>

      <form onSubmit={submit} style={{ display: "flex", gap: "8px", alignItems: "center" }}>
        <input type="file" ref={fileInputRef} name="file" />
        <button className="primary-button" type="submit">
          업로드
        </button>
      </form>

      {materials.length === 0 ? (
        <p className="muted">업로드된 파일이 없습니다.</p>
      ) : (
        <ul>
          {materials.map((material) => (
            <li key={material.id}>
              <a href={materialDownloadUrl(material.id)} download>
                📄 {material.originalName}
              </a>
              <span className="muted">
                &nbsp;({Math.round(material.fileSize / 1024)}KB)
              </span>
            </li>
          ))}
        </ul>
      )}
    </article>
  );
}
