package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    @NotBlank(message = "일정 제목은 필수값입니다.")
    @Size(max = 30, message = "일정 제목은 30자 이내로 작성해주세요.")
    private String title;

    @NotBlank(message = "일정 내용은 필수값입니다.")
    @Size(max = 200, message = "일정 내용은 200자 이내로 작성해주세요.")
    private String content;

    @NotNull(message = "유저 ID는 필수값입니다.")
    private Long userId;

    @NotBlank(message = "비밀번호는 필수값입니다.")
    private String password;
}