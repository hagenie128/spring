package com.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.Member;
import com.spring.entity.StudyRecruit;
import com.spring.repository.MemberRepository;
import com.spring.repository.StudyRecruitRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final StudyRecruitRepository studyRecruitRepository;

    public DataInitializer(MemberRepository memberRepository,
                           StudyRecruitRepository studyRecruitRepository) {
        this.memberRepository = memberRepository;
        this.studyRecruitRepository = studyRecruitRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        List<Member> members = createMembers();
        createStudies(members);
    }

    private List<Member> createMembers() {
        List<Member> members = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            Member member = new Member();
            member.setUsername("user" + i);
            member.setPassword("1234");
            member.setNickname("스터디원" + i);
            members.add(member);
        }
        return memberRepository.saveAll(members);
    }

    private List<StudyRecruit> createStudies(List<Member> members) {
        String[] stacks = {"Java, Spring", "React, TypeScript", "SQL, JPA", "Python, FastAPI"};
        List<StudyRecruit> studies = new ArrayList<>();

        for (int i = 1; i <= 24; i++) {
            StudyRecruit study = new StudyRecruit();
            study.setLeader(members.get((i - 1) % members.size()));
            study.setTitle("스프린트 스터디 " + i);
            study.setDescription("함께 공부하고 주간 회고를 남기는 스터디입니다.");
            study.setTechStack(stacks[(i - 1) % stacks.length]);
            study.setMethod(i % 2 == 0 ? "온라인" : "오프라인");
            study.setCapacity(3 + (i % 3));
            study.setViewCount((long) i * 4);
            studies.add(study);
        }
        return studyRecruitRepository.saveAll(studies);
    }
}
