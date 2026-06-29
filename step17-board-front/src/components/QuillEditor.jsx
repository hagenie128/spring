import Quill from "quill";
import "quill/dist/quill.snow.css";
import { useEffect, useRef } from "react";

export default ({ onChange, defaultValue }) => {
  const editorRef = useRef(null);
  const quillInstance = useRef(null);
  const onChangeRef = useRef(onChange);
  const initializedRef = useRef(false);

  onChangeRef.current = onChange;

  // Quill 인스턴스는 최초 1회만 생성
  useEffect(() => {
    if (editorRef.current && !quillInstance.current) {
      quillInstance.current = new Quill(editorRef.current, {
        theme: "snow",
        modules: {
          toolbar: [
            ["bold", "italic", "underline", "strike"],
            ["blockquote", "code-block"],
            ["link", "image", "video", "formula"],
            [{ header: 1 }, { header: 2 }],
            [{ list: "ordered" }, { list: "bullet" }, { list: "check" }],
            [{ script: "sub" }, { script: "super" }],
            [{ indent: "-1" }, { indent: "+1" }],
            [{ direction: "rtl" }],
            [{ size: ["small", false, "large", "huge"] }],
            [{ header: [1, 2, 3, 4, 5, 6, false] }],
            [{ color: [] }, { background: [] }],
            [{ font: [] }],
            [{ align: [] }],
            ["clean"],
          ],
        },
      });

      quillInstance.current.on("text-change", () => {
        if (onChangeRef.current) {
          onChangeRef.current(quillInstance.current.getSemanticHTML());
        }
      });
    }
  }, []);

  // 수정 모드: API에서 본문을 받아온 뒤 최초 1회만 반영
  useEffect(() => {
    if (
      quillInstance.current &&
      defaultValue &&
      !initializedRef.current
    ) {
      quillInstance.current.root.innerHTML = defaultValue;
      initializedRef.current = true;
    }
  }, [defaultValue]);

  return (
    <div style={{ margin: "50px" }}>
      <div ref={editorRef} style={{ height: "500px" }}></div>
    </div>
  );
};
