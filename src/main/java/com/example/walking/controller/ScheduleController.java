package com.example.walking.controller;

import com.example.walking.dto.request.ScheduleRequestDto;
import com.example.walking.dto.response.ScheduleResponseDto;
import com.example.walking.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 일정 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> create(@RequestBody ScheduleRequestDto dto){
        scheduleService.create(dto);
        return Map.of("message", "일정이 생성되었습니다.");
    }

    // 일정 목록 조회
    @GetMapping
    public List<ScheduleResponseDto> list(){
        return scheduleService.findAll();
    }

    // 일정 상세 조회
    @GetMapping("{id}")
    public ScheduleResponseDto detail(@PathVariable Long id){
        return scheduleService.findById(id);

    }

    // 일정 수정
    @PutMapping("/{id}")
    public ScheduleResponseDto update(@PathVariable Long id, @RequestBody ScheduleRequestDto dto){
        return scheduleService.update(id, dto);
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        scheduleService.delete(id);
    }
}
