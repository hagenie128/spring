import { useEffect, useState } from "react";
import { useParams } from "react-router-dom"
import { postApi } from "../api/postApi";

export default () => {
  const {bno} = useParams();
  const [post ,setPost] = useState({});
  const [commentList, setCommentList] = useState([]);
  console.log(bno);
  useEffect(() => {
    postApi.getPost(bno).then(reponse => {
      setPost(reponse.data.board);
      setCommentList(reponse.data.commentList);
      console.log(reponse.data);
    }).catch(error=>{
      console.log(error);
    });
  },[bno]);

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
        <div className="post-detail-content">{post.content}</div>
        <div className="post-detail-footer">
          <div className="post-footer-group">
            <button className="btn btn-success-outline">좋아요 👍</button>
            <button className="btn btn-danger-outline">싫어요 👎</button>
          </div>
          <div className="post-footer-group">
            <button className="btn btn-secondary">수정</button>
            <button className="btn btn-danger-outline">삭제</button>
          </div>
        </div>
        <div className="comment-section">
          <h3 className="comment-title">댓글 목록 ({commentList ? commentList.length : 0})</h3>
          <div className="comment-form">
            <textarea className="comment-textarea" placeholder="댓글을 입력해 주세요."></textarea>
            <button className="comment-submit-btn">댓글<br/>등록</button>
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