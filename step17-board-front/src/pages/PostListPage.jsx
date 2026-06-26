import { useCallback, useEffect, useState } from "react"
import { postApi } from "../api/postApi";
import PaggingBar from "../components/PaggingBar";
import { Link, useNavigate } from "react-router-dom";

/**
 * [6/26 진도] 게시글 목록 페이지
 * 1. useEffect로 마운트 시 1페이지 조회 (GET /api/posts)
 * 2. PaggingBar에서 페이지 클릭 시 fetchPostData로 재조회
 * 3. Link로 상세 페이지 이동 (/posts/{bno})
 */
export default () => {
  const [posts, setPosts] = useState([]);
  const [pagging, setPagging] = useState({});
  const navigate = useNavigate();

  // [진도] 최초 로딩 — page=1, size=20, keyword=''(전체)
  useEffect(() => {
    postApi.getPage(1, '', 20)
    .then(response => {
        setPosts(response.data.list);
        setPagging(response.data.pagging);
        console.log(response.data);
      }
    )
  },[]);
  // [진도] 페이징 바 클릭 시 해당 페이지 데이터 다시 요청
  const fetchPostData = useCallback((pageNo) => {
    postApi.getPage(pageNo, '', 20)
    .then(response => {
        setPosts(response.data.list);
        setPagging(response.data.pagging);
        console.log(response.data);
      }
    )
  },[]);


  return <div className="container">
    <h2>게시글 목록</h2>
    <table>
      <thead>
        <tr>
          <th>글번호</th>
          <th>제목</th>
          <th>작성자</th>
          <th>작성일</th>
          <th>조회수</th>
          <th>좋아요</th>
          <th>싫어요</th>
        </tr>
      </thead>
      <tbody>
        {
          posts && posts.map(item => <tr key={item.bno}>
            <td>{item.bno}</td>
            <td>
              {/* [진도] react-router-dom Link — 클릭 시 SPA 방식으로 상세 이동 */}
              <Link to={`/posts/${item.bno}`}>{item.title}</Link>
            </td>
            <td>{item.nickname}</td>
            <td>{item.writeUpdateDate}</td>
            <td>{item.bcount}</td>
            <td>{item.blike}</td>
            <td>{item.bhate}</td>
          </tr>)

        }

      </tbody>
      <tfoot>
        <tr>
          <td colSpan={7}><PaggingBar pagging={pagging} onPageChange={fetchPostData}/> </td>
        </tr>
      </tfoot>
    </table>
  </div>
}
