import axiosInstance from "./axiosInstance";

/**
 * [6/26 진도] 게시글 API 레이어
 * 백엔드 BoardController(/api/posts)와 대응한다.
 * GET 요청은 로그인 없이 가능(permitAll), 등록/수정/삭제는 JWT 필요.
 */
export const postApi = {
  /**
   * GET /api/posts — 목록 + 페이징
   * 응답 키: list(게시글 배열), pagging(페이징 정보)
   */
  getPage : (page, keyword, size) => axiosInstance.get('/api/posts',{
    params : {
        page:page,
        keyword : keyword,
        size : size
    }
  }) ,
  /**
   * GET /api/posts/{bno} — 상세 + 댓글 목록
   * 응답 키: board(게시글 1건), commentList(댓글 배열)
   */
  getPost : (bno) => axiosInstance.get(`/api/posts/${bno}`),

};
