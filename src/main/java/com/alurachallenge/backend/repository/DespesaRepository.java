package com.alurachallenge.backend.repository;

import com.alurachallenge.backend.dto.output.ResumoCategoria;
import com.alurachallenge.backend.model.Despesa;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByDescricaoContainingIgnoreCase(String descricao, Sort sort);

    List<Despesa> findByDataBetween(LocalDate dataInicio, LocalDate dataFim, Sort sort);

    @Query("select coalesce(sum(d.valor), 0) from Despesa d " +
            "where d.data between :dataInicio and :dataFim")
    BigDecimal findTotalByDataBetween(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);

    @Query("select d.categoria as categoria, coalesce(sum(d.valor), 0) as total from Despesa d " +
            "where d.data between :dataInicio and :dataFim " +
            "group by d.categoria " +
            "order by totalGasto desc")
    List<ResumoCategoria> findCategoriaAndTotalByDataBetween(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);
}
