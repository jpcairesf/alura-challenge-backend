package com.alurachallenge.backend.controller;

import com.alurachallenge.backend.dto.DespesaDto;
import com.alurachallenge.backend.model.Despesa;
import com.alurachallenge.backend.model.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/despesa")
@RestController
public class DespesaController {

    private Sort sort = Sort.by("data").descending().and(Sort.by("valor").descending());

    @Autowired
    private DespesaRepository despesaRepository;

    @GetMapping()
    public List<Despesa> findAll() {
        return despesaRepository.findAll(sort);
    }

    @PostMapping
    public Despesa save(@Valid @RequestBody DespesaDto despesaDto) {
        Despesa despesa = despesaDto.toDespesa();
        despesaRepository.save(despesa);
        return despesa;
    }

    @GetMapping("/{id}")
    public Despesa find(@PathVariable("id") Long id) {
        Optional<Despesa> despesa = despesaRepository.findById(id);
        if (despesa.isPresent()) {
            return despesa.get();
        } throw new IllegalArgumentException("Id inválido");
    }

    @PutMapping("/{id}")
    public Despesa update(@PathVariable("id") Long id, @Valid @RequestBody DespesaDto despesaDto) {
        Optional<Despesa> despesa = despesaRepository.findById(id);
        if (despesa.isPresent()) {
            despesaDto.update(despesa.get());
            despesaRepository.save(despesa.get());
            return despesa.get();
        } throw new IllegalArgumentException("Id inválido");
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        Optional<Despesa> despesa = despesaRepository.findById(id);
        if (despesa.isPresent()) {
            despesaRepository.deleteById(id);
            return;
        } throw new IllegalArgumentException("Id inválido");
    }

    @DeleteMapping
    public void deleteAll() { despesaRepository.deleteAll(); }
}