package com.alurachallenge.backend.controller;

import com.alurachallenge.backend.dto.DespesaDto;
import com.alurachallenge.backend.model.Despesa;
import com.alurachallenge.backend.model.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/despesa")
@RestController
public class DespesaController {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @GetMapping
    public List<Despesa> findAll() {
        return movimentacaoRepository.findDespesas();
    }

    @PostMapping
    public Despesa save(@Valid @RequestBody DespesaDto despesaDto) {
        Despesa despesa = despesaDto.toDespesa();
        return despesa;
    }

    @GetMapping("/{id}")
    public Despesa find(@PathVariable("id") Long id) {
        //Implementar
        return null;
    }

    @PutMapping("/{id}")
    public Despesa update(@PathVariable("id") Long id, @RequestBody DespesaDto despesaDto) {
        //Implementar
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        //Implementar
    }
}
