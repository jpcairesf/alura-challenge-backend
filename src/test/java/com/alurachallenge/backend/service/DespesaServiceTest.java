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

import javax.validation.constraints.NotNull;
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
        DespesaDtoInput despesa1 = newDespesaDtoInput(1, 1, BigDecimal.ONE, "testando", "FIXA", "MORADIA");

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
        DespesaDtoInput despesa1 = newDespesaDtoInput(1, 1, BigDecimal.ONE, "testando", "FIXA", "MORADIA");

        when(despesaRepository.save(any(Despesa.class))).thenReturn(new Despesa());

        despesaService.save(despesa1);

        List<Despesa> outputs = new ArrayList();
        Despesa despesa = newDespesa(new Despesa(), 1, 1, BigDecimal.ONE, "testando", "FIXA", "MORADIA");

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
        DespesaDtoInput despesa1 = newDespesaDtoInput(1, 1, BigDecimal.ONE, "testando", "FIXA", "MORADIA");

        DespesaDtoInput despesa2 = newDespesaDtoInput(1, 1, BigDecimal.ONE, "nada", "FIXA", "MORADIA");

        when(despesaRepository.save(any(Despesa.class))).thenReturn(new Despesa());

        despesaService.save(despesa1);
        despesaService.save(despesa2);

        List<Despesa> outputs = new ArrayList();
        Despesa despesa = newDespesa(new Despesa(), 1, 1, BigDecimal.ONE, "testando", "FIXA", "MORADIA");

        outputs.add(despesa);

        Sort sort = Sort.by("data").descending().and(Sort.by("valor").descending());
        when(despesaRepository.findByDescricaoContainingIgnoreCase("testando", sort)).thenReturn(outputs);

        List<DespesaDtoOutput> expected = despesaService.search("testando");

        verify(despesaRepository).findByDescricaoContainingIgnoreCase("testando", sort);
        assertThat(outputs.size()).isSameAs(expected.size());
    }

    @Test
    public void shouldUpdateCorrectly() {
        DespesaDtoInput despesa1 = newDespesaDtoInput(2, 2, BigDecimal.TEN, "testando", "EVENTUAL", "ALIMENTACAO");

        when(despesaRepository.save(any(Despesa.class))).thenReturn(new Despesa());

        Optional<Despesa> optionalDespesa = Optional.ofNullable(new Despesa());
        Despesa despesa = newDespesa(optionalDespesa.get(), 1, 1, BigDecimal.ONE, "testando", "FIXA", "ALIMENTACAO");

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
        Optional<Despesa> optionalDespesa = Optional.ofNullable(new Despesa());
        Despesa despesa = newDespesa(optionalDespesa.get(), 1, 1, BigDecimal.ONE, "testando", "FIXA", "ALIMENTACAO");

        when(despesaRepository.findById(anyLong())).thenReturn(optionalDespesa);

        despesaService.delete(despesa.getId());

        verify(despesaRepository).deleteById(despesa.getId());
    }

    @NotNull
    private DespesaDtoInput newDespesaDtoInput(int month, int day, BigDecimal valor, String descricao, String frequencia, String categoria) {
        DespesaDtoInput input = new DespesaDtoInput();
        input.setData(LocalDate.of(2022, month, day));
        input.setValor(valor);
        input.setDescricao(descricao);
        input.setFrequencia(frequencia);
        input.setCategoria(categoria);
        return input;
    }

    @NotNull
    private Despesa newDespesa(Despesa despesa, int month, int day, BigDecimal valor, String descricao, String frequencia, String categoria) {
        despesa.setId(1L);
        despesa.setData(LocalDate.of(2022, month, day));
        despesa.setValor(valor);
        despesa.setDescricao(descricao);
        despesa.setFrequencia(Frequencia.valueOf(frequencia));
        despesa.setCategoria(Categoria.valueOf(categoria));
        return despesa;
    }


}
