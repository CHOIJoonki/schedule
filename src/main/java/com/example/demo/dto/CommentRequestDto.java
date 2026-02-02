
package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    @NotBlank(message = "댓글 내용은 필수값입니다.")
    @Size(max = 100, message = "댓글 내용은 100자 이내로 작성해주세요.")
    private String content;

    @NotBlank(message = "작성자명은 필수값입니다.")
    private String author;

    @NotBlank(message = "비밀번호는 필수값입니다.")
    private String password;

    @NotNull(message = "일정 ID는 필수값입니다.")
    private Long scheduleId;
}
