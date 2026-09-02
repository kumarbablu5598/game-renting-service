package com.vromo.gamerentingservice.repository;

import com.vromo.gamerentingservice.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {

    boolean existsByTitleAndStudio(String title, String studio);

    Optional<Game> findByTitleAndStudio(String title, String studio);

}
