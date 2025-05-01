package com.example.walking.dto.request;

import lombok.Data;

@Data
public class ScheduleRequestDto {
    private String title;
    private String content;
    private Long writerId;
}
