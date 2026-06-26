import { useEffect, useState } from "react";
import { postApi } from "./api/postApi";

/**
 * Step 1 연습용 App.js
 * TODO 3개를 채우면 목록이 화면에 나타난다.
 */
function App() {
  // TODO 1: posts 상태 — useState, 초기값 빈 배열 []
  const [posts, setPosts] = useState(/* ??? */);

  // TODO 2: 마운트 시 API 호출
  useEffect(() => {
    // postApi.getPage(1, '', 20) 호출
    // .then(res => setPosts(res.data.??? ))  ← 목록 배열 키 이름은?
  }, []);

  return (
    <div>
      <h1>게시글 목록 (Step 1)</h1>
      {/* TODO 3: posts.map으로 제목 출력 */}
      <ul>
        {/* {posts.map(item => <li key={???}>{item.???}</li>)} */}
      </ul>
    </div>
  );
}

export default App;
