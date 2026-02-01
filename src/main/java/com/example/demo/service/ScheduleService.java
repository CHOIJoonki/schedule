package com.example.demo.service;

import com.example.demo.dto.CommentResponseDto;
import com.example.demo.dto.ScheduleDetailResponseDto;
import com.example.demo.dto.ScheduleRequestDto;
import com.example.demo.dto.ScheduleResponseDto;
import com.example.demo.entity.Schedule;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        validateScheduleRequest(requestDto);

        Schedule schedule = new Schedule(
                requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getAuthor(),
                requestDto.getPassword()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }

    private void validateScheduleRequest(ScheduleRequestDto requestDto) {
        if (requestDto.getTitle() == null || requestDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("일정 제목은 필수값입니다.");
        }
        if (requestDto.getTitle().length() > 30) {
            throw new IllegalArgumentException("일정 제목은 30자 이내로 작성해주세요.");
        }
        if (requestDto.getContent() == null || requestDto.getContent().isEmpty()) {
            throw new IllegalArgumentException("일정 내용은 필수값입니다.");
        }
        if (requestDto.getContent().length() > 200) {
            throw new IllegalArgumentException("일정 내용은 200자 이내로 작성해주세요.");
        }
        if (requestDto.getAuthor() == null || requestDto.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("작성자명은 필수값입니다.");
        }
        if (requestDto.getPassword() == null || requestDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수값입니다.");
        }
    }

    public List<ScheduleResponseDto> getSchedules(String author) {
        List<Schedule> schedules;
        if (author != null && !author.isEmpty()) {
            schedules = scheduleRepository.findAllByAuthorOrderByUpdatedAtDesc(author);
        } else {
            schedules = scheduleRepository.findAllByOrderByUpdatedAtDesc();
        }
        return schedules.stream().map(ScheduleResponseDto::new).toList();
    }

    public ScheduleDetailResponseDto getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));
        List<CommentResponseDto> comments = commentRepository.findAllByScheduleId(id)
                .stream().map(CommentResponseDto::new).toList();
        return new ScheduleDetailResponseDto(schedule, comments);
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        validateUpdateRequest(requestDto);

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        schedule.update(requestDto.getTitle(), requestDto.getAuthor());
        return new ScheduleResponseDto(schedule);
    }

    private void validateUpdateRequest(ScheduleRequestDto requestDto) {
        if (requestDto.getTitle() == null || requestDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("일정 제목은 필수값입니다.");
        }
        if (requestDto.getTitle().length() > 30) {
            throw new IllegalArgumentException("일정 제목은 30자 이내로 작성해주세요.");
        }
        if (requestDto.getAuthor() == null || requestDto.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("작성자명은 필수값입니다.");
        }
        if (requestDto.getPassword() == null || requestDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수값입니다.");
        }
    }

    public void deleteSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        scheduleRepository.delete(schedule);
    }
}