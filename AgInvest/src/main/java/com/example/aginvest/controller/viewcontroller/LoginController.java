package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.user.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button fazerLogin;

    @FXML
    private TextField emailFieldLogin;

    @FXML
    private PasswordField senhaFielLogin;

    public void realizarLogin(){
        try {
            String email = emailFieldLogin.getText().trim();
            String senha = senhaFielLogin.getText().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                // Mostrar alerta para o usuário
                System.err.println("Email e senha são obrigatórios!");
                return;
            }

            UserController userLogin = new UserController();
            String loginRealizado = userLogin.login(email, senha);

            if (loginRealizado != null) {
                // Login bem-sucedido - navegar para a próxima tela
                carregarTelaPrincipal();
            } else {
                // Mostrar mensagem de erro de login
                System.err.println("Login falhou! Verifique suas credenciais.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro durante o login: " + e.getMessage());
        }
    }

    private void carregarTelaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/RendaFixa.fxml"));
            Scene mainScene = new Scene(loader.load(), 360, 640);

            // Aplicar CSS se necessário
            String css = getClass().getResource("/com/example/aginvest/styles.css").toExternalForm();
            if (css != null) {
                mainScene.getStylesheets().add(css);
            }

            Stage stage = (Stage) fazerLogin.getScene().getWindow();
            stage.setScene(mainScene);
            stage.setTitle("Tela Principal");
            stage.setWidth(360);
            stage.setHeight(640);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar tela principal: " + e.getMessage());
        }
    }

}
