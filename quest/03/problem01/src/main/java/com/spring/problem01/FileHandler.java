package com.spring.problem01;

/**
 * 파일 처리 서비스.
 * openDirectory() 는 컨테이너 초기화 시, cleanUp() 은 종료 시 호출되어야 한다.
 */
public class FileHandler {

    private String workDir;

    public FileHandler() {
        System.out.println("[FileHandler] 생성자 호출");
    }

    /** 컨테이너 시작 시 작업 디렉토리를 준비한다. */
    public void openDirectory() {
        System.out.println("[FileHandler] openDirectory() — " + workDir + " 디렉토리 준비 완료");
    }

    /** 컨테이너 종료 시 임시 파일을 삭제한다. */
    public void cleanUp() {
        System.out.println("[FileHandler] cleanUp() — 임시 파일 삭제 완료");
    }

    public void processFile(String filename) {
        System.out.println("[FileHandler] 파일 처리: " + filename);
    }

    public void setWorkDir(String workDir) { this.workDir = workDir; }
    public String getWorkDir()             { return workDir; }
}
