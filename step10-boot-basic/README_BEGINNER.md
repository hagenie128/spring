# Spring Boot 메뉴 관리 시스템 (초보자 가이드)

본 프로젝트(`step10-boot-basic`)는 Spring Boot를 사용하여 아주 기본적인 **메뉴 관리 시스템(CRUD)**을 구현한 예제입니다.

## 1. 프로젝트 핵심 기술
- **Spring Boot 3.5.x**: Java 기반의 웹 애플리케이션 프레임워크입니다.
- **Thymeleaf**: HTML 화면을 동적으로 그리기 위한 템플릿 엔진입니다.
- **Lombok**: 반복적인 Java 코드(Getter/Setter 등)를 자동으로 만들어줍니다.
- **Bean Validation**: 사용자가 입력한 데이터가 올바른지(필수값, 숫자 범위 등) 검증합니다.

## 2. 프로젝트 구조 및 파일 설명

### 📁 Java 소스 코드 (`src/main/java`)
1. **Step10BootBasicApplication.java**: 우리 프로젝트의 "시작 버튼"입니다.
2. **controller/MenuController.java**: 사용자의 클릭이나 데이터 전송을 받아서 어디로 보낼지 결정하는 "안내 데스크" 역할을 합니다.
3. **service/MenuService.java**: 실제 업무 로직(데이터 가공, 수정 로직 등)을 처리하는 "비즈니스 전문가"입니다.
4. **repository/MenuRepository.java**: 데이터를 보관하는 "창고"입니다. (여기서는 메모리 저장소 사용)
5. **dto/MenuDTO.java**: 화면과 서버 사이에 데이터를 주고받는 "전달 상자"입니다.

### 📁 화면 템플릿 (`src/main/resources/templates`)
- **home.html**: 첫 시작 화면입니다.
- **menu/list.html**: 등록된 전체 메뉴 목록을 보여주고 검색, 삭제, 수정 링크를 제공합니다.
- **menu/form.html**: 새로운 메뉴를 등록하거나 기존 메뉴를 수정할 때 사용하는 입력 폼입니다.

### ⚙️ 설정 파일
- **build.gradle**: 프로젝트에 필요한 라이브러리(도구)들을 정의하는 명세서입니다.
- **application.properties**: 서버 포트(8888)나 파일 경로 같은 세부 설정을 관리합니다.

## 3. 핵심 동작 흐름 (CRUD)
1. **Create (등록)**: `form.html`에서 데이터 입력 -> `MenuController.create` 실행 -> `MenuService`를 통해 `MenuRepository`에 저장.
2. **Read (조회)**: `MenuController.list` 호출 -> 저장된 모든 메뉴를 가져와 `list.html`로 뿌려줌.
3. **Update (수정)**: `list.html`의 [수정] 클릭 -> `updateForm`에서 데이터 로드 -> `update` 실행하여 정보 변경.
4. **Delete (삭제)**: `list.html`의 [삭제] 클릭 -> `delete` 메서드가 실행되어 저장소에서 해당 데이터 제거.

## 4. 학습 포인트
- 컨트롤러에서 `@GetMapping`과 `@PostMapping`이 어떻게 다른지 확인해보세요.
- Thymeleaf의 `th:text`, `th:each`, `th:if` 문법이 HTML과 어떻게 어우러지는지 살펴보세요.
- `BindingResult`를 사용하여 잘못된 입력값을 어떻게 걸러내는지 코드를 통해 익혀보세요.
