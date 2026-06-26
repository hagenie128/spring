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