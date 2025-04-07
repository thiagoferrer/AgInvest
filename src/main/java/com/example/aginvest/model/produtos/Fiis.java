package com.example.aginvest.model.produtos;


import java.math.BigDecimal;


public class Fiis extends Produto {
    private double aporte;
    private int qtdCotas;
    private int reinvestir;
    private double txIr;
    private double precoFiis;
    private double dividendYield;
    private double desvioCotas;
    private double desvioDividendos;
    private double saldoCotas;
    private double saldoDividendos;
    private int id_fiis;

    public double getDividendosMensais() {
        return dividendosMensais;
    }

    public void setDividendosMensais(double dividendosMensais) {
        this.dividendosMensais = dividendosMensais;
    }

    private double dividendosMensais;


    public Fiis(String nome, double valorInvestido, double txIr, double precoFiis, double dividendYield, double desvioCotas, double desvioDividendos) {
        super(nome, valorInvestido);
        this.txIr = txIr;
        this.precoFiis = precoFiis;
        this.dividendYield = dividendYield;
        this.desvioCotas = desvioCotas;
        this.desvioDividendos = desvioDividendos;
    }


    public Fiis( double aporte, int qtdCotas, int meses, int reinvestir) {
        super( meses);
        this.aporte = aporte;
        this.qtdCotas = qtdCotas;
        this.reinvestir = reinvestir;
    }
    public Fiis(double valorInvestido, double aporte, int meses, int qtdCotas) {
        super(valorInvestido, meses);
        this.aporte = aporte;
        this.qtdCotas = qtdCotas;
    }


    public Fiis(String nome, int id_fiis, double precoFiis, double dividendYield, double desvioCotas, double desvioDividendos) {
        super(nome);
        this.id_fiis = id_fiis;
        this.precoFiis = precoFiis;
        this.dividendYield = dividendYield;
        this.desvioCotas = desvioCotas;
        this.desvioDividendos = desvioDividendos;
    }


    public Fiis(String nome, double precoFiis, double dividendYield, double desvioCotas, double desvioDividendos) {
        super(nome);
        this.precoFiis = precoFiis;
        this.dividendYield = dividendYield;
        this.desvioCotas = desvioCotas;
        this.desvioDividendos = desvioDividendos;
    }


    public Fiis(double aporte, int meses, int qtdCotas) {
        super( meses);
        this.qtdCotas = qtdCotas;
        this.aporte = aporte;
    }


    public double getAporte() {
        return aporte;
    }


    public void setAporte(double aporte) {
        this.aporte = aporte;
    }


    public double getTxIr() {
        return txIr;
    }


    public void setTxIr(double txIr) {
        this.txIr = txIr;
    }


    public double getPrecoFiis() {
        return precoFiis;
    }


    public void setPrecoFiis(double precoFiis) {
        this.precoFiis = precoFiis;
    }


    public double getDividendYield() {
        return dividendYield;
    }


    public void setDividendYield(double dividendYield) {
        this.dividendYield = dividendYield;
    }


    public double getDesvioCotas() {
        return desvioCotas;
    }


    public void setDesvioCotas(double desvioCotas) {
        this.desvioCotas = desvioCotas;
    }


    public double getDesvioDividendos() {
        return desvioDividendos;
    }


    public void setDesvioDividendos(double desvioDividendos) {
        this.desvioDividendos = desvioDividendos;
    }


    public int getQtdCotas() {
        return qtdCotas;
    }


    public void setQtdCotas(int qtdCotas) {
        this.qtdCotas = qtdCotas;
    }


    public int getReinvestir() {
        return reinvestir;
    }


    public void setReinvestir(int reinvestir) {
        this.reinvestir = reinvestir;
    }




    public double getSaldoCotas() {
        return saldoCotas;
    }


    public void setSaldoCotas(double saldoCotas) {
        this.saldoCotas = saldoCotas;
    }


    public double getSaldoDividendos() {
        return saldoDividendos;
    }


    public void setSaldoDividendos(double saldoDividendos) {
        this.saldoDividendos = saldoDividendos;
    }


    public int getId_fiis() {
        return id_fiis;
    }


    public void setId_fiis(int id_fiis) {
        this.id_fiis = id_fiis;
    }
}
