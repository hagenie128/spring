import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { postApi } from "../api/postApi";

/**
 * [6/26 진도] 게시글 상세 페이지
 * 1. useParams()로 URL의 :bno(글번호) 추출
 * 2. GET /api/posts/{bno} → board, commentList를 state에 저장
 * 3. 상세 UI + 댓글 목록 표시 (App.css의 .post-detail 스타일)
 * TODO: 댓글 작성 버튼에 POST /api/comments 연동 필요
 * TODO: post 초기값 {} 대신 null 사용 시 로딩 처리가 정확해짐
 */
export default () => {

  const { bno } = useParams(); // App.js 라우트 /posts/:bno 와 이름 일치
  const [post, setPost] = useState({});
  const [commentList, setCommentList] = useState([]);

  // [진도] bno가 바뀔 때마다(다른 글 클릭) 상세 API 재호출
  useEffect(() => {
    postApi.getPost(bno)
    .then(response => {
      console.log(response.data);
        setPost(response.data.board);
        setCommentList(response.data.commentList);
      }
    ).catch(error => {
      console.error('Error fetching post:', error);
    });
  },[bno]);

  return <div className="container post-detail">
    {
      !post ? <div>Loading...</div> : (
        <>
    <h2 className="post-title">{post.title}</h2>
      <div className="post-meta">
        <span className="author">작성자 : {post.nickname}</span>
        <span className="view-count">조회수 : {post.bcount}</span>
        <span className="write-date">최종 수정일 : {post.writeUpdateDate}</span>
      </div>
      <div className="post-content">{post.content}</div>
      <div className="post-footer">
        <div className="post-footer-group">

        <button type="button" className="like-button">좋아요 👍 {post.blike}</button>
        <button type="button" className="hate-button">싫어요 👎 {post.bhate}</button>
        </div>
        <div className="post-footer-group">
          <button type="button" className="edit-button">수정</button>
          <button type="button" className="delete-button">삭제</button>
          <button type="button" className="list-button">목록으로</button>
        </div>
      </div>
      <div className="comment-area">
      <h3 className="comment-heading">댓글 {commentList.length}개</h3>
      {/* TODO: 댓글 작성 — POST /api/comments, Body: { bno, content }, JWT 필요 */}
      <div className="comment-form">
        <textarea name="content" placeholder="댓글을 입력하세요" rows={4} />
        <button type="submit" className="comment-submit">댓글 작성</button>
      </div>
      <div className="comment-list">
        {commentList.map(item => <div className="comment-item" key={item.cno}>
          <div className="comment-info">
            <span className="author">👤 {item.nickname}</span>
            <span className="date">📅 {item.cdate}</span>
          </div>
          <div className="comment-content">{item.content}</div>
          <div className="comment-actions">
          <button type="button" className="like-button">좋아요 👍</button>
          <button type="button" className="hate-button">싫어요 👎</button>

          <button type="button" className="edit-button">수정</button>
          <button type="button" className="delete-button">삭제</button>
          </div>
        </div>)}
      </div>  
      </div>
      </>
      )}
    </div>
}