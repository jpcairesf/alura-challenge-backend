package com.alurachallenge.backend.controller;

import com.alurachallenge.backend.dto.ReceitaDto;
import com.alurachallenge.backend.model.Receita;
import com.alurachallenge.backend.model.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/receita")
@RestController
public class ReceitaController {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @GetMapping
    public List<Receita> findAll() {
        return movimentacaoRepository.findReceitas();
    }

    @PostMapping
    public Receita save(@Valid @RequestBody ReceitaDto receitaDto) {
        Receita receita = receitaDto.toReceita();
        return receita;
    }

    @GetMapping("/{id}")
    public Receita find(@PathVariable("id") Long id) {
        //Implementar
        return null;
    }

    @PutMapping("/{id}")
    public Receita update(@PathVariable("id") Long id, @RequestBody ReceitaDto receitaDto) {
        //Implementar
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        //Implementar
    }
}
