package com.alurachallenge.backend.dto.output;

import com.alurachallenge.backend.model.Categoria;

import java.math.BigDecimal;

public interface ResumoCategoria {

    Categoria getCategoria();

    BigDecimal getTotal();
}
