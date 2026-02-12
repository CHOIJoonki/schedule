package com.example.demo.dto;

import com.example.demo.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulePageResponseDto {

    private String title;
    private String content;
    private int commentCount;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SchedulePageResponseDto(Schedule schedule, int commentCount) {
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.commentCount = commentCount;
        this.username = schedule.getUser().getUsername();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}