package com.spring.problem03;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ProblemMain {

    public static void main(String[] args) {

        /**
         * TODO 4: AnnotationConfigApplicationContext 를 사용해 컨테이너를 생성하세요.
         *         AppConfig.class 를 생성자 인자로 전달합니다.
         *
         *         힌트:
         *           AnnotationConfigApplicationContext context =
         *               new AnnotationConfigApplicationContext(???.class);
         */
        // TODO 4: context 생성 코드 작성
        AnnotationConfigApplicationContext context = 
        		new AnnotationConfigApplicationContext(AppConfig.class); // 이 줄을 수정하세요

        /**
         * TODO 5: context 에서 "memberService" Bean을 가져와
         *         register("홍길동") 을 호출하세요.
         */
        // TODO 5: getBean 후 register 호출
        MemberService service = context.getBean(MemberService.class);
        service.register("홍길동");

        System.out.println("\n▶ 컨테이너 종료");
        if (context != null) context.close();
    }
}
