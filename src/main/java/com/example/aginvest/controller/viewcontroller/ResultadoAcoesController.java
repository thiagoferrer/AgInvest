package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.CalculadoraVariavel;
import com.example.aginvest.model.produtos.Acoes;
import javafx.fxml.FXML;

import java.util.List;

public class ResultadoAcoesController {
    @FXML
    public void initialize (){

    }
    public void CalcularAcoes (Acoes acoes){
        CalculadoraVariavel calculadoraVariavel = new CalculadoraVariavel();
        List<Acoes> resultados = calculadoraVariavel.simularAcao(acoes);
        for (Acoes aacoes : resultados) {
            System.out.println("nome: " + aacoes.getNome());
            System.out.println("capital: " + aacoes.getValorInvestido());
            System.out.println("quantidade ações: " + aacoes.getQtdAcoes());

            System.out.println("total compra: " + aacoes.getCustoTotalCompra());
            System.out.println("total venda: " + aacoes.getValorTotalVenda());
            System.out.println("saldo final: " + aacoes.getSaldoFinal());
            System.out.println("troco capital: " + aacoes.getTroco());

        }
    }
}
