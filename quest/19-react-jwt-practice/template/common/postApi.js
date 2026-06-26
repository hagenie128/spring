import axiosInstance from "./axiosInstance";

export const postApi = {
  // R — Read
  getPage: (page, keyword, size) =>
    axiosInstance.get("/api/posts", {
      params: { page, keyword, size },
    }),
  getPost: (bno) => axiosInstance.get(`/api/posts/${bno}`),

  // C — Create
  create: (data) => axiosInstance.post("/api/posts", data),

  // U — Update
  update: (bno, data) => axiosInstance.patch(`/api/posts/${bno}`, data),

  // D — Delete (delete는 예약어 → remove)
  remove: (bno) => axiosInstance.delete(`/api/posts/${bno}`),
};
