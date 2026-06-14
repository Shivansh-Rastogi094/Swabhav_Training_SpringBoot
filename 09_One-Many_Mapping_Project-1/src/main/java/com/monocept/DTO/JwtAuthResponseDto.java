package com.monocept.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponseDto {
	 private String token;

	    private String tokenType;

	    private String username;
}
