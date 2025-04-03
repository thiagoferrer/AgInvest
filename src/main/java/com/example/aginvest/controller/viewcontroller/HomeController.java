package com.example.aginvest.controller.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeController {

    @FXML
    private Button btnFundoImobiliario;

    @FXML
    private Button btnRendaFixa;

    @FXML
    private Button btnAcoes;

    @FXML
    private Button btnFAQ;

    @FXML
    public void initialize() {
        // Ação para o botão "Fundo Imobiliário"
        btnFundoImobiliario.setOnAction(event -> {
            System.out.println("Botão Fundo Imobiliário clicado!");
        });

        // Ação para o botão "Renda Fixa"
        btnRendaFixa.setOnAction(event -> {
            System.out.println("Botão Renda Fixa clicado!");
        });

        // Ação para o botão "Ações"
        btnAcoes.setOnAction(event -> {
            System.out.println("Botão Ações clicado!");
        });

        // Ação para o botão "FAQ"
        btnFAQ.setOnAction(event -> {
            System.out.println("Botão FAQ clicado!");
        });
    }
}