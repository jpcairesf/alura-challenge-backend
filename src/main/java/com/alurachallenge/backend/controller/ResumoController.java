package com.alurachallenge.backend.controller;

import com.alurachallenge.backend.dto.output.ResumoDoMes;
import com.alurachallenge.backend.service.ResumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/resumo")
@RestController
public class ResumoController {

    private ResumoService resumoService;

    @Autowired
    public ResumoController(ResumoService resumoService) {
        this.resumoService = resumoService;
    }

    @GetMapping("/{ano}/{mes}")
    public ResumoDoMes getResumo(
            @PathVariable("ano") Integer ano,
            @PathVariable("mes") Integer mes) {
        return resumoService.toResumo(ano, mes);
    }

}
