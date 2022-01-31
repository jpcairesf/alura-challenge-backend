package com.alurachallenge.backend.controller;

import com.alurachallenge.backend.dto.ReceitaDto;
import com.alurachallenge.backend.model.Receita;
import com.alurachallenge.backend.model.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/receita")
@RestController
public class ReceitaController {

    private Sort sort = Sort.by("data").descending().and(Sort.by("valor").descending());

    @Autowired
    private ReceitaRepository receitaRepository;

    @GetMapping
    public List<Receita> findAll() {
        return receitaRepository.findAll(sort);
    }

    @PostMapping
    public Receita save(@Valid @RequestBody ReceitaDto receitaDto) {
        Receita receita = receitaDto.toReceita();
        receitaRepository.save(receita);
        return receita;
    }

    @GetMapping("/{id}")
    public Receita find(@PathVariable("id") Long id) {
        Optional<Receita> receita = receitaRepository.findById(id);
        if (receita.isPresent()) {
            return receita.get();
        } throw new IllegalArgumentException("Id inválido");
    }

    @PutMapping("/{id}")
    public Receita update(@PathVariable("id") Long id, @Valid @RequestBody ReceitaDto receitaDto) {
        Optional<Receita> receita = receitaRepository.findById(id);
        if (receita.isPresent()) {
            receitaDto.update(receita.get());
            receitaRepository.save(receita.get());
            return receita.get();
        } throw new IllegalArgumentException("Id inválido");
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        Optional<Receita> receita = receitaRepository.findById(id);
        if (receita.isPresent()) {
            receitaRepository.deleteById(id);
            return;
        } throw new IllegalArgumentException("Id inválido");
    }

    @DeleteMapping
    public void deleteAll() { receitaRepository.deleteAll(); }

}
