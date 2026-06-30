import { useEffect, useRef, useState } from "react";
import { Link, useParams } from "react-router-dom"
import { postApi } from "../api/postApi";
import "quill/dist/quill.snow.css";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import DOMPurify from "dompurify";

const PostDetailPage = () => {
  const { bno } = useParams();
  const [post, setPost] = useState(null);
  const [commentList, setCommentList] = useState([]);
  const { user } = useAuth();
  const navigate = useNavigate();
  const commentForm = useRef(null);
  //댓글 수정 위한 상태값
  const [commentEditMode, setCommentEditMode] = useState(null);
  const [commentEditContent, setCommentEditContent] = useState('');

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
    if (!user) {
      alert('로그인이 필요합니다.');
      navigate('/login');
      return;
    }
    await postApi.postReaction({ mid: user.id, bno: post.bno, type: type })
      .then(res => {
        setPost(prev => ({
          ...prev, blike: res.data.count.likeCount,
          bhate: res.data.count.dislikeCount
        }));
      })
      .catch(error => {
        console.log(error);
      });
  }

  const handleCommentReaction = async (type, cno) => {
    if (!user) {
      alert('로그인이 필요합니다.');
      navigate('/login');
      return;
    }
    await postApi.postCommentReaction({ type: type, cno: cno })
      .then(res => {
        setCommentList(prev => prev.map(comment => {
          if (cno === comment.cno) {
            return { ...comment, clike: res.data.count.likeCount, chate: res.data.count.dislikeCount };
          }
          return comment;
        }));
      })
      .catch(err => console.log(err))
  }


  const isEdit = user && post && (user.id === post.mid);

  const handleAddComment = async () => {
    await postApi.addComment({ bno: post.bno, content: commentForm.current.value })
      .then(res => {
        console.log(res.status);
        setCommentList(res.data.commentList);
      }).catch(err => console.log(err))
  }

  const handleDeleteComment = async (cno) => {
    console.log(cno);
    await postApi.deleteComment(cno)
      .then(res => {
        console.log(res.status);
        setCommentList(res.data.commentList);
      }).catch(err => console.log(err))
  }


  const handleUpdateComment = async (cno) => {
    if (!user) {
      alert('로그인이 필요합니다.');
      navigate('/login');
      return;
    }
    await postApi.updateComment({ cno, content: commentEditContent })
      .then(res => {
        setCommentList(res.data.commentList);
        setCommentEditMode(null);
      }).catch(err => console.log(err))
  }

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
            <div dangerouslySetInnerHTML={{ __html: DOMPurify.sanitize(post.content) }}></div>
          </div>
          <div className="post-detail-footer">
            <div className="post-footer-group">
              <button className="btn btn-success-outline" onClick={
                () => handleReaction('LIKE')}>좋아요 👍<span className="like-count">
                  {post.blike}</span></button>
              <button className="btn btn-danger-outline" onClick={
                () => handleReaction('DISLIKE')}>싫어요 👎<span className="dislike-count">
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
            {
              user ?
                <div className="comment-form">
                  <textarea className="comment-textarea" placeholder="댓글을 입력해 주세요." ref={commentForm}></textarea>
                  <button className="comment-submit-btn" onClick={handleAddComment}>댓글<br />등록</button>
                </div>
                :
                <div className="message-box">
                  <Link to={"/login"} className="nav-link" style={{ textAlign: 'center' }}>
                    댓글을 작성하시려면 로그인하세요
                  </Link>
                </div>
            }
            <div className="comment-list">
              {commentList && commentList.map((item, index) => <div key={item.cno || index} className="comment-item">
                {item.cno === commentEditMode ?
                  <div className="comment-form">
                    <textarea className="comment-textarea" placeholder="댓글을 입력해 주세요." onChange={(e) => setCommentEditContent(e.target.value)} defaultValue={item.content}></textarea>
                    <button className="comment-submit-btn" onClick={() => { handleUpdateComment(item.cno) }}>수정</button>
                    <button className="comment-submit-btn" onClick={() => { setCommentEditMode(null) }}>취소</button>
                  </div>
                  :
                  <>
                    <div className="comment-header">
                      <div className="comment-info">
                        <span>👤 {item.nickname}</span>
                        <span>📅 {item.cdate}</span>
                      </div>
                      <div className="comment-action">
                        <button className="btn-comment-action" onClick={() => handleCommentReaction('LIKE', item.cno)}>좋아요 👍<span>{item.clike}</span></button>
                        <button className="btn-comment-action" onClick={() => handleCommentReaction('DISLIKE', item.cno)}>싫어요 👎<span>{item.chate}</span></button>
                        {user && user.id === item.mid &&
                          <>
                            <button className="btn-comment-action" onClick={() => { setCommentEditMode(item.cno) }}>수정</button>
                            <button className="btn-comment-action btn-comment-danger" onClick={() => handleDeleteComment(item.cno)}>삭제</button>
                          </>
                        }
                      </div>
                    </div>
                    <div className="comment-content">{item.content}</div>
                  </>
                }


              </div>)}
            </div>
          </div>
        </>
    }
  </div >
}

export default PostDetailPage;
