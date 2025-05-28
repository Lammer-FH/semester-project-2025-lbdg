package com.lbdg.library_backend.DTOs.responseDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class UserResponseDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("userFullName")
    private String userFullName;
    @JsonProperty("role")
    private String role;
}
