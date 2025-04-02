package com.example.aginvest.controller;
/*
import com.invest7.dao.RendaFixaDAO;
import com.invest7.model.produtos.RendaFixa;
import com.invest7.util.TaxService;

 */

import com.example.aginvest.dao.RendaFixaDAO;
import com.example.aginvest.model.produtos.RendaFixa;
import com.example.aginvest.util.TaxService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CalculadoraFixa {
    private final RendaFixaDAO dao = new RendaFixaDAO();

    public List<RendaFixa> simularInvestimento(BigDecimal valorInicial, BigDecimal aporteMensal, int meses) {
        List<RendaFixa> produtos = dao.buscarTodosProdutos();

        for (RendaFixa produto : produtos) {
            BigDecimal totalInvestido = valorInicial.add(aporteMensal.multiply(BigDecimal.valueOf(meses)));
            BigDecimal annualRate = BigDecimal.valueOf(produto.getRentabilidadeBruta()).divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
            BigDecimal monthlyRate = BigDecimal.valueOf(Math.pow(1 + annualRate.doubleValue(), 1.0/12) - 1);
            BigDecimal balance = valorInicial;

            // Compound interest with monthly contributions
            for (int month = 1; month <= meses; month++) {
                balance = balance.add(aporteMensal).multiply(BigDecimal.ONE.add(monthlyRate));
            }

            BigDecimal rendimentoBruto = balance.subtract(totalInvestido).setScale(2, RoundingMode.HALF_UP);
            int dias = meses * 30;
            BigDecimal irRate = TaxService.calcularTaxaIR(dias, produto.isTaxable());
            BigDecimal impostoIR = rendimentoBruto.multiply(irRate).setScale(2, RoundingMode.HALF_UP);
            BigDecimal rendimentoLiquido = rendimentoBruto.subtract(impostoIR);
            BigDecimal valorTotal = totalInvestido.add(rendimentoLiquido);

            // Calculate percentage profit
            String percentLucro = totalInvestido.compareTo(BigDecimal.ZERO) > 0 ?
                    rendimentoLiquido.divide(totalInvestido, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100))
                            .setScale(2, RoundingMode.HALF_UP) + "%" : "0%";

            produto.setSimulationResults(totalInvestido, rendimentoBruto, impostoIR, rendimentoLiquido, valorTotal, percentLucro);
        }
        return produtos;
    }

    public List<RendaFixa> simularInvestimentoPerfil(BigDecimal valorInicial, BigDecimal aporteMensal, int meses, int quantidade) {
        List<RendaFixa> produtos = dao.buscarPorQuantidade(quantidade);

        for (RendaFixa produto : produtos) {
            BigDecimal totalInvestido = valorInicial.add(aporteMensal.multiply(BigDecimal.valueOf(meses)));
            BigDecimal annualRate = BigDecimal.valueOf(produto.getRentabilidadeBruta()).divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
            BigDecimal monthlyRate = BigDecimal.valueOf(Math.pow(1 + annualRate.doubleValue(), 1.0/12) - 1);
            BigDecimal balance = valorInicial;

            // Compound interest with monthly contributions
            for (int month = 1; month <= meses; month++) {
                balance = balance.add(aporteMensal).multiply(BigDecimal.ONE.add(monthlyRate));
            }

            BigDecimal rendimentoBruto = balance.subtract(totalInvestido).setScale(2, RoundingMode.HALF_UP);
            int dias = meses * 30;
            BigDecimal irRate = TaxService.calcularTaxaIR(dias, produto.isTaxable());
            BigDecimal impostoIR = rendimentoBruto.multiply(irRate).setScale(2, RoundingMode.HALF_UP);
            BigDecimal rendimentoLiquido = rendimentoBruto.subtract(impostoIR);
            BigDecimal valorTotal = totalInvestido.add(rendimentoLiquido);

            // Calculate percentage profit
            String percentLucro = totalInvestido.compareTo(BigDecimal.ZERO) > 0 ?
                    rendimentoLiquido.divide(totalInvestido, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100))
                            .setScale(2, RoundingMode.HALF_UP) + "%" : "0%";

            produto.setSimulationResults(totalInvestido, rendimentoBruto, impostoIR, rendimentoLiquido, valorTotal, percentLucro);
        }
        return produtos;
    }
}