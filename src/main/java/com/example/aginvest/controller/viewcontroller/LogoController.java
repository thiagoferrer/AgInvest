package com.example.aginvest.controller.viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class LogoController {

    @FXML
    private Button comecarButton;

    @FXML
    private void initialize() {
        // Configurações iniciais, se necessário
        // Exemplo: comecarButton.setDisable(true); // Desativa o botão até uma condição
    }

    @FXML
    private void onclickcomecar() {
        try {
            // Carrega o arquivo FXML da tela de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            if (loader.getLocation() == null) {
                System.err.println("Erro: Login.fxml não encontrado!");
                return;
            }
            Scene loginScene = new Scene(loader.load(), 360, 640); // Define as dimensões diretamente

            // Carrega o CSS para estilizar a tela de login
            String css = getClass().getResource("styles.css").toExternalForm();
            if (css == null) {
                System.err.println("Erro: styles.css não encontrado!");
            } else {
                loginScene.getStylesheets().add(css);
            }

            // Obtém o Stage atual (janela) a partir do botão
            Stage stage = (Stage) comecarButton.getScene().getWindow();

            // Define a nova cena (tela de login) no Stage
            stage.setScene(loginScene);
            stage.setTitle("Tela de Login");
            stage.setWidth(360);  // Largura
            stage.setHeight(640); // Altura
            stage.centerOnScreen(); // Centraliza a janela
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar a tela de login: " + e.getMessage());
            // Aqui você pode adicionar um alerta para o usuário, se desejar
        }
    }
}