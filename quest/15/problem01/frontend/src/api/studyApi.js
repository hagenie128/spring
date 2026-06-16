import http from "./http";

// TODO frontend-2: 아래 API 함수들을 Axios로 완성하세요.

export async function fetchStudies(params) {
  throw new Error("TODO frontend-2");
}

export async function fetchStudy(id) {
  throw new Error("TODO frontend-2");
}

export async function createStudy(payload) {
  throw new Error("TODO frontend-2");
}

export async function updateStudy(id, payload) {
  throw new Error("TODO frontend-2");
}

export async function applyStudy(id, payload) {
  throw new Error("TODO frontend-2");
}

export async function acceptApplication(id) {
  throw new Error("TODO frontend-2");
}

export async function rejectApplication(id) {
  throw new Error("TODO frontend-2");
}

export async function toggleReaction(id, payload) {
  throw new Error("TODO frontend-2");
}

export async function uploadMaterial(id, file) {
  throw new Error("TODO frontend-2");
}

export function materialDownloadUrl(id) {
  return `http://localhost:8080/api/materials/${id}/download`;
}
