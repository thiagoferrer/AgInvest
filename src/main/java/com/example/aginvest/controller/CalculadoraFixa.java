package com.example.aginvest.controller;
/*
import com.invest7.dao.RendaFixaDAO;
import com.invest7.model.produtos.RendaFixa;
import com.invest7.util.TaxService;
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



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CalculadoraFixa {
    private final RendaFixaDAO dao = new RendaFixaDAO();

    public List<RendaFixa> simularInvestimento(BigDecimal valorInicial, BigDecimal aporteMensal, int meses) {
        List<RendaFixa> produtos = dao.buscarTodosProdutos();

        for (RendaFixa produto : produtos) {
            // Convert annual rate to monthly decimal format
            BigDecimal annualRate = BigDecimal.valueOf(produto.getRentabilidadeBruta())
                    .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

            BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
            BigDecimal totalInvestido = valorInicial.add(aporteMensal.multiply(BigDecimal.valueOf(meses)));

            // Future value calculation using compound interest formula
            BigDecimal futureValueInitial = valorInicial.multiply(
                    BigDecimal.ONE.add(monthlyRate).pow(meses)
            );

            // Future value of monthly contributions (annuity formula)
            BigDecimal futureValueContributions = BigDecimal.ZERO;
            if (monthlyRate.compareTo(BigDecimal.ZERO) > 0) {
                futureValueContributions = aporteMensal.multiply(
                        BigDecimal.ONE.add(monthlyRate).pow(meses).subtract(BigDecimal.ONE)
                ).divide(monthlyRate, RoundingMode.HALF_UP);
            } else {
                futureValueContributions = aporteMensal.multiply(BigDecimal.valueOf(meses));
            }

            BigDecimal valorFinal = futureValueInitial.add(futureValueContributions)
                    .setScale(2, RoundingMode.HALF_UP);

            BigDecimal rendimentoBruto = valorFinal.subtract(totalInvestido);

            // Tax calculations
            int dias = meses * 30;
            BigDecimal irRate = TaxService.calcularTaxaIR(dias, produto.isTaxable());
            BigDecimal impostoIR = rendimentoBruto.multiply(irRate)
                    .setScale(2, RoundingMode.HALF_UP);

            BigDecimal rendimentoLiquido = rendimentoBruto.subtract(impostoIR)
                    .setScale(2, RoundingMode.HALF_UP);

            BigDecimal valorTotal = totalInvestido.add(rendimentoLiquido)
                    .setScale(2, RoundingMode.HALF_UP);

            // Calculate percentage profit
            String percentLucro;
            if (totalInvestido.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal percent = rendimentoLiquido
                        .divide(totalInvestido, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100))
                        .setScale(2, RoundingMode.HALF_UP);
                percentLucro = percent + "%";
            } else {
                percentLucro = "0%";
            }

            produto.setSimulationResults(
                    totalInvestido,
                    rendimentoBruto,
                    impostoIR,
                    rendimentoLiquido,
                    valorTotal,
                    percentLucro
            );
        }

        return produtos;
    }
}