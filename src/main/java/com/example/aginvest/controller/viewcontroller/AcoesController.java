package com.example.aginvest.controller.viewcontroller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class AcoesController {

    // Botões do cabeçalho
    @FXML
    private Button homeButton;
    @FXML
    private Button faqButton;
    @FXML
    private Button contaButton;

    // Campos de entrada
    @FXML
    private TextField capitalInicialField;
    @FXML
    private TextField prazoField;

    // Botões de ação
    @FXML
    private Button voltarButton;
    @FXML
    private Button calcularButton;

    // Método para o botão "Home"
    @FXML
    public void onClickHome() {
        try {
            java.net.URL fxmlLocation = getClass().getResource("Home.fxml");
            if (fxmlLocation == null) {
                throw new IOException("Não foi possível encontrar o arquivo Home.fxml");
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            Scene homeScene = new Scene(root);
            Stage stage = (Stage) homeButton.getScene().getWindow();
            stage.setScene(homeScene);
            stage.setTitle("Home - Invest7");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela Home: " + e.getMessage());
        }
    }

    // Método para o botão "FAQ"
    @FXML
    public void onClickFaq() {
        System.out.println("Botão FAQ clicado!");
        // Adicione lógica para navegar para a tela de FAQ
    }

    // Método para o botão "Conta"
    @FXML
    public void onClickConta() {
        System.out.println("Botão Conta clicado!");
        // Adicione lógica para navegar para a tela de Conta
    }

    // Método para o botão "Voltar"
    @FXML
    public void onClickVoltar() {
        try {
            java.net.URL fxmlLocation = getClass().getResource("Home.fxml");
            if (fxmlLocation == null) {
                throw new IOException("Não foi possível encontrar o arquivo Home.fxml");
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            Scene homeScene = new Scene(root);
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            stage.setScene(homeScene);
            stage.setTitle("Home - Invest7");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela Home: " + e.getMessage());
        }
    }

    // Método para o botão "Calcular"
    @FXML
    public void onClickCalcular() {
        System.out.println("Botão Calcular clicado!");
        // Adicione lógica para realizar o cálculo com base nos valores inseridos
        String capitalInicial = capitalInicialField.getText();
        String prazo = prazoField.getText();

        System.out.println("Capital Inicial: " + capitalInicial);
        System.out.println("Prazo: " + prazo + " meses");
    }
}