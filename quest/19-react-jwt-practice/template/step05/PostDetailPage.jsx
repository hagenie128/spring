import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { postApi } from "../api/postApi";

/**
 * Step 5 PostDetailPage — TODO 채우기
 */
export default function PostDetailPage() {
  // TODO 1: useParams — 라우트 /posts/:bno 와 이름 일치!
  const { bno } = useParams();

  const [post, setPost] = useState(null);
  const [comments, setComments] = useState([]);

  // TODO 2: bno 바뀔 때 상세 API 호출
  useEffect(() => {
    // postApi.getPost(bno)
    // .then(res => {
    //   setPost(res.data.board);
    //   setComments(res.data.commentList);
    // });
  }, [bno]);

  if (!post) return <p>로딩 중...</p>;

  return (
    <div>
      <Link to="/">← 목록</Link>
      <h2>{post.title}</h2>
      <p>작성자: {post.nickname}</p>
      <div>{post.content}</div>

      <h3>댓글 {comments.length}개</h3>
      <ul>
        {comments.map((c) => (
          <li key={c.cno}>
            <strong>{c.nickname}</strong>: {c.content}
          </li>
        ))}
      </ul>
    </div>
  );
}
