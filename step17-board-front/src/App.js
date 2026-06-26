import logo from './logo.svg';
import './App.css';
import NavBar from './components/NavBar';
import { Route, Routes } from 'react-router-dom';
import PostListPage from './pages/PostListPage';
import LoginPage from './pages/LoginPage';
import SignupPage from './pages/SignupPage';
import PostDetailPage from './pages/PostDetailPage';
import PostWritePage from './pages/PostWritePage';
/*
    [6/26 진도] React Router 라우트 구조
      /               → 게시글 목록 (공개, GET /api/posts)
      /login          → 로그인 (공개)
      /signup         → 회원가입 (공개)
      /posts/:bno     → 게시글 상세 (공개, GET /api/posts/{bno})
      /posts/create   → 게시글 작성 (인증 필요, POST /api/posts)
      /posts/:bno/edit → 게시글 수정 (인증 필요, PATCH /api/posts/{bno})

    주의: URL 파라미터 이름(:bno)과 useParams()에서 꺼내는 이름이 같아야 한다.
*/
function App() {
  return  <>
    <NavBar/>
    <hr />
    <Routes>
      {/* 공개 */}
      <Route path='/' element={<PostListPage/>}/>
      <Route path='/login' element={<LoginPage/>}/>
      <Route path='/signup' element={<SignupPage/>}/>
      <Route path='/posts/:bno' element={<PostDetailPage/>}/>
      {/* 인증 */}

      <Route path='/posts/create' element={<PostWritePage/>}/>
      <Route path='/posts/:bno/edit' element={<PostWritePage/>}/>

    </Routes>
  </>;
}

export default App;
