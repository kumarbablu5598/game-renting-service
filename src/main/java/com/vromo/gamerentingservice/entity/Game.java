package com.vromo.gamerentingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "games",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_game_title_studio",
                        columnNames = {"title", "studio"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String studio;

    @ElementCollection
    @CollectionTable(
            name = "game_genres",
            joinColumns = @JoinColumn(name = "game_id")
    )
    @Column(name = "genre", nullable = false)
    private List<String> genres = new ArrayList<>();

    @OneToOne(mappedBy = "game")
    private Loan loan;

}
