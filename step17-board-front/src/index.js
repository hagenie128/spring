import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';

/**
 * [6/26 진도] 앱 최상단 구조
 * BrowserRouter → AuthProvider → App 순서로 감싸야
 * 모든 페이지에서 useAuth(), Link, useNavigate를 사용할 수 있다.
 * AuthProvider가 없으면 useAuth() 호출 시 에러가 난다.
 */
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <AuthProvider>
      <App />
    </AuthProvider>
  </BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
