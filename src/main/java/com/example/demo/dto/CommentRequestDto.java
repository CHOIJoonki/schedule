package com.example.demo.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String content;
    private String author;
    private String password;
    private Long scheduleId;
}