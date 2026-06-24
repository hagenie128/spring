import logo from './logo.svg';
import './App.css';
import { useEffect, useRef, useState } from 'react';
import axios from 'axios';

// 백엔드 서버의 공통 주소입니다.
// 회원가입·로그인처럼 여러 API를 호출할 때 이 값을 앞에 붙이면,
// 서버 주소가 바뀌더라도 이 한 곳만 수정하면 됩니다.
const BASE_URL = "http://localhost:8888";

// JWT(JSON Web Token)를 브라우저의 localStorage에 저장하고 꺼내는 기능을
// 한 객체에 모아 둔 작은 저장소입니다. 컴포넌트가 저장 방식의 세부 내용을
// 직접 알지 않아도 되므로, 토큰 관련 코드가 여러 곳에 흩어지는 것을 줄여 줍니다.
const tokenStore = {
  // access token은 보호된 API를 호출할 때 인증 정보로 사용하는 짧은 수명의 토큰입니다.
  getAccessToken : ()=> localStorage.getItem("accessToken"),
  setAccessToken : (token)=> localStorage.setItem("accessToken",token),

  // refresh token은 access token이 만료되었을 때 새 access token을 발급받는 데 사용합니다.
  getRefreshToken : ()=> localStorage.getItem("refreshToken"),
  setRefreshToken : (token)=> localStorage.setItem("refreshToken",token),

  // 로그아웃하거나 인증 정보가 더 이상 유효하지 않을 때 두 토큰을 함께 제거합니다.
  clearToken : ()=> {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
  }
}

