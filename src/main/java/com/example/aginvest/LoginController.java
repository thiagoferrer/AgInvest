package com.example.aginvest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private Button comecarButton; // Correspondente ao fx:id="comecarButton" no FXML

    @FXML
    public void onclickhome() { // Método vinculado ao onAction="#onclickcomecar"
        try {
            // Carregar o FXML da tela Home
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent root = loader.load();

            // Criar uma nova cena para a tela Home
            Scene homeScene = new Scene(root);

            // Obter o palco (stage) atual a partir do botão
            Stage stage = (Stage) comecarButton.getScene().getWindow();

            // Definir a nova cena no palco
            stage.setScene(homeScene);
            stage.setTitle("Home - Invest7");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela Home: " + e.getMessage());
        }
    }
}