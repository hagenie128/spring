import axiosInstance from "./axiosInstance";

export const commentApi = {
  create: (data) => axiosInstance.post("/api/comments", data),
  // data: { bno, content }
};
