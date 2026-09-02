package com.vromo.gamerentingservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateGameRequest {

    @NotBlank(message = "Title must not be empty")
    private String title;

    @NotBlank(message = "Studio must not be empty")
    private String studio;

    @NotEmpty(message = "At least one genre is required")
    private List<String> genres;

}
