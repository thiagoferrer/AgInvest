package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.CalculadoraVariavel;
import com.example.aginvest.model.produtos.Fiis;
import javafx.fxml.FXML;

import java.util.List;

public class ResultadoFIIsController {
    @FXML
    public void initialize (){

    }
    public void CalcularFiis (Fiis fiis){
        CalculadoraVariavel calculadoraVariavel = new CalculadoraVariavel();
        List<Fiis> resultados = calculadoraVariavel.simularFundoImobiliario(fiis);
        for (Fiis cfiis : resultados) {
            System.out.println("nome: " + cfiis.getNome());
            System.out.println("saldo cotas: " + String.format("%.2f", cfiis.getSaldoCotas()));
            System.out.println("saldo dividendos: " + String.format("%.2f",cfiis.getSaldoDividendos()));
            System.out.println("quantidades cotas: " + cfiis.getQtdCotas());
        }
    }
}
