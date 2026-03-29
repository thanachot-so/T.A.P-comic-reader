package com.tapcomiccomicreader.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GenreRequest(
    @NotBlank(message = "genre name cannot be empty")
    @Size(max = 50, message = "genre name is too long")
    String name
) {}
