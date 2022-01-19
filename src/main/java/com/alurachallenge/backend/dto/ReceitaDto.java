package com.alurachallenge.backend.dto;

import com.alurachallenge.backend.model.Categoria;
import com.alurachallenge.backend.model.Despesa;
import com.alurachallenge.backend.model.Frequencia;
import com.alurachallenge.backend.model.Receita;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ReceitaDto {

    private String frequencia;

    private String descricao;

    @Pattern(regexp = "^\\d+(\\.\\d{2})$")
    @NotNull
    private String valor;

    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$")
    @NotNull
    private String data;

    public Receita toReceita() {
        Receita receita = new Receita();
        receita.setData(LocalDate.parse(this.data));
        receita.setDescricao(this.descricao);
        receita.setFrequencia(Frequencia.valueOf(this.frequencia));
        receita.setValor(new BigDecimal(this.valor));
        return receita;
    }
}
