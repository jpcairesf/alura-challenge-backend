package com.alurachallenge.backend.repository;

import com.alurachallenge.backend.model.Receita;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    List<Receita> findByDescricaoContainingIgnoreCase(String descricao, Sort sort);

    List<Receita> findByDataBetween(LocalDate dataInicio, LocalDate dataFim, Sort sort);

    @Query("select coalesce(sum(r.valor), 0) from Receita r " +
            "where r.data between :dataInicio and :dataFim")
    BigDecimal findTotalByDataBetween(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);
}
