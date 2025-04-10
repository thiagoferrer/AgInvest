package com.example.aginvest.model.produtos;


import java.math.BigDecimal;

public class Previa {
    private String nome;
    private double totalInvestido;
    private double rendimentoBruto;
    private double  impostoIR;
    private double rendimentoLiquido;
    private double  valorTotal;
    private double rentabilidadeBruta;
    private String percentualLucro;


    public Previa(String nome) {
        this.nome = nome;
    }

    public Previa(String nome, double rentabilidadeBruta) {
        this.nome = nome;
        this.rentabilidadeBruta = rentabilidadeBruta;
    }

    public Previa(String nome, double rendimentoBruta, double  impostoIR, double rendimentoLiquido) {
        this.nome = nome;
        this.rentabilidadeBruta = rentabilidadeBruta;
    }

    public void setSimulationResults(double totalInvestido, double  rendimentoBruto,
                                     double  impostoIR, double  rendimentoLiquido,
                                     double  valorTotal, String percentualLucro) {
        this.totalInvestido = totalInvestido;
        this.rendimentoBruto = rendimentoBruto;
        this.impostoIR = impostoIR;
        this.rendimentoLiquido = rendimentoLiquido;
        this.valorTotal = valorTotal;
        this.percentualLucro = percentualLucro;
    }

    public String getNome() { return nome;}
    public double getTotalInvestido() { return totalInvestido; }
    public double getRendimentoBruto() { return rendimentoBruto; }
    public double getImpostoIR() { return impostoIR; }
    public double getRendimentoLiquido() { return rendimentoLiquido; }
    public double getValorTotal() { return valorTotal;}
    public double getRentabilidadeBruta() {return rentabilidadeBruta;}
    public String getPercentualLucro() {return percentualLucro;}


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTotalInvestido(double totalInvestido) {
        this.totalInvestido = totalInvestido;
    }

    public void setRendimentoBruto(double rendimentoBruto) {
        this.rendimentoBruto = rendimentoBruto;
    }

    public void setImpostoIR(double impostoIR) {
        this.impostoIR = impostoIR;
    }

    public void setRendimentoLiquido(double rendimentoLiquido) {
        this.rendimentoLiquido = rendimentoLiquido;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setRentabilidadeBruta(double rentabilidadeBruta) {
        this.rentabilidadeBruta = rentabilidadeBruta;
    }

    public void setPercentualLucro(String percentualLucro) {
        this.percentualLucro = percentualLucro;
    }
}
