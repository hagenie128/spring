export default ({ pagging, onPageChange } ) => {

  const pageNumbers = [];
  if (pagging && pagging.startPageOfPageGroup && pagging.endPageOfPageGroup) {
    for(let i = pagging.startPageOfPageGroup ; i <= pagging.endPageOfPageGroup; i++)
      pageNumbers.push(i);
  }

  return <ul className="pagination">
    <li className="pagination-item">
      <button 
        className="pagination-btn"
        disabled={!pagging || !pagging.priviousPageGroup} 
        onClick={() => onPageChange(pagging.startPageOfPageGroup - 1)}
      >
        ◀
      </button>
    </li>
    {
      pageNumbers.map(item => <li key={item} className="pagination-item">
        <button 
          className={`pagination-btn ${item === pagging.currentPage ? 'active' : ''}`}
          onClick={() => onPageChange(item)} 
          disabled={item === pagging.currentPage}
        >
          {item}
        </button>
      </li>)
    }
    <li className="pagination-item">
      <button 
        className="pagination-btn"
        disabled={!pagging || !pagging.nextPageGroup} 
        onClick={() => onPageChange(pagging.endPageOfPageGroup + 1)}
      >
        ▶
      </button>
    </li>
  </ul>
}