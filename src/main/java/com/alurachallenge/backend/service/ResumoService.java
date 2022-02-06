package com.alurachallenge.backend.service;

import com.alurachallenge.backend.dto.output.ResumoCategoria;
import com.alurachallenge.backend.dto.output.ResumoDoMes;
import com.alurachallenge.backend.repository.DespesaRepository;
import com.alurachallenge.backend.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;

@Service
public class ResumoService {

    private ReceitaRepository receitaRepository;

    private DespesaRepository despesaRepository;

    @Autowired
    public ResumoService(
            ReceitaRepository receitaRepository,
            DespesaRepository despesaRepository) {
        this.receitaRepository = receitaRepository;
        this.despesaRepository = despesaRepository;
    }

    public ResumoDoMes toResumo(Integer ano, Integer mes) {
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes,
                Month.of(mes).length(Year.of(ano).isLeap()));
        BigDecimal totalReceitas = receitaRepository.findTotalByDataBetween(dataInicio, dataFim);
        BigDecimal totalDespesas = despesaRepository.findTotalByDataBetween(dataInicio, dataFim);
        BigDecimal saldo = totalReceitas.subtract(totalDespesas);
        return new ResumoDoMes(
                totalReceitas, totalDespesas, saldo,
                toResumoCategoria(dataInicio, dataFim));
    }

    public List<ResumoCategoria> toResumoCategoria(LocalDate dataInicio, LocalDate dataFim) {
        return despesaRepository.findCategoriaAndTotalByDataBetween(dataInicio, dataFim);
    }

    /**
    public List<ResumoDoMesCategoria> toResumoCategoria(
            LocalDate dataInicio, LocalDate dataFim) {
        List<ResumoDoMesCategoria> list = new ArrayList<>();
        for (Categoria c : Categoria.values()) {
            Double totalGasto = despesaRepository
                    .findTotalByCategoriaAndDataBetween(c, dataInicio, dataFim);
            list.add(new ResumoDoMesCategoria(c,totalGasto));
        }
        return list;
    }
     **/

}
