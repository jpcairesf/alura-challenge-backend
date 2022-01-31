package com.alurachallenge.backend.dto;

import com.alurachallenge.backend.model.Categoria;
import com.alurachallenge.backend.model.Despesa;
import com.alurachallenge.backend.model.Frequencia;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DespesaDtoInput {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @NotBlank
    private String frequencia;

    private String descricao;

    @Pattern(regexp = "^\\d+(\\.\\d{2})$")
    @NotBlank
    private String valor;

    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$")
    @NotBlank
    private String data;

    @NotBlank
    private String categoria;

    public void update(Despesa despesa) {
        despesa.setData(LocalDate.parse(this.data, formatter));
        despesa.setDescricao(this.descricao);
        despesa.setFrequencia(Frequencia.valueOf(this.frequencia.toUpperCase()));
        despesa.setValor(new BigDecimal(this.valor));
        despesa.setCategoria(Categoria.valueOf(this.categoria.toUpperCase()));
    }

    public Despesa toDespesa() {
        Despesa despesa = new Despesa();
        update(despesa);
        return despesa;
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
