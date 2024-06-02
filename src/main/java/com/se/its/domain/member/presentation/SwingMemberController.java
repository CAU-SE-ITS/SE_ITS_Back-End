package com.se.its.domain.member.presentation;

import com.se.its.domain.member.application.MemberService;
import com.se.its.domain.member.dto.request.MemberDeleteRequestDto;
import com.se.its.domain.member.dto.request.MemberRoleUpdateRequestDto;
import com.se.its.domain.member.dto.request.MemberSignInRequestDto;
import com.se.its.domain.member.dto.request.MemberSignUpRequestDto;
import com.se.its.domain.member.dto.response.MemberResponseDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SwingMemberController {
    private final MemberService memberService;

    public MemberResponseDto signUp(
            Long id,
            MemberSignUpRequestDto memberSignUpRequestDto
    ) {
        return memberService.signUp(id, memberSignUpRequestDto);
    }

    public MemberResponseDto createAdmin(
            MemberSignUpRequestDto memberSignUpRequestDto
    ) {
        return memberService.adminCreate(memberSignUpRequestDto);
    }

    public MemberResponseDto signIn(
            MemberSignInRequestDto memberSignInRequestDto
    ) {
        return memberService.signIn(memberSignInRequestDto);
    }

    public MemberResponseDto findMemberById(
            Long id
    ) {
        return memberService.findMemberById(id);
    }

    public List<MemberResponseDto> findMembersByAdmin(
            Long id
    ) {
        return memberService.findAllMembers(id);
    }

    public MemberResponseDto deleteMember(
            Long id,
            MemberDeleteRequestDto memberDeleteRequestDto
    ) {
        return memberService.deleteMember(id, memberDeleteRequestDto);
    }


    public MemberResponseDto updateMemberRole(
            Long id,
            MemberRoleUpdateRequestDto memberRoleUpdateRequestDto
    ) {
        return memberService.updateMemberRole(id, memberRoleUpdateRequestDto);
    }

}
