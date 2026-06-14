package com.monocept.Controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.DTO.JwtAuthResponseDto;
import com.monocept.DTO.LoginRequestDto;
import com.monocept.security.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
	
	 private final AuthenticationManager authenticationManager;

	    private final JwtService jwtService;

	    @Operation(summary = "Login and generate JWT token")
	    @PostMapping("/login")
	    public JwtAuthResponseDto login(@Valid @RequestBody LoginRequestDto requestDto) {

	        log.info("Login request received for username: {}", requestDto.getUsername());

	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        requestDto.getUsername(),
	                        requestDto.getPassword()
	                )
	        );

	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

	        String token = jwtService.generateToken(userDetails);

	        log.info("JWT token generated successfully for username: {}", userDetails.getUsername());

	        return new JwtAuthResponseDto(token, "Bearer", userDetails.getUsername());
	    }

}
