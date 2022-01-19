package com.alurachallenge.backend.dto;

import com.alurachallenge.backend.model.Categoria;
import com.alurachallenge.backend.model.Despesa;
import com.alurachallenge.backend.model.Frequencia;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DespesaDto {

    private String frequencia;

    private String descricao;

    @Pattern(regexp = "^\\d+(\\.\\d{2})$")
    @NotNull
    private String valor;

    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$")
    @NotNull
    private String data;

    @NotNull
    private String categoria;

    public Despesa toDespesa() {
        Despesa despesa = new Despesa();
        despesa.setData(LocalDate.parse(this.data));
        despesa.setDescricao(this.descricao);
        despesa.setFrequencia(Frequencia.valueOf(this.frequencia));
        despesa.setValor(new BigDecimal(this.valor));
        despesa.setCategoria(Categoria.valueOf(categoria));
        return despesa;
    }
}
