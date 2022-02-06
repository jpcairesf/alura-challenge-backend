package com.alurachallenge.backend.service;

import com.alurachallenge.backend.dto.input.DespesaDtoInput;
import com.alurachallenge.backend.dto.output.DespesaDtoOutput;
import com.alurachallenge.backend.model.Categoria;
import com.alurachallenge.backend.model.Despesa;
import com.alurachallenge.backend.model.Frequencia;
import com.alurachallenge.backend.repository.DespesaRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DespesaServiceTest {

    @Mock
    DespesaRepository despesaRepository;

    @InjectMocks
    DespesaService despesaService;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void shouldSaveCorrectly() {
        DespesaDtoInput despesa1 = new DespesaDtoInput();
        despesa1.setData(LocalDate.of(2022, 1, 1));
        despesa1.setValor(BigDecimal.ONE);
        despesa1.setFrequencia("FIXA");
        despesa1.setCategoria("MORADIA");

        when(despesaRepository.save(any(Despesa.class))).thenReturn(new Despesa());

        DespesaDtoOutput despesaSaved = despesaService.save(despesa1);

        verify(despesaRepository).save(any(Despesa.class));
        assertThat(despesaSaved.getValor()).isSameAs(despesa1.getValor());
        assertThat(despesaSaved.getData()).isSameAs(despesa1.getData());
        assertThat(despesaSaved.getDescricao()).isSameAs(despesa1.getDescricao());
        assertThat(despesaSaved.getFrequencia()).isSameAs(despesa1.getFrequencia());
        assertThat(despesaSaved.getCategoria()).isSameAs(despesa1.getCategoria());
    }

    @Test
    public void shouldReturnAllDespesas() {
        DespesaDtoInput despesa1 = new DespesaDtoInput();
        despesa1.setData(LocalDate.of(2022, 1, 1));
        despesa1.setValor(BigDecimal.ONE);
        despesa1.setFrequencia("FIXA");
        despesa1.setCategoria("MORADIA");

        when(despesaRepository.save(any(Despesa.class))).thenReturn(new Despesa());

        despesaService.save(despesa1);

        List<Despesa> outputs = new ArrayList();
        Despesa despesa = new Despesa();
        despesa.setData(LocalDate.of(2022, 1, 1));
        despesa.setValor(BigDecimal.ONE);
        despesa.setFrequencia(Frequencia.FIXA);
        despesa.setCategoria(Categoria.MORADIA);
        outputs.add(despesa);

        Sort sort = Sort.by("data").descending().and(Sort.by("valor").descending());
        when(despesaRepository.findByDescricaoContainingIgnoreCase("", sort)).thenReturn(outputs);

        List<DespesaDtoOutput> expected = despesaService.search(null);

        verify(despesaRepository).findByDescricaoContainingIgnoreCase("", sort);
        assertThat(outputs.size()).isSameAs(expected.size());
        assertThat(outputs.get(0).getData()).isSameAs(expected.get(0).getData());
        assertThat(outputs.get(0).getValor()).isSameAs(expected.get(0).getValor().negate());
        assertThat(outputs.get(0).getDescricao()).isSameAs(expected.get(0).getDescricao());
        assertThat(outputs.get(0).getFrequencia()).isSameAs(Frequencia.valueOf(expected.get(0).getFrequencia()));
        assertThat(outputs.get(0).getCategoria()).isSameAs(Categoria.valueOf(expected.get(0).getCategoria()));
    }

    @Test
    public void shouldNotReturnDespesasWithoutDescricao() {
        DespesaDtoInput despesa1 = new DespesaDtoInput();
        despesa1.setDescricao("testando");
        despesa1.setData(LocalDate.of(2022, 1, 1));
        despesa1.setValor(BigDecimal.ONE);
        despesa1.setFrequencia("FIXA");
        despesa1.setCategoria("MORADIA");

        DespesaDtoInput despesa2 = new DespesaDtoInput();
        despesa2.setDescricao("nada");
        despesa2.setData(LocalDate.of(2022, 1, 1));
        despesa2.setValor(BigDecimal.ONE);
        despesa2.setFrequencia("FIXA");
        despesa2.setCategoria("MORADIA");

        when(despesaRepository.save(any(Despesa.class))).thenReturn(new Despesa());

        despesaService.save(despesa1);
        despesaService.save(despesa2);

        List<Despesa> outputs = new ArrayList();
        Despesa despesa = new Despesa();
        despesa.setDescricao("testando");
        despesa.setData(LocalDate.of(2022, 1, 1));
        despesa.setValor(BigDecimal.ONE);
        despesa.setFrequencia(Frequencia.FIXA);
        despesa.setCategoria(Categoria.MORADIA);
        outputs.add(despesa);

        Sort sort = Sort.by("data").descending().and(Sort.by("valor").descending());
        when(despesaRepository.findByDescricaoContainingIgnoreCase("testando", sort)).thenReturn(outputs);

        List<DespesaDtoOutput> expected = despesaService.search("testando");

        verify(despesaRepository).findByDescricaoContainingIgnoreCase("testando", sort);
        assertThat(outputs.size()).isSameAs(expected.size());
    }

    @Test
    public void shouldUpdateCorrectly() {
        DespesaDtoInput despesa1 = new DespesaDtoInput();
        despesa1.setData(LocalDate.of(2022, 2, 2));
        despesa1.setValor(BigDecimal.TEN);
        despesa1.setFrequencia("EVENTUAL");
        despesa1.setCategoria("ALIMENTACAO");

        when(despesaRepository.save(any(Despesa.class))).thenReturn(new Despesa());

        Optional<Despesa> optionalDespesa = Optional.ofNullable(new Despesa());
        Despesa despesa = optionalDespesa.get();
        despesa.setId(1L);
        despesa.setData(LocalDate.of(2022, 1, 1));
        despesa.setValor(BigDecimal.ONE);
        despesa.setFrequencia(Frequencia.FIXA);
        despesa.setCategoria(Categoria.ALIMENTACAO);

        when(despesaRepository.findById(anyLong())).thenReturn(optionalDespesa);

        DespesaDtoOutput despesaUpdated = despesaService.update(despesa.getId(), despesa1);

        assertThat(despesa1.getValor()).isSameAs(despesaUpdated.getValor());
        assertThat(despesa1.getDescricao()).isSameAs(despesaUpdated.getDescricao());
        assertThat(despesa1.getData()).isSameAs(despesaUpdated.getData());
        assertThat(despesa1.getFrequencia()).isSameAs(despesaUpdated.getFrequencia());
        assertThat(despesa1.getCategoria()).isSameAs(despesaUpdated.getCategoria());
    }

    @Test
    public void shouldDeleteCorrectly() {
        DespesaDtoInput despesa1 = new DespesaDtoInput();
        despesa1.setData(LocalDate.of(2022, 2, 2));
        despesa1.setValor(BigDecimal.TEN);
        despesa1.setFrequencia("EVENTUAL");
        despesa1.setCategoria("ALIMENTACAO");

        when(despesaRepository.save(any(Despesa.class))).thenReturn(new Despesa());

        despesaService.save(despesa1);

        Optional<Despesa> optionalDespesa = Optional.ofNullable(new Despesa());
        Despesa despesa = optionalDespesa.get();
        despesa.setId(1L);
        despesa.setData(LocalDate.of(2022, 1, 1));
        despesa.setValor(BigDecimal.ONE);
        despesa.setFrequencia(Frequencia.FIXA);
        despesa.setCategoria(Categoria.ALIMENTACAO);

        when(despesaRepository.findById(anyLong())).thenReturn(optionalDespesa);

        despesaService.delete(despesa.getId());

        verify(despesaRepository).deleteById(despesa.getId());
    }
}
