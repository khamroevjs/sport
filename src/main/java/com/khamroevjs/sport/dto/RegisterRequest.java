package com.khamroevjs.sport.dto;

import com.khamroevjs.sport.model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(@NotBlank
                              String username,

                              @Size(min = 6)
                              @NotBlank
                              String password,

                              @NotNull
                              UserRole role) {
}
