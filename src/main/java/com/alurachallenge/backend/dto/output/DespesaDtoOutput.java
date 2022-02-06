package com.alurachallenge.backend.dto.output;

import com.alurachallenge.backend.model.Despesa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DespesaDtoOutput {

    private Long id;

    private String frequencia;

    private String descricao;

    private BigDecimal valor;

    private LocalDate data;

    private String categoria;

    public DespesaDtoOutput(Despesa despesa) {
        this.id = despesa.getId();
        this.frequencia = despesa.getFrequencia().toString();
        this.descricao = despesa.getDescricao();
        this.valor = despesa.getValor().negate();
        this.data = despesa.getData();
        this.categoria = despesa.getCategoria().toString();
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
