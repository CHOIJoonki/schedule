package com.example.demo.service;

import com.example.demo.dto.CommentRequestDto;
import com.example.demo.dto.CommentResponseDto;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Schedule;
import com.example.demo.entity.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Schedule schedule = scheduleRepository.findById(requestDto.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        int commentCount = commentRepository.countBySchedule_ScheduleId(requestDto.getScheduleId());
        if (commentCount >= 10) {
            throw new IllegalArgumentException("하나의 일정에는 댓글을 10개까지만 작성할 수 있습니다.");
        }

        Comment comment = new Comment(requestDto.getContent(), user, schedule);
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment);
    }

    public List<CommentResponseDto> getComments(Long scheduleId) {
        scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        return commentRepository.findAllBySchedule_ScheduleId(scheduleId)
                .stream().map(CommentResponseDto::new).toList();
    }
}