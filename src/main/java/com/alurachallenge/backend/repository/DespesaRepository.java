package com.alurachallenge.backend.model.repository;

import com.alurachallenge.backend.model.Despesa;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findAll(Sort sort);

    List<Despesa> findByDataBetween(LocalDate dataInicio, LocalDate dataFim, Sort sort);
}
