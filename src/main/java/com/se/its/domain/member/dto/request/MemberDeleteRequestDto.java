package com.se.its.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberDeleteRequestDto {
    private Long id;
}
