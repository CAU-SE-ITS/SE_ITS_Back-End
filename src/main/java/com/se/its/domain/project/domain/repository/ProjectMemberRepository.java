package com.se.its.domain.project.domain.repository;

import com.se.its.domain.member.domain.Member;
import com.se.its.domain.member.domain.Role;
import com.se.its.domain.project.domain.Project;
import com.se.its.domain.project.domain.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    List<ProjectMember> findByProjectIdAndIsDeletedFalse(Long projectId);

    boolean existsByMemberIdAndProjectIdAndIsDeletedFalse(Long memberId, Long projectId);

    Optional<ProjectMember> findByMemberIdAndProjectIdAndIsDeletedFalse(Long memberId, Long projectId);

    List<ProjectMember> findByMemberIdAndIsDeletedFalse(Long memberId);

    Optional<ProjectMember> findByProjectIdAndMemberIdAndIsDeletedFalse(Long projectId, Long memberId);

}
