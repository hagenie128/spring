package com.spring.context;

/**
 * Java Config(@Configuration)에서 @Bean으로 등록하는 일반 POJO.
 * @Component 없음 — AppConfig.reportService()가 인스턴스를 생성한다.
 */
public class ReportService {

    private final String reportType;

    public ReportService(String reportType) {
        this.reportType = reportType;
        System.out.println("[ReportService] 생성: " + reportType);
    }

    public void generateReport() {
        System.out.println("[ReportService] " + reportType + " 리포트 생성 완료");
    }
}
