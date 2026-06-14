package com.swabhav.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourseResponseDto {

    private Long id;

    @JsonProperty("course_name")
    private String courseName;

    @JsonProperty("course_code")
    private String courseCode;

    private Double fees;
}
