package com.example.aginvest.controller.viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import java.io.IOException;

public class FiisController {

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
    private TextField aporteMensalField;
    @FXML
    private TextField quantidadeCotasField;
    @FXML
    private TextField prazoField;

    // CheckBox para reinvestir dividendos
    @FXML
    private CheckBox reinvestirCheckBox;
    @FXML
    private CheckBox naoReinvestirCheckBox;

    // Botões de ação
    @FXML
    private Button voltarButton;
    @FXML
    private Button calcularButton;

    // Método para o botão "Home"
    @FXML
    public void onClickHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
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
        String aporteMensal = aporteMensalField.getText();
        String quantidadeCotas = quantidadeCotasField.getText();
        String prazo = prazoField.getText();
        boolean reinvestir = reinvestirCheckBox.isSelected();
        boolean naoReinvestir = naoReinvestirCheckBox.isSelected();

        System.out.println("Capital Inicial: " + capitalInicial);
        System.out.println("Aporte Mensal: " + aporteMensal);
        System.out.println("Quantidade de Cotas: " + quantidadeCotas);
        System.out.println("Prazo: " + prazo + " meses");
        System.out.println("Reinvestir Dividendos: " + (reinvestir ? "Sim" : "Não"));
    }

    // Método para garantir que apenas um CheckBox seja selecionado
    @FXML
    public void onReinvestirSelected() {
        if (reinvestirCheckBox.isSelected()) {
            naoReinvestirCheckBox.setSelected(false);
        }
    }

    @FXML
    public void onNaoReinvestirSelected() {
        if (naoReinvestirCheckBox.isSelected()) {
            reinvestirCheckBox.setSelected(false);
        }
    }
}