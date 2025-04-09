package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.user.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button fazerLogin;

    @FXML
    private Button voltarButton;

    @FXML
    private TextField emailFieldLogin;

    @FXML
    private TextField senhaFielLogin;

    @FXML
    private Label errorMessage; // Novo campo para a mensagem de erro

    @FXML private ImageView logoImage;

    public void initialize() {
        Image logo = new Image(getClass().getResource("/image/Logo.png").toExternalForm());
        logoImage.setImage(logo);
    }

    public void realizarLogin() {
        try {
            // Limpa os estilos de erro antes de validar
            clearErrorStyles();

            String email = emailFieldLogin.getText().trim();
            String senha = senhaFielLogin.getText().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                showError("Email e senha são obrigatórios!");
                return;
            }

            if (!email.contains("@") || !email.contains(".")) {
                showError("Por favor, insira um email válido!");
                return;
            }

            UserController userLogin = new UserController();
            String loginRealizado = userLogin.login(email, senha);

            if (loginRealizado != null) {
                carregarTelaPrincipal();
            } else {
                showError("Login falhou! Verifique suas credenciais.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erro durante o login: " + e.getMessage());
        }
    }

    private void showError(String message) {
        errorMessage.setText(message);
        errorMessage.setVisible(true);

        // Aplica borda vermelha nos campos
        emailFieldLogin.setStyle("-fx-border-color: #FF0000; -fx-border-radius: 8;");
        senhaFielLogin.setStyle("-fx-border-color: #FF0000; -fx-border-radius: 8;");
    }

    private void carregarTelaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/Home.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) fazerLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showError("Erro ao carregar tela principal: " + e.getMessage());
        }
    }

    private void clearErrorStyles() {
        errorMessage.setVisible(false);
        emailFieldLogin.setStyle("-fx-border-color: #87CEFA; -fx-border-radius: 8;");
        senhaFielLogin.setStyle("-fx-border-color: #87CEFA; -fx-border-radius: 8;");
    }

    @FXML
    private void handleVoltar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/Logo.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            Scene scene = new Scene(root, 360, 640);
            stage.setScene(scene);
            stage.setTitle("Invest7");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erro ao carregar a tela inicial: " + e.getMessage());
        }
    }
}