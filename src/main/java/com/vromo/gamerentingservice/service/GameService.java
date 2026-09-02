package com.vromo.gamerentingservice.service;

import com.vromo.gamerentingservice.dto.CreateGameRequest;
import com.vromo.gamerentingservice.dto.GameResponse;
import com.vromo.gamerentingservice.dto.LoanResponse;
import com.vromo.gamerentingservice.entity.Game;
import com.vromo.gamerentingservice.entity.Loan;
import com.vromo.gamerentingservice.exception.BadRequestException;
import com.vromo.gamerentingservice.exception.ResourceNotFoundException;
import com.vromo.gamerentingservice.repository.GameRepository;
import com.vromo.gamerentingservice.repository.LoanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final LoanRepository loanRepository;


// ADD GAME

    public GameResponse addGame(CreateGameRequest request) {

        // Check duplicate game
        boolean gameExists = gameRepository.existsByTitleAndStudio(
                request.getTitle(),
                request.getStudio()
        );

        if (gameExists) {
            throw new BadRequestException(
                    "Game already exists with title: "
                            + request.getTitle()
                            + " and studio: "
                            + request.getStudio()
            );
        }

        // Create game entity
        Game game = new Game();

        game.setTitle(request.getTitle());
        game.setStudio(request.getStudio());
        game.setGenres(request.getGenres());

        // Save game
        Game savedGame = gameRepository.save(game);

        return new GameResponse(
                savedGame.getId(),
                savedGame.getTitle(),
                savedGame.getStudio(),
                savedGame.getGenres(),
                false,
                null
        );
    }


// REMOVE GAME

    @Transactional
    public void removeGame(Long gameId) {

        // Check if game exists
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Game not found with id: " + gameId
                        )
                );

        // Do not allow deleting a loaned game
        if (loanRepository.existsByGameId(gameId)) {
            throw new BadRequestException(
                    "Cannot remove a game that is currently loaned"
            );
        }

        gameRepository.delete(game);
    }


// LOAN GAME

    @Transactional
    public LoanResponse loanGame(Long gameId, Long memberId) {

        // Check if game exists
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Game not found with id: " + gameId
                        )
                );

        // Check if game is already loaned
        if (loanRepository.existsByGameId(gameId)) {
            throw new BadRequestException(
                    "Game is already loaned to another member"
            );
        }

        // Count currently loaned games by this member
        long loanCount = loanRepository.countByMemberId(memberId);

        // Maximum 3 games allowed
        if (loanCount >= 3) {
            throw new BadRequestException(
                    "Member cannot loan more than 3 games"
            );
        }

        // Create loan
        Loan loan = new Loan();

        loan.setGame(game);
        loan.setMemberId(memberId);
        loan.setLoanedAt(LocalDateTime.now());

        // Save loan
        Loan savedLoan = loanRepository.save(loan);

        return new LoanResponse(
                savedLoan.getId(),
                game.getId(),
                savedLoan.getMemberId(),
                savedLoan.getLoanedAt()
        );
    }


// RETURN GAME

    @Transactional
    public void returnGame(Long gameId, Long memberId) {

        // Find the active loan for this game
        Loan loan = loanRepository.findByGameId(gameId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "This game is not currently loaned"
                        )
                );

        // Verify that the same member is returning the game
        if (!loan.getMemberId().equals(memberId)) {
            throw new BadRequestException(
                    "This game was loaned to another member"
            );
        }

        // Delete the loan = game returned
        loanRepository.delete(loan);
    }

}
