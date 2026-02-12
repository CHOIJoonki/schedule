package com.example.demo.service;

import com.example.demo.dto.CommentResponseDto;
import com.example.demo.dto.ScheduleDetailResponseDto;
import com.example.demo.dto.ScheduleRequestDto;
import com.example.demo.dto.ScheduleResponseDto;
import com.example.demo.entity.Schedule;
import com.example.demo.entity.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Schedule schedule = new Schedule(
                requestDto.getTitle(),
                requestDto.getContent(),
                user
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }

    public List<ScheduleResponseDto> getSchedules(String username) {
        List<Schedule> schedules;
        if (username != null && !username.isEmpty()) {
            schedules = scheduleRepository.findAllByUser_UsernameOrderByUpdatedAtDesc(username);
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
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto, Long userId) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        if (!schedule.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 일정만 수정할 수 있습니다.");
        }

        schedule.update(requestDto.getTitle());
        return new ScheduleResponseDto(schedule);
    }

    public void deleteSchedule(Long id, Long userId) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        if (!schedule.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 일정만 삭제할 수 있습니다.");
        }

        scheduleRepository.delete(schedule);
    }
}