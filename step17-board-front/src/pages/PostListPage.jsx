import { useCallback, useEffect, useState } from "react"
import { postApi } from "../api/postApi";
import PaggingBar from "../components/PaggingBar";
import { Link } from "react-router-dom";

export default () => {
  const [posts, setPosts] = useState([]);
  const [pagging, setPagging] = useState({});

  // 게시글 첫번째 페이지 로드해서 출력
  useEffect(() => {
    postApi.getPage(1, '', 20)
    .then(response => {
        setPosts(response.data.list);
        setPagging(response.data.pagging);
        console.log(response.data);
      }
    )
  },[]);
  // 페이지 번호에 해당하는 게시글 조회
  const fetchPostData = useCallback((pageNo) => {
    postApi.getPage(pageNo, '', 20)
    .then(response => {
        setPosts(response.data.list);
        setPagging(response.data.pagging);
        console.log(response.data);
      }
    )
  },[]);


  return <div className="post-list-container">
    <div className="post-list-header">
      <h2 className="post-list-title">게시글 목록</h2>
    </div>
    <div className="table-responsive">
      <table className="post-table">
        <thead>
          <tr>
            <th className="td-center" style={{ width: '80px' }}>글번호</th>
            <th>제목</th>
            <th className="td-center" style={{ width: '120px' }}>작성자</th>
            <th className="td-center" style={{ width: '150px' }}>작성일</th>
            <th className="td-center" style={{ width: '90px' }}>조회수</th>
            <th className="td-center" style={{ width: '90px' }}>좋아요</th>
            <th className="td-center" style={{ width: '90px' }}>싫어요</th>
          </tr>
        </thead>
        <tbody>
          {
            posts && posts.map(item => <tr key={item.bno}>
              <td className="td-center">{item.bno}</td>
              <td><Link to={`/posts/${item.bno}`}>{item.title}</Link></td>
              <td className="td-center">{item.nickname}</td>
              <td className="td-center">{item.writeUpdateDate}</td>
              <td className="td-center">
                <span className="stat-badge stat-count">{item.bcount}</span>
              </td>
              <td className="td-center">
                <span className="stat-badge stat-like">👍 {item.blike}</span>
              </td>
              <td className="td-center">
                <span className="stat-badge stat-hate">👎 {item.bhate}</span>
              </td>
            </tr>)
          }
        </tbody>
        <tfoot>
          <tr>
            <td colSpan={7} className="pagination-cell">
              <PaggingBar pagging={pagging} onPageChange={fetchPostData}/>
            </td>
          </tr>
        </tfoot>
      </table>
    </div>
  </div>
}