import { useEffect, useState } from "react";
import { useParams } from "react-router-dom"
import { postApi } from "../api/postApi";
import "quill/dist/quill.snow.css";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default () => {
  const { bno } = useParams();
  const [post, setPost] = useState(null);
  const [commentList, setCommentList] = useState([]);
  const { user } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    // 수업 포인트:
    // 상세 페이지는 URL 파라미터 bno로 게시글/댓글을 함께 조회한다.
    postApi.getPost(bno).then(reponse => {
      setPost(reponse.data.board);
      setCommentList(reponse.data.commentList);
    }).catch(error => {
      console.log(error);
    });
  }, [bno]);

  const handleDelete = async () => {
    if (window.confirm('삭제하시겠습니까?')) {// confirm 창 띄울시 window.confirm() 사용
      try {
        await postApi.remove(bno);
        navigate('/');
      } catch (error) {
        if (error.response.status === 403) {
          alert('삭제 권한이 없습니다.');
        } else {
          alert('삭제에 실패했습니다.');
        }
        console.log(error);
      }
    } else {
      return;
    }
  }

  const handleReaction = async (type) => {
    // 수업 포인트:
    // 좋아요/싫어요는 현재 로그인 회원 기준으로 토글 처리되고,
    // 응답으로 받은 count 값으로 화면 숫자만 즉시 갱신한다.
    await postApi.postReaction({mid: user.id, bno: post.bno, type: type})
    .then(res => {
      setPost(prev => ({...prev, blike: res.data.count.likeCount, 
        bhate: res.data.count.dislikeCount}));
    })
    .catch(error => {
      console.log(error);
    });
  }

  const isEdit = user && (user.id === post.mid);

  return <div className="post-detail-container">
    {
      !post ? <div className="post-loading">현재 게시글 읽어오고 있습니다.</div> :
        <>
          <h2 className="post-detail-title">{post.title}</h2>
          <div className="post-detail-meta">
            <span className="meta-item"><span className="meta-label">작성자</span> {post.nickname}</span>
            <span className="meta-item"><span className="meta-label">조회수</span> {post.bcount}</span>
            <span className="meta-item"><span className="meta-label">작성일</span> {post.writeUpdateDate}</span>
          </div>
          <div className="post-detail-content ql-container ql-snow" style={{ border: 'none' }}>
            <div dangerouslySetInnerHTML={{ __html: post.content }}></div>
          </div>
          <div className="post-detail-footer">
            <div className="post-footer-group">
              <button className="btn btn-success-outline" onClick={
                () => handleReaction('like')}>좋아요 👍<span className="like-count">
                  {post.blike}</span></button>
              <button className="btn btn-danger-outline" onClick={
                () => handleReaction('dislike')}>싫어요 👎<span className="dislike-count">
                  {post.bhate ? post.bhate : 0}</span></button>
            </div>
            {isEdit && (
              <div className="post-footer-group">
                <button className="btn btn-secondary" onClick={() => navigate(`/posts/${post.bno}/edit`)}>수정</button>
                <button className="btn btn-danger-outline" onClick={handleDelete}>삭제</button>
              </div>
            )}
          </div>
          <div className="comment-section">
            <h3 className="comment-title">댓글 목록 ({commentList ? commentList.length : 0})</h3>
            <div className="comment-form">
              <textarea className="comment-textarea" placeholder="댓글을 입력해 주세요."></textarea>
              <button className="comment-submit-btn">댓글<br />등록</button>
            </div>
            <div className="comment-list">
              {commentList && commentList.map((item, index) => <div key={item.cno || index} className="comment-item">
                <div className="comment-header">
                  <div className="comment-info">
                    <span>👤 {item.nickname}</span>
                    <span>📅 {item.cdate}</span>
                  </div>
                  <div className="comment-action">
                    <button className="btn-comment-action">좋아요 👍</button>
                    <button className="btn-comment-action">싫어요 👎</button>
                    <button className="btn-comment-action">수정</button>
                    <button className="btn-comment-action btn-comment-danger">삭제</button>
                  </div>
                </div>
                <div className="comment-content">{item.content}</div>
              </div>)}
            </div>
          </div>
        </>
    }
  </div>
}