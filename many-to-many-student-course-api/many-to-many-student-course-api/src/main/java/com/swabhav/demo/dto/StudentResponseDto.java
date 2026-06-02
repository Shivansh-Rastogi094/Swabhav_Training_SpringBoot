package com.swabhav.demo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StudentResponseDto {

    private Long id;

    @JsonProperty("full_name")
    private String fullName;

    private String email;

    private Integer age;

    private List<CourseResponseDto> courses;
}
