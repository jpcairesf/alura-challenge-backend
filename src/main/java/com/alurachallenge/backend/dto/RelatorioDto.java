package com.alurachallenge.backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RelatorioDto {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$")
    @NotBlank
    private String inicio;

    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$")
    @NotBlank
    private String fim;

    public LocalDate dataInicio() {
        return LocalDate.parse(this.inicio, formatter);
    }

    public LocalDate dataFim() {
        return LocalDate.parse(this.fim, formatter);
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }
}
