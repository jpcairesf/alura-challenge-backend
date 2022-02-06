package com.alurachallenge.backend.service;

import com.alurachallenge.backend.dto.input.DespesaDtoInput;
import com.alurachallenge.backend.dto.output.DespesaDtoOutput;
import com.alurachallenge.backend.model.Categoria;
import com.alurachallenge.backend.model.Despesa;
import com.alurachallenge.backend.model.Frequencia;
import com.alurachallenge.backend.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DespesaService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final Sort sort = Sort.by("data").descending().and(Sort.by("valor").descending());

    private DespesaRepository despesaRepository;

    @Autowired
    public DespesaService(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    public DespesaDtoOutput findById(Long id) {
        Optional<Despesa> despesa = despesaRepository.findById(id);
        if (despesa.isPresent()) {
            return new DespesaDtoOutput(despesa.get());
        } throw new IllegalArgumentException("Id inválido");
    }

    public List<DespesaDtoOutput> search(String xpto) {
        String descricao;
        if (xpto == null) { descricao = ""; }
        else { descricao = xpto; }
        return despesaRepository
                .findByDescricaoContainingIgnoreCase(descricao, sort)
                .stream().map(DespesaDtoOutput::new)
                .collect(Collectors.toList());
    }

    public List<DespesaDtoOutput> findByData(Integer ano, Integer mes) {
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes,
                Month.of(mes).length(Year.of(ano).isLeap()));
        return despesaRepository
                .findByDataBetween(dataInicio, dataFim, sort)
                .stream().map(DespesaDtoOutput::new)
                .collect(Collectors.toList());
    }

    public DespesaDtoOutput save(DespesaDtoInput input) {
        Despesa despesa = toDespesa(input);
        despesaRepository.save(despesa);
        return new DespesaDtoOutput(despesa);
    }

    public DespesaDtoOutput update(Long id, DespesaDtoInput input) {
        Optional<Despesa> despesa = despesaRepository.findById(id);
        if (despesa.isPresent()) {
            set(input, despesa.get());
            despesaRepository.save(despesa.get());
            return new DespesaDtoOutput(despesa.get());
        } throw new IllegalArgumentException("Id inválido");
    }

    public void delete(Long id) {
        Optional<Despesa> despesa = despesaRepository.findById(id);
        if (despesa.isPresent()) {
            despesaRepository.deleteById(id);
            return;
        } throw new IllegalArgumentException("Id inválido");
    }

    public void set(DespesaDtoInput input, Despesa despesa) {
        if (input.getValor().compareTo(BigDecimal.ZERO) == 1 ) {
            despesa.setData(input.getData());
            despesa.setDescricao(input.getDescricao());
            despesa.setFrequencia(Frequencia.valueOf(input.getFrequencia().toUpperCase()));
            despesa.setCategoria(Categoria.valueOf(input.getCategoria().toUpperCase()));
            despesa.setValor(input.getValor().negate());
            return;
        } throw new IllegalArgumentException("Valor inválido");
    }

    public Despesa toDespesa(DespesaDtoInput input) {
        Despesa despesa = new Despesa();
        set(input, despesa);
        return despesa;
    }
}
