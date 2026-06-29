import { useContext, useState } from "react";
import QuillEditor from "../components/QuillEditor"
import { useParams } from "react-router-dom";


export default () => {
  // 수정인지? 작성인지?
  const {bno} = useParams();
  const isEditorMode = !!bno;

  // 1. 제목, quill Editor 글내용 입력 받는 폼
  const [form, setForm] = useState({title:'',content:''});
  
  const onChangePostDetail = (newPostDetail) => {
    setForm({...form, content : newPostDetail});
  }
  
  const onChangeTitle = (e) => {
    setForm({...form, title : e.taget.value});
  }
  
  // 2. 글쓰기 버튼 클릭시 게시글 전송
  const handleSubmit = () => {
    
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
        <QuillEditor onChange={onChangePostDetail} defaultVale={form.content}/>
      </div>
    </div>
    <div className="error-box"></div>
    <div className="post-actions">
      <button>글쓰기</button><button>취소</button>
    </div>
  </div>
}
