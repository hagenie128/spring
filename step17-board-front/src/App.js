import './App.css';
import NavBar from './components/NavBar';
import { Route, Routes } from 'react-router-dom';
import PostListPage from './pages/PostListPage';
import LoginPage from './pages/LoginPage';
import SignupPage from './pages/SignupPage';
import PostDetailPage from './pages/PostDetailPage';
import PostWritePage from './pages/PostWritePage';
/*
    라우터 구조
      /               →  전체 게시글 목록(공개)
      /login          →  로그인(공개)
      /signup         →  회원가입(공개)
      /posts/:id      → 게시글 상세(공개)
      /posts/create   → 게시글 작성(인증 필요)
      /posts/:id/edit → 게시글 수정(인증 필요)
*/
function App() {
  return (
    <div className="app-container">
      <NavBar />
      <main className="main-content">
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
      </main>
    </div>
  );
}

export default App;
