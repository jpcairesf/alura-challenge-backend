package com.alurachallenge.backend.service;

import com.alurachallenge.backend.dto.input.ReceitaDtoInput;
import com.alurachallenge.backend.dto.output.ReceitaDtoOutput;
import com.alurachallenge.backend.model.Frequencia;
import com.alurachallenge.backend.model.Receita;
import com.alurachallenge.backend.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReceitaService {

    private static final Sort sort = Sort.by("data").descending().and(Sort.by("valor").descending());

    private ReceitaRepository receitaRepository;

    @Autowired
    public ReceitaService(ReceitaRepository receitaRepository) {
        this.receitaRepository = receitaRepository;
    }

    public ReceitaDtoOutput findById(Long id) {
        Optional<Receita> receita = receitaRepository.findById(id);
        if (receita.isPresent()) {
            return new ReceitaDtoOutput(receita.get());
        } throw new IllegalArgumentException("Id inválido");
    }

    public List<ReceitaDtoOutput> search(String xpto) {
        String descricao;
        if (xpto == null) { descricao = ""; }
        else { descricao = xpto; }
        return receitaRepository
                .findByDescricaoContainingIgnoreCase(descricao, sort)
                .stream().map(ReceitaDtoOutput::new)
                .collect(Collectors.toList());
    }

    public List<ReceitaDtoOutput> findByData(Integer ano, Integer mes) {
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes,
                Month.of(mes).length(Year.of(ano).isLeap()));
        return receitaRepository
                .findByDataBetween(dataInicio, dataFim, sort)
                .stream().map(ReceitaDtoOutput::new)
                .collect(Collectors.toList());
    }

    public ReceitaDtoOutput save(ReceitaDtoInput input) {
        Receita receita = toReceita(input);
        receitaRepository.save(receita);
        return new ReceitaDtoOutput(receita);
    }

    public ReceitaDtoOutput update(Long id, ReceitaDtoInput input) {
        Optional<Receita> receita = receitaRepository.findById(id);
        if (receita.isPresent()) {
            set(input, receita.get());
            receitaRepository.save(receita.get());
            return new ReceitaDtoOutput(receita.get());
        } throw new IllegalArgumentException("Id inválido");
    }

    public void delete(Long id) {
        Optional<Receita> receita = receitaRepository.findById(id);
        if (receita.isPresent()) {
            receitaRepository.deleteById(id);
            return;
        } throw new IllegalArgumentException("Id inválido");
    }

    public void set(ReceitaDtoInput input, Receita receita) {
        if (input.getValor().compareTo(BigDecimal.ZERO) == 1) {
            receita.setData(input.getData());
            receita.setDescricao(input.getDescricao());
            receita.setFrequencia(Frequencia.valueOf(input.getFrequencia().toUpperCase()));
            receita.setValor(input.getValor());
            return;
        } throw new IllegalArgumentException("Valor inválido");
    }

    public Receita toReceita(ReceitaDtoInput input) {
        Receita receita = new Receita();
        set(input, receita);
        return receita;
    }

}
