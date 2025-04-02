package com.example.aginvest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeController {

    // Botões do cabeçalho
    @FXML
    private Button comecarButton; // Botão "Home"
    @FXML
    private Button faqButton; // Botão "FAQ"
    @FXML
    private Button contaButton; // Botão "Conta"

    // Botões do GridPane
    @FXML
    private Button perfilButton; // Botão "De Perfil"
    @FXML
    private Button rendaFixaButton; // Botão "Renda Fixa"
    @FXML
    private Button acoesButton; // Botão "Ações"
    @FXML
    private Button fundoImobiliarioButton; // Botão "Fundo Imobiliário"

    // Método para o botão "Home"
    @FXML
    public void onclickcomecar() {
        // Já estamos na tela Home, então apenas recarregar
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) comecarButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Home - Invest7");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao recarregar a tela Home: " + e.getMessage());
        }
    }

    // Método para o botão "FAQ"
    @FXML
    public void onClickFaq() {
        loadNewScreen("FAQ.fxml", "FAQ - Invest7", faqButton);
    }

    // Método para o botão "Conta"
    @FXML
    public void onClickConta() {
        loadNewScreen("Conta.fxml", "Conta - Invest7", contaButton);
    }

    // Método para o botão "De Perfil"
    @FXML
    public void onClickPerfil() {
        loadNewScreen("Perfil.fxml", "Perfil do Investidor - Invest7", perfilButton);
    }

    // Método para o botão "Renda Fixa"
    @FXML
    public void onClickRendaFixa() {
        loadNewScreen("RendaFixa.fxml", "Renda Fixa - Invest7", rendaFixaButton);
    }

    // Método para o botão "Ações"
    @FXML
    public void onClickAcoes() {
        loadNewScreen("Acoes.fxml", "Ações - Invest7", acoesButton);
    }

    // Método para o botão "Fundo Imobiliário"
    @FXML
    public void onClickFundoImobiliario() {
        loadNewScreen("FundoImobiliario.fxml", "Fundo Imobiliário - Invest7", fundoImobiliarioButton);
    }

    // Método auxiliar para carregar novas telas
    private void loadNewScreen(String fxmlFile, String title, Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela " + title + ": " + e.getMessage());
        }
    }
}