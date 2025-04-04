package com.example.aginvest.controller.viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class ContaController {

    // Botões do cabeçalho
    @FXML private Button homeButton;
    @FXML private Button faqButton;
    @FXML private Button contaButton;

    // Botões de opções
    @FXML private Button dadosDacontaButton;
    @FXML private Button atualizacaoContaButton;
    @FXML private Button refazerQuizButton;
    @FXML private Button deletarContaButton;
    @FXML private Button backButton;

    @FXML
    private void initialize() {
        System.out.println("ContaController inicializado");
        // Configurações adicionais podem ser feitas aqui
    }

    // Métodos de navegação
    @FXML
    private void onClickHome() {
        carregarTela(homeButton, "/com/example/aginvest/home.fxml", "Home - Invest7");
    }

    @FXML
    private void onClickFaq() {
        carregarTela(faqButton, "/com/example/aginvest/faq.fxml", "FAQ - Invest7");
    }

    @FXML
    private void onClickConta() {
        // Já estamos na tela de conta
    }

    @FXML
    private void onClickDadosConta() {
        carregarTela(dadosDacontaButton, "/com/example/aginvest/dadosconta.fxml", "Dados da Conta - Invest7");
    }

    @FXML
    private void onClickAtualizacaoConta() {
        carregarTela(atualizacaoContaButton, "/com/example/aginvest/atualizacaoconta.fxml", "Atualizar Dados - Invest7");
    }

    @FXML
    private void onClickRefazerQuiz() {
        carregarTela(refazerQuizButton, "/com/example/aginvest/quiz.fxml", "Questionário - Invest7");
    }

    @FXML
    private void onClickDeletarConta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/deletarconta.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Deletar Conta - Invest7");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            mostrarAlerta("Erro", "Não foi possível abrir a tela de deletar conta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickBackToHome() {
        carregarTela(backButton, "/com/example/aginvest/home.fxml", "Home - Invest7");
    }

    private void carregarTela(Button botaoOrigem, String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) botaoOrigem.getScene().getWindow();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            mostrarAlerta("Erro", "Não foi possível carregar a tela: " + fxmlPath + "\nErro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}