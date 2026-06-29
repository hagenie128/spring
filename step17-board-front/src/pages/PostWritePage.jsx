import { useEffect, useState } from "react";
import QuillEditor from "../components/QuillEditor"
import { useNavigate, useParams } from "react-router-dom";
import { postApi } from "../api/postApi";


export default () => {
  const {bno} = useParams();// 게시글 번호
  
  // 수정인지? 작성인지?
  const isEditorMode = !!bno;
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  // 1. 제목, quill Editor 글내용 입력 받는 폼
  const [form, setForm] = useState({title:'',content:''});
  const [error, setError] = useState('');
  
  useEffect(() => {
    // 수업 포인트:
    // 수정 페이지는 URL에 이미 글번호가 있으므로, 진입 시 기존 글을 한 번 불러와 폼 초기값으로 사용한다.
    if(!isEditorMode) return; // 수정모드가 아니면 실행하지 않음
      postApi.getPost(bno).then((reponse) => {
        setForm(prev => ({...prev, title: reponse.data.board.title, content: reponse.data.board.content}));
      }).catch(error=>{
        console.log(error);
        alert('게시글 정보를 불러오는데 실패했습니다.');
        navigate('/');
      }).finally(() => {
        setLoading(false);
      });
    },[bno,isEditorMode, navigate]);
  const onChangePostDetail = (newPostDetail) => {
    setForm(prevForm => ({...prevForm, content : newPostDetail}));
  }
  
  const onChangeTitle = (e) => {
    setForm(prevForm => ({...prevForm, title : e.target.value}));
  }
  
  // 2. 글쓰기 버튼 클릭시 게시글 전송
  const handleSubmit = async () => {
    try{
      if(!form.title.trim() || !form.content.trim()){
        alert('제목과 내용을 입력해 주세요');
        return;
      }

      setLoading(true);
      if(isEditorMode){
        // 수업 포인트:
        // PATCH 응답은 204 No Content라서 서버가 글번호를 다시 주지 않는다.
        // 그래서 수정 후 이동은 응답값이 아니라 URL의 bno를 그대로 사용한다.
        await postApi.update(bno, form);
        navigate(`/posts/${bno}`);
      }else{
        // 수업 포인트:
        // create 응답에는 생성된 게시글 객체가 들어오므로, 그 안의 bno로 상세 페이지 이동 가능하다.
        const res = await postApi.create(form);
        navigate(`/posts/${res.data.board.bno}`);
      }
    }catch(err){
      console.log(err);
      setError(err.response?.data?.message || '저장에 실패했습니다.');
    }finally{
      setLoading(false);
    }
  }

  return <div className="container">
    <h2>{isEditorMode ? '게시글 수정' : '게시글 쓰기'}</h2>
    <div className="post-title">
      <label>제목</label>
      <input type="text" placeholder="제목을 입력하세요" onChange={onChangeTitle} value={form.title}/>
    </div>
    <div className="post-detail">
      <label>내용</label>
      <div id="editor">
        <QuillEditor key={bno || "create"} onChange={onChangePostDetail} defaultValue={form.content}/>
      </div>
    </div>
    { error && <div className="error-box">{error}</div>}
    <div className="post-actions">
      <button onClick={handleSubmit} disabled={loading}>{
      loading ? '저장 중.....' : (isEditorMode ? '수정하기' : '글쓰기')}</button>
      <button onClick={() => navigate(-1)}>취소</button>
    </div>
  </div>
}