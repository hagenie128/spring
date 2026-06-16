package com.spring.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.spring.entity.ApplicationStatus;
import com.spring.entity.ReactionType;
import com.spring.entity.RecruitStatus;

public class StudyDtos {

    public record StudyListResponse(
            Long id,
            String title,
            String techStack,
            String method,
            Integer capacity,
            Integer acceptedCount,
            RecruitStatus status,
            Long viewCount,
            String leaderNickname
    ) {
    }

    public record StudyDetailResponse(
            Long id,
            String title,
            String description,
            String techStack,
            String method,
            Integer capacity,
            Integer acceptedCount,
            RecruitStatus status,
            Long viewCount,
            String leaderNickname,
            List<ApplicationResponse> waitingApplications,
            List<ApplicationResponse> acceptedApplications,
            long likeCount,
            long dislikeCount,
            List<MaterialResponse> materials
    ) {
    }

    public record StudyCreateRequest(
            Long leaderId,
            String title,
            String description,
            String techStack,
            String method,
            Integer capacity
    ) {
    }

    public record StudyUpdateRequest(
            String title,
            String description,
            String techStack,
            String method,
            Integer capacity
    ) {
    }

    public record ApplicationCreateRequest(Long applicantId, String message) {
    }

    public record ReactionRequest(Long memberId, ReactionType type) {
    }

    public record ApplicationResponse(
            Long id,
            String applicantNickname,
            String message,
            ApplicationStatus status,
            LocalDateTime createdAt
    ) {
    }

    public record MaterialResponse(Long id, String originalName, Long fileSize) {
    }
}
