import axiosInstance from "./axiosInstance";

export const postApi = {
  getPage : (page, keyword, size) => axiosInstance.get('/api/posts',{
    params : {
        page:page,
        keyword : keyword,
        size : size
    }
  }) ,

};
