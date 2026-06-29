import Quill from "quill";
import "quill/dist/quill.snow.css";
import { useEffect, useRef } from "react";

export default ({ onChange, defaultValue }) => {
  const editorRef = useRef(null);
  const quillInstance = useRef(null);
  const onChangeRef = useRef(onChange);
  const initializedRef = useRef(false);

  onChangeRef.current = onChange;

  // 수업 포인트:
  // Quill은 React의 일반 input처럼 value로 완전히 제어하지 않는다.
  // 그래서 인스턴스 생성과 text-change 이벤트 등록은 최초 1회만 수행해야 한다.
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

  // 수업 포인트:
  // 수정 모드에서 서버 본문을 받아온 뒤 최초 1회만 editor에 밀어 넣는다.
  // 타이핑할 때마다 defaultValue를 다시 반영하면 커서가 끝으로 튄다.
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
