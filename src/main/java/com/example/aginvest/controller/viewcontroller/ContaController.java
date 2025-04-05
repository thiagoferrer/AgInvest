package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.user.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

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
        carregarTela(atualizacaoContaButton, "/com/example/aginvest/AtualizarDados.fxml", "Atualizar Dados - Invest7");
    }

    @FXML
    private void onClickRefazerQuiz() {
        carregarTela(refazerQuizButton, "/com/example/aginvest/quiz.fxml", "Questionário - Invest7");
    }

    @FXML private void onClickSairConta(){
        // 1. Mostra um diálogo de confirmação
        Alert confirmacao = new Alert(AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Saída");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Tem certeza que deseja sair da sua conta?");

        Optional<ButtonType> resultado = confirmacao.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            UserController userController = new UserController();
            userController.logout();

            carregarTela(contaButton, "/com/example/aginvest/login.fxml", "Login - Invest7");

        }
    }
    @FXML
    private void onClickDeletarConta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/deletarconta.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) deletarContaButton.getScene().getWindow();
            stage.setTitle("Deletar Conta - Invest7");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            mostrarAlerta("Erro", "Não foi possível abrir a tela de deletar conta: " + e.getMessage(), AlertType.INFORMATION);
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
            mostrarAlerta("Erro", "Não foi possível carregar a tela: " + fxmlPath + "\nErro: " + e.getMessage(), AlertType.INFORMATION);
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, AlertType information) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}