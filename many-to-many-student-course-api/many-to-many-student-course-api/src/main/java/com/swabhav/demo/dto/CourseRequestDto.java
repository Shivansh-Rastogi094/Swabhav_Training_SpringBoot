package com.swabhav.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CourseRequestDto {

    @JsonProperty("course_name")
    @NotBlank(message = "Course name is required")
    private String courseName;

    @JsonProperty("course_code")
    @NotBlank(message = "Course code is required")
    private String courseCode;

    @NotNull(message = "Fees is required")
    @Positive(message = "Fees must be greater than 0")
    private Double fees;
}
