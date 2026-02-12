package com.example.demo.controller;

import com.example.demo.dto.CommentRequestDto;
import com.example.demo.dto.CommentResponseDto;
import com.example.demo.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@Valid @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        CommentResponseDto responseDto = commentService.createComment(requestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@RequestParam Long scheduleId) {
        List<CommentResponseDto> responseDtos = commentService.getComments(scheduleId);
        return ResponseEntity.ok(responseDtos);
    }
}