package com.alurachallenge.backend.dto.output;

import com.alurachallenge.backend.model.Receita;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReceitaDtoOutput {

    private Long id;

    private String frequencia;

    private String descricao;

    private BigDecimal valor;

    private LocalDate data;

    public ReceitaDtoOutput(Receita receita) {
        this.id = receita.getId();
        this.frequencia = receita.getFrequencia().toString();
        this.descricao = receita.getDescricao();
        this.valor = receita.getValor();
        this.data = receita.getData();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
