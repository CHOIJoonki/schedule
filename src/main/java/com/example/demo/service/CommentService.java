package com.example.demo.service;

import com.example.demo.dto.CommentRequestDto;
import com.example.demo.dto.CommentResponseDto;
import com.example.demo.entity.Comment;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        validateCommentRequest(requestDto);

        scheduleRepository.findById(requestDto.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        int commentCount = commentRepository.countByScheduleId(requestDto.getScheduleId());
        if (commentCount >= 10) {
            throw new IllegalArgumentException("하나의 일정에는 댓글을 10개까지만 작성할 수 있습니다.");
        }

        Comment comment = new Comment(
                requestDto.getContent(),
                requestDto.getAuthor(),
                requestDto.getPassword(),
                requestDto.getScheduleId()
        );
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment);
    }

    private void validateCommentRequest(CommentRequestDto requestDto) {
        if (requestDto.getContent() == null || requestDto.getContent().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용은 필수값입니다.");
        }
        if (requestDto.getContent().length() > 100) {
            throw new IllegalArgumentException("댓글 내용은 100자 이내로 작성해주세요.");
        }
        if (requestDto.getAuthor() == null || requestDto.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("작성자명은 필수값입니다.");
        }
        if (requestDto.getPassword() == null || requestDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수값입니다.");
        }
    }
}