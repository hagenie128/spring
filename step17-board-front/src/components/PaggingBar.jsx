/**
 * [6/26 진도] 페이징 컴포넌트
 * 백엔드 PaggingVO의 startPageOfPageGroup ~ endPageOfPageGroup 범위를 버튼으로 표시
 * onPageChange(페이지번호)를 부모(PostListPage)에 전달해 목록을 다시 조회한다
 */
export default ({ pagging, onPageChange } ) => {

  const pageNumbers = [];
  for(let i = pagging.startPageOfPageGroup ; i <= pagging.endPageOfPageGroup; i++)
    pageNumbers.push(i);

  return <ul>
    <li>
      <button disabled={!pagging.priviousPageGroup} onClick={() => onPageChange(pagging.startPageOfPageGroup - 1)}>◀◀</button>
    </li>
    {
      pageNumbers.map(item => <li key={item}>
        <button onClick={() => onPageChange(item)} disabled={item === pagging.currentPage}>{item}</button>
      </li>)
    }
    <li>
      <button disabled={!pagging.nextPageGroup} onClick={() => onPageChange(pagging.endPageOfPageGroup + 1)}>▶▶</button>
    </li>
  </ul>
}