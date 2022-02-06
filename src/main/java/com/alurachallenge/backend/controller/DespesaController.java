package com.alurachallenge.backend.controller;

import com.alurachallenge.backend.dto.input.DespesaDtoInput;
import com.alurachallenge.backend.dto.output.DespesaDtoOutput;
import com.alurachallenge.backend.service.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/despesas")
@RestController
public class DespesaController {

    private DespesaService despesaService;

    @GetMapping
    public List<DespesaDtoOutput> search(
            @RequestParam(value = "descricao", required = false) String descricao) {
        return despesaService.search(descricao);
    }

    @Autowired
    public DespesaController(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @GetMapping("/{id}")
    public DespesaDtoOutput find(@PathVariable("id") Long id) {
        return despesaService.findById(id);
    }

    @GetMapping("/{ano}/{mes}")
    public List<DespesaDtoOutput> findByData(
            @PathVariable("ano") Integer ano,
            @PathVariable("mes") Integer mes) {
        return despesaService.findByData(ano, mes);
    }

    @PostMapping
    public DespesaDtoOutput save(@Valid @RequestBody DespesaDtoInput input) {
        return despesaService.save(input);
    }

    @PutMapping("/{id}")
    public DespesaDtoOutput update(@PathVariable("id") Long id, @Valid @RequestBody DespesaDtoInput input) {
        return despesaService.update(id, input);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        despesaService.delete(id);
    }
}