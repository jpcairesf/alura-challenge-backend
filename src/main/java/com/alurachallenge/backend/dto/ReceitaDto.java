package com.alurachallenge.backend.dto;

import com.alurachallenge.backend.model.Frequencia;
import com.alurachallenge.backend.model.Receita;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReceitaDto {

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

    public void update(Receita receita) {
        receita.setData(LocalDate.parse(this.data, formatter));
        receita.setDescricao(this.descricao);
        receita.setFrequencia(Frequencia.valueOf(this.frequencia.toUpperCase()));
        receita.setValor(new BigDecimal(this.valor));
    }

    public Receita toReceita() {
        Receita receita = new Receita();
        update(receita);
        return receita;
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
}
