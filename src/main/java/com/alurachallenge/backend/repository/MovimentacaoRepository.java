package com.alurachallenge.backend.model.repository;

import com.alurachallenge.backend.model.Movimentacao;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByDataBetween(LocalDate dataInicio, LocalDate dataFim, Sort sort);
}
