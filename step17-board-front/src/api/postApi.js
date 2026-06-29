import axiosInstance from "./axiosInstance";

export const postApi = {
  getPage : (page, keyword, size) => axiosInstance.get('/api/posts',{
    params : {
        page:page,
        keyword : keyword,
        size : size
    }
  }) ,
  getPost : (bno) => axiosInstance.get(`/api/posts/${bno}`),
  create : (data) => axiosInstance.post(`/api/posts`,data),
  update : (bno, data) => axiosInstance.patch(`/api/posts/${bno}`,data),
  remove : (bno) => axiosInstance.delete(`/api/posts/${bno}`)
};
