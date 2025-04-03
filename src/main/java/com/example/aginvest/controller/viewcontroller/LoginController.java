package com.example.aginvest.controller.viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    private void handleSignIn() {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validação simples: verifica se os campos estão vazios
        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Erro: Por favor, preencha todos os campos.");
            return;
        }

        System.out.println("Botão Sign In clicado! E-mail: " + email + ", Senha: " + password);

        // Navegação para a tela Home.fxml
        try {
            // Carrega o arquivo Home.fxml
            FXMLLoader loader = new FXMLLoader();
            // Ajusta o caminho para o local correto
            loader.setLocation(getClass().getResource("/com/example/aginvest/Home.fxml"));
            if (loader.getLocation() == null) {
                throw new IOException("Não foi possível encontrar o arquivo Home.fxml no caminho especificado.");
            }
            Scene homeScene = new Scene(loader.load(), 360, 640);

            // Obtém o stage atual a partir de um dos nós da cena (neste caso, emailField)
            Stage stage = (Stage) emailField.getScene().getWindow();

            // Define a nova cena no stage
            stage.setScene(homeScene);
            stage.setTitle("Invest7 - Home");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela Home: " + e.getMessage());
        }
    }

    @FXML
    private void handleForgotPassword() {
        System.out.println("Link Esqueci a senha clicado!");
        // Adicione aqui a lógica para recuperação de senha
    }
}