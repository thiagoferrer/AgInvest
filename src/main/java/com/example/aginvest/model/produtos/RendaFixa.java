package com.example.aginvest.model.produtos;

import java.math.BigDecimal;

public class RendaFixa {
    private String nome;
    private double rentabilidadeBruta;
    private String tipoProduto;
    private boolean taxable;
    private BigDecimal totalInvestido;
    private BigDecimal rendimentoBruto;
    private BigDecimal impostoIR;
    private BigDecimal rendimentoLiquido;
    private BigDecimal valorTotal;
    private String percentualLucro;
    private int id_renda;

    public RendaFixa(String nome, double rentabilidadeBruta, String tipoProduto, boolean taxable) {
        this.nome = nome;
        this.id_renda = id_renda;
        this.rentabilidadeBruta = rentabilidadeBruta;
        this.tipoProduto = tipoProduto;
        this.taxable = taxable;
    }

    public RendaFixa(String nome, int id_renda, double rentabilidadeBruta, String tipoProduto, boolean taxable) {
        this.nome = nome;
        this.rentabilidadeBruta = rentabilidadeBruta;
        this.tipoProduto = tipoProduto;
        this.taxable = taxable;
    }


    public void setSimulationResults(BigDecimal totalInvestido, BigDecimal rendimentoBruto,
                                     BigDecimal impostoIR, BigDecimal rendimentoLiquido,
                                     BigDecimal valorTotal, String percentualLucro) {
        this.totalInvestido = totalInvestido;
        this.rendimentoBruto = rendimentoBruto;
        this.impostoIR = impostoIR;
        this.rendimentoLiquido = rendimentoLiquido;
        this.valorTotal = valorTotal;
        this.percentualLucro = percentualLucro;
    }

    // Getters
    public String getNome() { return nome; }
    public String getTipoProduto() { return tipoProduto; }
    public boolean isTaxable() { return taxable; }
    public BigDecimal getTotalInvestido() { return totalInvestido; }
    public BigDecimal getRendimentoBruto() { return rendimentoBruto; }
    public BigDecimal getImpostoIR() { return impostoIR; }
    public BigDecimal getRendimentoLiquido() { return rendimentoLiquido; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public String getPercentualLucro() { return percentualLucro; }
    public double getRentabilidadeBruta() { return rentabilidadeBruta; }
}