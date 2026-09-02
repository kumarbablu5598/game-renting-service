package com.vromo.gamerentingservice.repository;

import com.vromo.gamerentingservice.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByGameId(Long gameId);

    long countByMemberId(Long memberId);
    Optional<Loan> findByGameId(Long gameId);
}
