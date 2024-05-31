package com.se.its.domain.project.domain.repository;

import com.se.its.domain.member.domain.Member;
import com.se.its.domain.project.domain.Project;
import com.se.its.domain.project.domain.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    List<ProjectMember> findByProjectId(Long ProjectId);

    boolean existsByMemberIdAndProjectId(Long memberId, Long projectId);

    List<ProjectMember> findByMemberId(Long memberId);

    Optional<ProjectMember> findByProjectIdAndMemberId(Long projectId, Long memberId);
}
