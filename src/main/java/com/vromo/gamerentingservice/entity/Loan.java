package com.vromo.gamerentingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "loans",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_loan_game",
                        columnNames = "game_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @OneToOne
    @JoinColumn(
            name = "game_id",
            nullable = false,
            unique = true
    )
    private Game game;

    @Column(nullable = false)
    private LocalDateTime loanedAt;

}
