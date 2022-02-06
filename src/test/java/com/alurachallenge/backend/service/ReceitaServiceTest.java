package com.alurachallenge.backend.service;

import com.alurachallenge.backend.dto.input.ReceitaDtoInput;
import com.alurachallenge.backend.dto.output.ReceitaDtoOutput;
import com.alurachallenge.backend.model.Frequencia;
import com.alurachallenge.backend.model.Receita;
import com.alurachallenge.backend.repository.ReceitaRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReceitaServiceTest {

    @Mock
    ReceitaRepository receitaRepository;

    @InjectMocks
    ReceitaService receitaService;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void shouldSaveCorrectly() {
        ReceitaDtoInput receita1 = new ReceitaDtoInput();
        receita1.setData(LocalDate.of(2022, 1, 1));
        receita1.setValor(BigDecimal.ONE);
        receita1.setFrequencia("FIXA");

        when(receitaRepository.save(any(Receita.class))).thenReturn(new Receita());

        ReceitaDtoOutput receitaSaved = receitaService.save(receita1);

        verify(receitaRepository).save(any(Receita.class));
        assertThat(receitaSaved.getValor()).isSameAs(receita1.getValor());
        assertThat(receitaSaved.getData()).isSameAs(receita1.getData());
        assertThat(receitaSaved.getDescricao()).isSameAs(receita1.getDescricao());
        assertThat(receitaSaved.getFrequencia()).isSameAs(receita1.getFrequencia());
    }

    @Test
    public void shouldReturnAllReceitas() {
        ReceitaDtoInput receita1 = new ReceitaDtoInput();
        receita1.setData(LocalDate.of(2022, 1, 1));
        receita1.setValor(BigDecimal.ONE);
        receita1.setFrequencia("FIXA");

        when(receitaRepository.save(any(Receita.class))).thenReturn(new Receita());

        receitaService.save(receita1);

        List<Receita> outputs = new ArrayList();
        Receita receita = new Receita();
        receita.setData(LocalDate.of(2022, 1, 1));
        receita.setValor(BigDecimal.ONE);
        receita.setFrequencia(Frequencia.FIXA);
        outputs.add(receita);

        Sort sort = Sort.by("data").descending().and(Sort.by("valor").descending());
        when(receitaRepository.findByDescricaoContainingIgnoreCase("", sort)).thenReturn(outputs);

        List<ReceitaDtoOutput> expected = receitaService.search(null);

        verify(receitaRepository).findByDescricaoContainingIgnoreCase("", sort);
        assertThat(outputs.size()).isSameAs(expected.size());
        assertThat(outputs.get(0).getData()).isSameAs(expected.get(0).getData());
        assertThat(outputs.get(0).getValor()).isSameAs(expected.get(0).getValor());
        assertThat(outputs.get(0).getDescricao()).isSameAs(expected.get(0).getDescricao());
        assertThat(outputs.get(0).getFrequencia()).isSameAs(Frequencia.valueOf(expected.get(0).getFrequencia()));
    }

    @Test
    public void shouldNotReturnReceitasWithoutDescricao() {
        ReceitaDtoInput receita1 = new ReceitaDtoInput();
        receita1.setDescricao("testando");
        receita1.setData(LocalDate.of(2022, 1, 1));
        receita1.setValor(BigDecimal.ONE);
        receita1.setFrequencia("FIXA");

        ReceitaDtoInput receita2 = new ReceitaDtoInput();
        receita2.setDescricao("nada");
        receita2.setData(LocalDate.of(2022, 1, 1));
        receita2.setValor(BigDecimal.ONE);
        receita2.setFrequencia("FIXA");

        when(receitaRepository.save(any(Receita.class))).thenReturn(new Receita());

        receitaService.save(receita1);
        receitaService.save(receita2);

        List<Receita> outputs = new ArrayList();
        Receita receita = new Receita();
        receita.setDescricao("testando");
        receita.setData(LocalDate.of(2022, 1, 1));
        receita.setValor(BigDecimal.ONE);
        receita.setFrequencia(Frequencia.FIXA);
        outputs.add(receita);

        Sort sort = Sort.by("data").descending().and(Sort.by("valor").descending());
        when(receitaRepository.findByDescricaoContainingIgnoreCase("testando", sort)).thenReturn(outputs);

        List<ReceitaDtoOutput> expected = receitaService.search("testando");

        verify(receitaRepository).findByDescricaoContainingIgnoreCase("testando", sort);
        assertThat(outputs.size()).isSameAs(expected.size());
    }

    @Test
    public void shouldUpdateCorrectly() {
        ReceitaDtoInput receita1 = new ReceitaDtoInput();
        receita1.setData(LocalDate.of(2022, 2, 2));
        receita1.setValor(BigDecimal.TEN);
        receita1.setFrequencia("EVENTUAL");

        when(receitaRepository.save(any(Receita.class))).thenReturn(new Receita());

        java.util.Optional<Receita> optionalReceita = java.util.Optional.ofNullable(new Receita());
        Receita receita = optionalReceita.get();
        receita.setId(1L);
        receita.setData(LocalDate.of(2022, 1, 1));
        receita.setValor(BigDecimal.ONE);
        receita.setFrequencia(Frequencia.FIXA);

        when(receitaRepository.findById(anyLong())).thenReturn(optionalReceita);

        ReceitaDtoOutput receitaUpdated = receitaService.update(receita.getId(), receita1);

        assertThat(receita1.getValor()).isSameAs(receitaUpdated.getValor());
        assertThat(receita1.getDescricao()).isSameAs(receitaUpdated.getDescricao());
        assertThat(receita1.getData()).isSameAs(receitaUpdated.getData());
        assertThat(receita1.getFrequencia()).isSameAs(receitaUpdated.getFrequencia());
    }

    @Test
    public void shouldDeleteCorrectly() {
        ReceitaDtoInput receita1 = new ReceitaDtoInput();
        receita1.setData(LocalDate.of(2022, 2, 2));
        receita1.setValor(BigDecimal.TEN);
        receita1.setFrequencia("EVENTUAL");

        when(receitaRepository.save(any(Receita.class))).thenReturn(new Receita());

        receitaService.save(receita1);

        java.util.Optional<Receita> optionalReceita = java.util.Optional.ofNullable(new Receita());
        Receita receita = optionalReceita.get();
        receita.setId(1L);
        receita.setData(LocalDate.of(2022, 1, 1));
        receita.setValor(BigDecimal.ONE);
        receita.setFrequencia(Frequencia.FIXA);

        when(receitaRepository.findById(anyLong())).thenReturn(optionalReceita);

        receitaService.delete(receita.getId());

        verify(receitaRepository).deleteById(receita.getId());
    }

}
