package com.vromo.gamerentingservice.controller;

import com.vromo.gamerentingservice.dto.CreateGameRequest;
import com.vromo.gamerentingservice.dto.GameResponse;
import com.vromo.gamerentingservice.dto.LoanResponse;
import com.vromo.gamerentingservice.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;



// ADD GAME

    @PostMapping
    public ResponseEntity<GameResponse> addGame(
            @Valid @RequestBody CreateGameRequest request
    ) {

        GameResponse response = gameService.addGame(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }



// REMOVE GAME

    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> removeGame(
            @PathVariable Long gameId
    ) {

        gameService.removeGame(gameId);

        return ResponseEntity
                .noContent()
                .build();
    }



// LOAN GAME

    @PostMapping("/{gameId}/loan")
    public ResponseEntity<LoanResponse> loanGame(
            @PathVariable Long gameId,
            @RequestHeader(value = "X-Member-Id") Long memberId
    ) {

        LoanResponse response =
                gameService.loanGame(gameId, memberId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


// RETURN GAME

    @PostMapping("/{gameId}/return")
    public ResponseEntity<Void> returnGame(
            @PathVariable Long gameId,
            @RequestHeader(value = "X-Member-Id") Long memberId
    ) {

        gameService.returnGame(gameId, memberId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
