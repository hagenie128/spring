import { useState } from "react";
import StudyDetailPage from "./pages/StudyDetailPage.jsx";
import StudyFormPage from "./pages/StudyFormPage.jsx";
import StudyListPage from "./pages/StudyListPage.jsx";

export default function App() {
  const [view, setView] = useState("list");
  const [selectedStudyId, setSelectedStudyId] = useState(null);

  const openDetail = (id) => {
    setSelectedStudyId(id);
    setView("detail");
  };

  return (
    <main className="app-shell">
      <header className="app-header">
        <button className="logo-button" onClick={() => setView("list")}>
          스터디 모집
        </button>
        <button className="primary-button" onClick={() => setView("new")}>
          모집 등록
        </button>
      </header>

      {view === "list" && <StudyListPage onOpenDetail={openDetail} />}
      {view === "detail" && (
        <StudyDetailPage studyId={selectedStudyId} onEdit={() => setView("edit")} />
      )}
      {view === "new" && <StudyFormPage onDone={() => setView("list")} />}
      {view === "edit" && (
        <StudyFormPage studyId={selectedStudyId} onDone={() => openDetail(selectedStudyId)} />
      )}
    </main>
  );
}
