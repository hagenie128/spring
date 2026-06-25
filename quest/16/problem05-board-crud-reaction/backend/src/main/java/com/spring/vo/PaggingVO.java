package com.spring.vo;

/**
 * 게시판 하단에 페이지 번호(1, 2, 3, 4, 5 ...)를 보여주기 위해
 * 필요한 계산을 담당하는 클래스입니다.
 */
public class PaggingVO {
	// 전체 게시글 개수
	private int count;
	// 현재 사용자가 보고 있는 페이지 번호
	private int currentPage;
	// 한 화면(페이지)에 보여줄 게시글 개수 (30개 고정)
	private final int PAGE_CONTENT_COUNT = 30;
	// 게시판 하단에 한 번에 보여줄 페이지 번호 개수 (예: 1~5, 6~10 -> 5개 고정)
	private final int PAGE_GROUP_COUNT = 5;

	// 생성자: 객체를 만들 때 전체 글 개수와 현재 페이지 번호를 받습니다.
	public PaggingVO(int count, int currentPage) {
		this.count = count;
		this.currentPage = currentPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 전체 페이지 개수를 계산합니다.
	 * (전체 게시글 수 / 한 페이지당 글 수) + (나머지가 있으면 페이지 1개 추가)
	 */
	public int getTotalPage() {
		return count / PAGE_CONTENT_COUNT + (count % PAGE_CONTENT_COUNT != 0 ? 1 : 0);
	}

	/**
	 * 전체 페이지 그룹(묶음) 개수를 계산합니다.
	 * 예: 총 12페이지면 (1~5), (6~10), (11~12) -> 총 3그룹
	 */
	public int getTotalPageGroup() {
		return getTotalPage() / PAGE_GROUP_COUNT + (getTotalPage() % PAGE_GROUP_COUNT != 0 ? 1 : 0);
	}

	/**
	 * 현재 페이지가 속한 그룹 번호를 계산합니다.
	 * 예: 현재 페이지가 7이면 2번째 그룹(6~10)에 속함
	 */
	public int getCurrentPageGroupNo() {
		return currentPage / PAGE_GROUP_COUNT + (currentPage % PAGE_GROUP_COUNT != 0 ? 1 : 0);
	}

	/**
	 * 현재 페이지 그룹의 '시작' 페이지 번호를 계산합니다.
	 * 예: 2번째 그룹이면 시작은 6
	 */
	public int getStartPageOfPageGroup() {
		return (getCurrentPageGroupNo()-1) * PAGE_GROUP_COUNT + 1;
	}

	/**
	 * 현재 페이지 그룹의 '끝' 페이지 번호를 계산합니다.
	 * 예: 2번째 그룹이면 끝은 10. 단, 실제 전체 페이지가 7까지만 있으면 7을 반환합니다.
	 */
	public int getEndPageOfPageGroup() {
		return Math.min(getTotalPage(), getCurrentPageGroupNo()*PAGE_GROUP_COUNT);
	}

	/**
	 * [◀] 이전 페이지 그룹으로 가는 링크를 보여줄지 말지 결정합니다.
	 * 첫 번째 그룹(1~5)일 때는 이전 그룹이 없으므로 false를 반환합니다.
	 */
	public boolean isPriviousPageGroup() {
		return getCurrentPageGroupNo() > 1;
	}

	/**
	 * [▶] 다음 페이지 그룹으로 가는 링크를 보여줄지 말지 결정합니다.
	 * 마지막 그룹일 때는 다음 그룹이 없으므로 false를 반환합니다.
	 */
	public boolean isNextPageGroup() {
		return getCurrentPageGroupNo() < getTotalPageGroup();
	}

	@Override
	public String toString() {
		return "PaggingVO [count=" + count + ", currentPage=" + currentPage + ", PAGE_CONTENT_COUNT="
				+ PAGE_CONTENT_COUNT + ", PAGE_GROUP_COUNT=" + PAGE_GROUP_COUNT + ", getCurrentPage()="
				+ getCurrentPage() + ", getTotalPage()=" + getTotalPage() + ", getTotalPageGroup()="
				+ getTotalPageGroup() + ", getCurrentPageGroupNo()=" + getCurrentPageGroupNo()
				+ ", getStartPageOfPageGroup()=" + getStartPageOfPageGroup() + ", getEndPageOfPageGroup()="
				+ getEndPageOfPageGroup() + ", isPriviousPageGroup()=" + isPriviousPageGroup() + ", isNextPageGroup()="
				+ isNextPageGroup() + "]";
	}
	
}





