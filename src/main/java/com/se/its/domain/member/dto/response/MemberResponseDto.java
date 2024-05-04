package com.se.its.domain.member.dto.response;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class MemberResponseDto {

    private Long id;

    private String email;

    private String password;

}
