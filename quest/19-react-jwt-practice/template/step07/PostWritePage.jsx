import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { postApi } from "../api/postApi";

/**
 * Step 7~8 PostWritePage — 등록 + 수정 겸용
 */
export default function PostWritePage() {
  const navigate = useNavigate();
  const { bno } = useParams();
  const isEdit = Boolean(bno);

  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  // TODO (Step 8): isEdit이면 getPost로 기존 title, content 불러오기
  // useEffect(() => { ... }, [bno, isEdit]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (isEdit) {
        // TODO: postApi.update(bno, { title, content })
        // navigate(`/posts/${bno}`);
      } else {
        // TODO: postApi.create({ title, content })
        // navigate("/");
      }
    } catch (error) {
      alert("저장 실패");
    }
  };

  return (
    <div>
      <h2>{isEdit ? "글 수정" : "글쓰기"}</h2>
      <form onSubmit={handleSubmit}>
        <input
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="제목"
        />
        <textarea
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder="내용"
          rows={8}
        />
        <button type="submit">{isEdit ? "수정" : "등록"}</button>
      </form>
    </div>
  );
}
