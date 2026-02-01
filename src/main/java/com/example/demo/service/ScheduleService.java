package com.example.demo.service;

import com.example.demo.dto.ScheduleRequestDto;
import com.example.demo.dto.ScheduleResponseDto;
import com.example.demo.entity.Schedule;
import com.example.demo.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(
                requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getAuthor(),
                requestDto.getPassword()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
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

    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));
        return new ScheduleResponseDto(schedule);
    }
}