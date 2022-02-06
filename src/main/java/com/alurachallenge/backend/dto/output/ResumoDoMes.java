package com.alurachallenge.backend.dto.output;

import java.math.BigDecimal;
import java.util.List;

public class ResumoDoMes {

    private BigDecimal totalReceitas;

    private BigDecimal totalDespesas;

    private BigDecimal totalSaldo;

    private List<ResumoCategoria> gastos;

    public ResumoDoMes(
            BigDecimal totalReceitas,
            BigDecimal totalDespesas,
            BigDecimal totalSaldo,
            List<ResumoCategoria> gastos) {
        this.totalReceitas = totalReceitas;
        this.totalDespesas = totalDespesas;
        this.totalSaldo = totalSaldo;
        this.gastos = gastos;
    }

    public BigDecimal getTotalReceitas() {
        return totalReceitas;
    }

    public void setTotalReceitas(BigDecimal totalReceitas) {
        this.totalReceitas = totalReceitas;
    }

    public BigDecimal getTotalDespesas() {
        return totalDespesas;
    }

    public void setTotalDespesas(BigDecimal totalDespesas) {
        this.totalDespesas = totalDespesas;
    }

    public BigDecimal getTotalSaldo() {
        return totalSaldo;
    }

    public void setTotalSaldo(BigDecimal totalSaldo) {
        this.totalSaldo = totalSaldo;
    }

    public List<ResumoCategoria> getGastos() {
        return gastos;
    }

    public void setGastos(List<ResumoCategoria> gastos) {
        this.gastos = gastos;
    }
}
