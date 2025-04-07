package com.example.aginvest.model.produtos;

import java.math.BigDecimal;

public class Produto {
    private int idProduto;
    private String nome;
    private double valorInvestido;
    private BigDecimal capital;
    private int meses;

    public Produto(BigDecimal capital, int meses) {
        this.capital = capital;
        this.meses = meses;
    }

    public Produto(int meses) {
        this.meses = meses;
    }

    public Produto (String nome) {
        this.nome = nome;
    }


    public Produto (String nome, double valorInvestido) {
        this.nome = nome;
        this.valorInvestido = valorInvestido;
    }



    public Produto (double valorInvestido, int meses) {
        this.valorInvestido = valorInvestido;
        this.meses = meses;
    }

    public void exibir(){
        System.out.println("Produto: " + nome);
        System.out.println("Valor Investido: R$ " + valorInvestido);
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public double getValorInvestido() {
        return valorInvestido;
    }

    public void setValorInvestido(double valorInvestido) {
        this.valorInvestido = valorInvestido;
    }

    public int getMeses() {return meses;}

    public void setMeses(int meses) {
        this.meses = meses;
    }
}
