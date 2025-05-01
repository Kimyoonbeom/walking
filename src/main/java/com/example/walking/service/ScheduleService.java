package com.example.walking.service;

import com.example.walking.dto.request.ScheduleRequestDto;
import com.example.walking.dto.response.CommentResponseDto;
import com.example.walking.dto.response.ScheduleResponseDto;
import com.example.walking.entity.Schedule;
import com.example.walking.repository.CommentRepository;
import com.example.walking.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    // 일정 생성
    public ScheduleResponseDto create(ScheduleRequestDto dto){
        Schedule schedule = new Schedule(dto.getTitle(), dto.getContent(), dto.getWriterId());
        Schedule saved = scheduleRepository.save(schedule);
        return toResponse(saved, 0, null);
    }

    // 일정 목록 조회(댓글 개수 포함)
    public List<ScheduleResponseDto> findAll(){
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(schedule -> toResponse(
                        schedule, commentRepository.countByScheduleId(schedule.getId()).intValue(), null
                ))
                .collect(Collectors.toList());
    }

    private ScheduleResponseDto toResponse(Schedule schedule, int commentCount, List<CommentResponseDto> comments) {
        return ScheduleResponseDto.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .writerId(schedule.getWriterId())
                .createdAt(schedule.getCreatedAt())
                .updatedAt(schedule.getUpdatedAt())
                .commentCount(commentCount)
                .comments(comments)
                .build();
    }

    // 일정 상세 조회
    public ScheduleResponseDto findById(Long id){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException("일정이 없습니다."));
        return toResponse(schedule, commentRepository.countByScheduleId(id).intValue(), null);
    }

    // 일정 수정
    public ScheduleResponseDto update(Long id, ScheduleRequestDto dto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("일정 없음"));
        schedule.update(dto.getTitle(), dto.getContent());
        Schedule updated = scheduleRepository.save(schedule);
        return toResponse(updated, commentRepository.countByScheduleId(id).intValue(), null);
    }

    // 일정 삭제
    public void delete(Long id){
        scheduleRepository.deleteById(id);
    }
}
