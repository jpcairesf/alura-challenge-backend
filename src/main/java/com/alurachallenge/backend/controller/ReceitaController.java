package com.alurachallenge.backend.controller;

import com.alurachallenge.backend.dto.input.ReceitaDtoInput;
import com.alurachallenge.backend.dto.output.ReceitaDtoOutput;
import com.alurachallenge.backend.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/receitas")
@RestController
public class ReceitaController {

    private ReceitaService receitaService;

    @Autowired
    public ReceitaController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @GetMapping
    public List<ReceitaDtoOutput> search(
            @RequestParam(value = "descricao", required = false) String descricao) {
        return receitaService.search(descricao);
    }

    @GetMapping("/{id}")
    public ReceitaDtoOutput findById(@PathVariable("id") Long id) {
        return receitaService.findById(id);
    }

    @GetMapping("/{ano}/{mes}")
    public List<ReceitaDtoOutput> findByData(
            @PathVariable("ano") Integer ano,
            @PathVariable("mes") Integer mes) {
        return receitaService.findByData(ano, mes);
    }

    @PostMapping
    public ReceitaDtoOutput save(@Valid @RequestBody ReceitaDtoInput input) {
        return receitaService.save(input);
    }

    @PutMapping("/{id}")
    public ReceitaDtoOutput update(@PathVariable("id") Long id, @Valid @RequestBody ReceitaDtoInput input) {
        return receitaService.update(id, input);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        receitaService.delete(id);
    }
}
