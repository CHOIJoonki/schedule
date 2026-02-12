package com.example.demo.controller;

import com.example.demo.dto.ScheduleDetailResponseDto;
import com.example.demo.dto.SchedulePageResponseDto;
import com.example.demo.dto.ScheduleRequestDto;
import com.example.demo.dto.ScheduleResponseDto;
import com.example.demo.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleRequestDto requestDto, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<Page<SchedulePageResponseDto>> getSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<SchedulePageResponseDto> responseDtos = scheduleService.getSchedules(page, size);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDetailResponseDto> getSchedule(@PathVariable Long id) {
        ScheduleDetailResponseDto responseDto = scheduleService.getSchedule(id);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleRequestDto requestDto, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        ScheduleResponseDto responseDto = scheduleService.updateSchedule(id, requestDto, userId);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        scheduleService.deleteSchedule(id, userId);
        return ResponseEntity.ok().build();
    }
}