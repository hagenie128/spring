import { materialDownloadUrl, uploadMaterial } from "../api/studyApi";

export default function FileUpload({ studyId, materials, onUploaded }) {
  const submit = async (event) => {
    event.preventDefault();
    // TODO frontend-8: FormData로 파일 업로드 API를 호출하세요.
  };

  return (
    <article className="panel">
      <h2>자료 파일</h2>
      <form onSubmit={submit}>
        <input type="file" name="file" />
        <button className="primary-button" type="submit">업로드</button>
      </form>
      <ul>
        {materials.map((material) => (
          <li key={material.id}>
            <a href={materialDownloadUrl(material.id)}>{material.originalName}</a>
          </li>
        ))}
      </ul>
    </article>
  );
}
