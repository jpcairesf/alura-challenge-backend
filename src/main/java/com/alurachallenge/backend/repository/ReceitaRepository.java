package com.alurachallenge.backend.model.repository;

import com.alurachallenge.backend.model.Receita;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findAll(Sort sort);

    List<Receita> findByDataBetween(LocalDate dataInicio, LocalDate dataFim, Sort sort);
}
