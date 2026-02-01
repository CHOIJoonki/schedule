package com.example.demo.controller;

import com.example.demo.dto.ScheduleRequestDto;
import com.example.demo.dto.ScheduleResponseDto;
import com.example.demo.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules(@RequestParam(required = false) String author) {
        List<ScheduleResponseDto> responseDtos = scheduleService.getSchedules(author);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id) {
        ScheduleResponseDto responseDto = scheduleService.getSchedule(id);
        return ResponseEntity.ok(responseDto);
    }
}