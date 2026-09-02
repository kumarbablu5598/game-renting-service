package com.vromo.gamerentingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GameResponse {

    private Long id;

    private String title;

    private String studio;

    private List<String> genres;

    private boolean loaned;

    private Long memberId;
}
