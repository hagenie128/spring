import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { postApi } from "../api/postApi";

export default () => {

  const { id } = useParams();
  const [post, setPost] = useState({});
  const [commentList, setCommentList] = useState([]);

  useEffect(() => {
    postApi.getPostById(id)
    .then(response => {
        setPost(response.data.board);
        setCommentList(response.data.commentList);
      }
    )
  },[id]);

  return <div className="container post-detail">
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
      <div className="comment-form">
        <textarea name="content" placeholder="댓글을 입력하세요" rows={4} />
        <button type="submit" className="comment-submit">댓글 작성</button>
      </div>
      <div className="comment-list">
        {commentList.map(item => <div className="comment-item" key={item.cno}>
          <div className="comment-info">
            <span className="author">작성자 : {item.nickname}</span>
            <span className="date">작성일 : {item.cdate}</span>
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
  </div>
}