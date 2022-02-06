package com.alurachallenge.backend.service;

import com.alurachallenge.backend.dto.output.ResumoCategoria;
import com.alurachallenge.backend.dto.output.ResumoDoMes;
import com.alurachallenge.backend.model.Categoria;
import com.alurachallenge.backend.model.Despesa;
import com.alurachallenge.backend.model.Frequencia;
import com.alurachallenge.backend.model.Receita;
import com.alurachallenge.backend.repository.DespesaRepository;
import com.alurachallenge.backend.repository.ReceitaRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResumoServiceTest {

    @Mock
    ReceitaRepository receitaRepository;

    @Mock
    DespesaRepository despesaRepository;

    @InjectMocks
    ResumoService resumoService;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void shouldBuildResumoCorrectly() {
        when(receitaRepository.save(any(Receita.class))).thenReturn(new Receita());
        when(despesaRepository.save(any(Despesa.class))).thenReturn(new Despesa());

        Receita receita = new Receita();
        receita.setId(1L);
        receita.setData(LocalDate.of(2022, 1, 1));
        receita.setValor(BigDecimal.TEN);
        receita.setFrequencia(Frequencia.FIXA);

        Despesa despesa = new Despesa();
        despesa.setId(2L);
        despesa.setData(LocalDate.of(2022, 1, 1));
        despesa.setValor(BigDecimal.ONE);
        despesa.setFrequencia(Frequencia.FIXA);
        despesa.setCategoria(Categoria.ALIMENTACAO);

        receitaRepository.save(receita);
        despesaRepository.save(despesa);

        BigDecimal totalReceitasExpected = receita.getValor();
        BigDecimal totalDespesasExpected = despesa.getValor();
        BigDecimal saldoExpected = totalReceitasExpected.subtract(totalDespesasExpected);

        List<ResumoCategoria> resumoCategoria = new ArrayList<>();

        when(receitaRepository.findTotalByDataBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(BigDecimal.TEN);
        when(despesaRepository.findTotalByDataBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(BigDecimal.ONE);
        when(despesaRepository.findCategoriaAndTotalByDataBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(resumoCategoria);

        ResumoDoMes resumoDoMes = resumoService.toResumo(2022, 1);

        assertThat(resumoDoMes.getTotalReceitas()).isSameAs(totalReceitasExpected);
        assertThat(resumoDoMes.getTotalDespesas()).isSameAs(totalDespesasExpected);
        assertThat(resumoDoMes.getTotalSaldo()).isSameAs(saldoExpected);
    }
}
