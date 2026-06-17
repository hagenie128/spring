package com.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// TODO backend-33: API 테스트용 샘플 회원과 모집글을 저장하세요.
// 힌트:
//   1. @Component를 유지하면 스프링이 자동으로 이 클래스를 빈으로 등록합니다.
//   2. 필요한 Repository를 생성자로 주입받으세요. (MemberRepository, StudyRecruitRepository 등)
//   3. run() 메서드 안에서 Member와 StudyRecruit 엔티티를 만들어 저장하세요.
//   4. 테스트 계정 예시: username=leader1, nickname=김리더, role=LEADER
//   5. @Transactional을 붙이면 run() 전체가 하나의 트랜잭션으로 처리됩니다.
@Component
public class DataInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) {
        // TODO: 여기에 샘플 데이터를 저장하는 코드를 작성하세요.
    }
}