function App() {
  // useRef는 입력창 DOM 요소를 기억합니다. 사용자가 버튼을 누른 시점에
  // ref.current.value를 읽으면 각 입력창에 현재 적힌 값을 가져올 수 있습니다.
  // 여기서는 입력할 때마다 state를 갱신하지 않는 비제어 컴포넌트 방식을 사용합니다.
  const usernameRef = useRef(null);
  const passwordRef = useRef(null);
  const emailRef = useRef(null);
  const [accessToken, setAccessToken] = useState(tokenStore.getAccessToken() || '');
  const [messageBox, setMessageBox] = useState('');

  useEffect(() => {
    // OAuth2 로그인은 일반 로그인처럼 axios 응답으로 바로 토큰을 받지 않습니다.
    // 사용자가 Google 로그인 화면을 거쳐 오면 백엔드의 OAuth2SuccessHandler가
    // /oauth2/callback?accessToken=...&refreshToken=... 형태로 프론트에 다시 보내 줍니다.
    const param = new URLSearchParams(window.location.search);

    if(window.location.pathname === '/oauth2/callback'){
      const accessToken = param.get('accessToken');
      const refreshToken = param.get('refreshToken');
      if(accessToken && refreshToken){
        // 쿼리스트링에서 꺼낸 토큰을 localStorage에 저장해 두면 새로고침 후에도 인증 상태를 유지할 수 있습니다.
        // 운영 환경에서는 토큰이 URL 기록에 남을 수 있으므로 HttpOnly 쿠키 방식도 함께 고려합니다.
        tokenStore.setAccessToken(accessToken);
        tokenStore.setRefreshToken(refreshToken);
        setAccessToken(accessToken);
        setMessageBox("구글 로그인과 JWT 저장이 완료되었습니다.");
      }
    }
  },[accessToken])


  // async 함수로 선언하면 axios 요청이 끝날 때까지 await로 기다릴 수 있습니다.
  const handleRegister = async () => {
    // 버튼을 클릭한 뒤 실행되므로 current에는 화면에 렌더링된 input 요소가 들어 있습니다.
    const username = usernameRef.current.value;
    const password = passwordRef.current.value;
    const email = emailRef.current.value;

    console.log(username,password,email);

    // 두 번째 인자로 전달한 객체는 JSON 요청 본문으로 변환되어 백엔드로 전송됩니다.
    // await 뒤의 코드는 서버가 응답한 다음 실행되며, 실제 응답 본문은 response.data에 있습니다.
    const response = await axios.post(BASE_URL+"/auth/signup",{username,password,email});
    
    console.log(response.data);
    setMessageBox(JSON.stringify(response.data));


  };

  // 회원가입 입력창과 로그인 입력창은 서로 다른 DOM 요소이므로 별도의 ref를 사용합니다.
  const loginUserName = useRef(null);
  const loginPassword = useRef(null);

  const handleLogin = async () => {
    // 일반 로그인 흐름입니다.
    // 1. 사용자가 입력한 아이디/비밀번호를 백엔드에 보냅니다.
    // 2. 백엔드는 AuthenticationManager로 비밀번호를 검증합니다.
    // 3. 성공하면 accessToken, refreshToken을 JSON으로 내려 줍니다.
    const username = loginUserName.current.value;
    const password = loginPassword.current.value;
    const url = `${BASE_URL}/auth/login`
    const response = await axios.post(url,{username,password});
    console.log(response.data);
    // accessToken은 보호된 API 호출마다 Authorization 헤더에 넣어 보냅니다.
    // refreshToken은 accessToken 재발급에 쓰기 위해 따로 저장합니다.
    tokenStore.setAccessToken(response.data.accessToken);
    tokenStore.setRefreshToken(response.data.refreshToken);
    setAccessToken(response.data.accessToken);
    setMessageBox(JSON.stringify(response.data));
  }
  const handleLogout = async () => {
    try{
      // axios.post는 세번째 인수로 객체를 전달하여 config로 설정할 수 있습니다. 이 객체에 headers를 포함시킵니다.
      // 로그아웃 API도 "현재 로그인한 사용자"를 알아야 하므로 accessToken을 Bearer 형식으로 보냅니다.
      const response = await axios.post(BASE_URL+'/auth/logout', null, {
        headers : {
          Authorization : `Bearer ${tokenStore.getAccessToken()}`
        }
      });

      console.log(response.data);
      tokenStore.clearToken();
      setAccessToken('');
      setMessageBox(JSON.stringify(response.data.message));
    }catch(err){
      console.log(err);
      setMessageBox(JSON.stringify(err.response.data));
    }
  }
  const handleMe = async () => {
    const url = `${BASE_URL}/auth/me` //get 방식으로 호출
    const response = await axios.get(url,{
      headers:{
        // Spring Security의 JwtAuthenticationFilter가 이 헤더를 읽어 현재 사용자를 복원합니다.
        Authorization : `Bearer ${accessToken}`
      }
    })
    setMessageBox(JSON.stringify(response.data));
  }

  const handleAllPost = async () => {
    // SecurityConfig에서 GET /api/posts/**는 permitAll로 열어 두었기 때문에 토큰 없이 호출합니다.
    const reponse = await axios.get(`${BASE_URL}/api/posts`);
    setMessageBox(JSON.stringify(reponse.data));
  }

  const handleSendPost = async () => {
    const title = "프론트에서 보낸 제목";
    const author = "작성자";
    const createdAt = '2026-06-23 11:25:45';
    try {
      const response = await axios.post(`${BASE_URL}/api/posts`,{title,author,createdAt},{
        headers:{
          // 게시글 등록은 인증된 사용자만 가능하므로 accessToken을 같이 보냅니다.
          Authorization : `Bearer ${accessToken}`
        }
      });
      setMessageBox(JSON.stringify(response.data));      
    } catch (error) {
      console.log(error);
    }
  }

  return (<div className="container">
    <header>
      <h1>JWT API 테스트 패널</h1>
      <div className='head-content'>{accessToken == '' ? "Access Token 출력 영역" : accessToken}</div>

    </header>
    <div className='result-message-box'>
      <h2>응답결과</h2>
      <div className='message-content'>{messageBox}</div>  
    </div>
    <main>
      <section className="card">
        <h2>회원가입</h2>
        <div className='form-group'>
          <label htmlFor="reg-username">아이디</label>
          <input type="text" id="reg-username" ref={usernameRef}/>
        </div>
        <div className='form-group'>
          <label htmlFor="reg-password">패스워드</label>
          <input type="password" id="reg-password" ref={passwordRef}/>
        </div>
        <div className='form-group'>
          <label htmlFor="reg-email">이메일</label>
          <input type="text" id="reg-email" ref={emailRef}/>
        </div>
        <div className='btn-group'>
          {/* onClick에 함수 자체를 전달하면 클릭 시점에 React가 handleRegister를 실행합니다. */}
          <button id="register" onClick={handleRegister}>회원가입</button>
        </div>
      </section>
       <section className="card">
          <h2>로그인</h2>
          <div className='form-group'>
            <label htmlFor="log-username">아이디</label>
            <input type="text" id="log-username" ref={loginUserName}/>
          </div>
          <div className='form-group'>
            <label htmlFor="log-password">패스워드</label>
            <input type="password" id="log-password" ref={loginPassword}/>
          </div>
          <div className='btn-group'>
            {/* 로그인 버튼도 같은 방식으로 미완성 handleLogin 함수와 연결되어 있습니다. */}
            <button id="login" onClick={handleLogin}>로그인</button>
            <button id="logout" onClick={handleLogout}>로그아웃</button>
            <button onClick={() => {
              // 백엔드의 OAuth2 시작 URL입니다. 이 주소로 이동하면 Spring Security가 Google 로그인 페이지로 보냅니다.
              window.location.href=`${BASE_URL}/oauth2/authorization/google`
            }}>구글 로그인</button>
          </div>
           
       </section>
       <section className="card">
        <h2>로그인한 회원의 정보</h2>
        {accessToken != '' ? <button onClick={handleMe}>내 정보 확인</button> : <div>로그인한 회원의 정보는 로그인한 후 확인할 수 있습니다.</div> }
       </section>
       <section className='card'>
        <h2>게시글</h2>
        <button onClick={handleAllPost}>전체 게시글 조회</button>
        <button onClick={handleSendPost}>게시글 등록 테스트</button>
       </section>
    </main>

  </div>
  );
}

export default App;
