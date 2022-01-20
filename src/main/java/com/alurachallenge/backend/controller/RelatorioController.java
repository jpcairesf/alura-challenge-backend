package com.alurachallenge.backend.controller;

import com.alurachallenge.backend.dto.RelatorioDto;
import com.alurachallenge.backend.model.Despesa;
import com.alurachallenge.backend.model.Movimentacao;
import com.alurachallenge.backend.model.Receita;
import com.alurachallenge.backend.model.repository.DespesaRepository;
import com.alurachallenge.backend.model.repository.MovimentacaoRepository;
import com.alurachallenge.backend.model.repository.ReceitaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/relatorio")
@RestController
public class RelatorioController {

    private Sort sort = Sort.by("data").descending().and(Sort.by("valor").descending());

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @GetMapping("despesa")
    public List<Despesa> findDespesaByDataBetweenPageable(@Valid @RequestBody RelatorioDto relatorioDto) {
        return despesaRepository.findByDataBetween(relatorioDto.dataInicio(), relatorioDto.dataFim(), sort);
    }

    @GetMapping("receita")
    public List<Receita> findReceitaByDataBetweenPageable(@Valid @RequestBody RelatorioDto relatorioDto) {
        return receitaRepository.findByDataBetween(relatorioDto.dataInicio(), relatorioDto.dataFim(), sort);
    }

    @GetMapping("movimentacao")
    public List<Movimentacao> findMovimentacaoByDataBetweenPageable(@Valid @RequestBody RelatorioDto relatorioDto) {
        return movimentacaoRepository.findByDataBetween(relatorioDto.dataInicio(), relatorioDto.dataFim(), sort);
    }
}
