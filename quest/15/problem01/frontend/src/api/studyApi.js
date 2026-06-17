import http from "./http";

// TODO frontend-2: 아래 각 함수 안에서 http 인스턴스로 백엔드 API를 호출하세요.
//   - fetchStudies(params)           GET  /studies?keyword=&status=&page=
//   - fetchStudy(id)                 GET  /studies/{id}
//   - createStudy(payload)           POST /studies
//   - updateStudy(id, payload)       PUT  /studies/{id}
//   - applyStudy(id, payload)        POST /studies/{id}/applications
//   - acceptApplication(id)          PATCH /applications/{id}/accept
//   - rejectApplication(id)          PATCH /applications/{id}/reject
//   - toggleReaction(id, payload)    POST /studies/{id}/reactions
//   - uploadMaterial(id, file)       POST /studies/{id}/materials (multipart/form-data)
//   - materialDownloadUrl(id)        문자열 반환: "http://localhost:8080/api/materials/{id}/download"

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
  throw new Error("TODO frontend-2");
}
