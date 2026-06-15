package com.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.ApplicationStatus;
import com.spring.entity.Member;
import com.spring.entity.StudyApplication;
import com.spring.entity.StudyRecruit;
import com.spring.repository.MemberRepository;
import com.spring.repository.StudyApplicationRepository;
import com.spring.repository.StudyRecruitRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final StudyRecruitRepository studyRecruitRepository;
    private final StudyApplicationRepository applicationRepository;

    public DataInitializer(MemberRepository memberRepository,
                           StudyRecruitRepository studyRecruitRepository,
                           StudyApplicationRepository applicationRepository) {
        this.memberRepository = memberRepository;
        this.studyRecruitRepository = studyRecruitRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        List<Member> members = createMembers();
        List<StudyRecruit> studies = createStudies(members);
        createApplications(studies, members);
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

    private void createApplications(List<StudyRecruit> studies, List<Member> members) {
        List<StudyApplication> applications = new ArrayList<>();
        for (int i = 0; i < studies.size(); i++) {
            StudyRecruit study = studies.get(i);
            for (int j = 1; j <= 2; j++) {
                Member applicant = members.get((i + j) % members.size());
                StudyApplication application = new StudyApplication();
                application.setStudyRecruit(study);
                application.setApplicant(applicant);
                application.setMessage("열심히 참여하고 싶습니다.");
                if (j == 1 && i % 3 == 0) {
                    application.setStatus(ApplicationStatus.ACCEPTED);
                    study.increaseAcceptedCount();
                }
                applications.add(application);
            }
        }
        applicationRepository.saveAll(applications);
    }
}
