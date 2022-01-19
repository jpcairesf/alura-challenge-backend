package com.alurachallenge.backend.model.repository;

import com.alurachallenge.backend.model.Despesa;
import com.alurachallenge.backend.model.Movimentacao;
import com.alurachallenge.backend.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    @Query("select movimentacao m where m.tipo = despesa")
    List<Despesa> findDespesas();

    @Query("select movimentacao m where m.tipo = receita")
    List<Receita> findReceitas();
}
