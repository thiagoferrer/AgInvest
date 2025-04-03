package com.example.aginvest.controller.viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Stack;

public class LogoController {
    private static final String APP_TITLE = "Invest7";
    private static final double WINDOW_WIDTH = 360;
    private static final double WINDOW_HEIGHT = 640;

    // Pilha para armazenar o histórico de navegação
    private static Stack<Scene> sceneHistory = new Stack<>();

    @FXML private Button cadastroButton;
    @FXML private Button loginButton;
    @FXML private Button simulacaoButton;
    @FXML private Button voltarButton; // Novo botão adicionado


    @FXML
    private void handleCadastro() {
        loadScene("/com/example/aginvest/Cadastro.fxml", "Cadastro");
    }

    @FXML
    private void handleLogin() {
        loadScene("/com/example/aginvest/Login.fxml", "Login");
    }

    @FXML
    private void handleSimulacao() {
        loadScene("/com/example/aginvest/SimuPrevia.fxml", "Simulação Prévia");
    }

    @FXML
    private void handleVoltar() {
        if (!sceneHistory.isEmpty()) {
            Scene previousScene = sceneHistory.pop();
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            stage.setScene(previousScene);
        } else {
            showAlert("Aviso", "Não há página anterior",
                    "Você já está na página inicial.", Alert.AlertType.INFORMATION);
        }
    }

    private void loadScene(String fxmlPath, String screenTitle) {
        try {
          

            Parent root = loader.load();
            Scene newScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            currentStage.setScene(newScene);
            currentStage.setTitle(APP_TITLE + " - " + screenTitle);
            currentStage.show();

        } catch (IOException e) {
            showError("Erro ao carregar tela",
                    "Erro ao carregar " + screenTitle + ": " + e.getMessage());
        }
    }

    private void showError(String title, String message) {
        showAlert(title, null, message, Alert.AlertType.ERROR);
    }

    private void showAlert(String title, String header, String content, Alert.AlertType type) {
        System.out.println(title + ": " + content);

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}