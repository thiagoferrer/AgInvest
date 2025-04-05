package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.user.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class DeletarContaController {

    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private Button homeButton;
    @FXML private Button faqButton;
    @FXML private Button contaButton;
    @FXML private Label emailErroLabel;
    @FXML private Label senhaErroLabel;
    @FXML private Label erroGeralLabel;

    @FXML
    private void initialize() {
        System.out.println("DeletarContaController inicializado");

        // Limpar mensagens de erro quando o usuário começar a digitar
        emailField.textProperty().addListener((obs, oldVal, newVal) -> {
            emailErroLabel.setVisible(false);
            erroGeralLabel.setVisible(false);
        });

        senhaField.textProperty().addListener((obs, oldVal, newVal) -> {
            senhaErroLabel.setVisible(false);
            erroGeralLabel.setVisible(false);
        });
    }

    @FXML
    private void onClickHome() {
        carregarTela("/com/example/aginvest/home.fxml", "AgInvest - Home");
    }

    @FXML
    private void onClickFaq() {
        carregarTela("/com/example/aginvest/faq.fxml", "AgInvest - FAQ");
    }

    @FXML
    private void onClickConta() {
        carregarTela("/com/example/aginvest/conta.fxml", "AgInvest - Minha Conta");
    }

    @FXML
    private void onClickDeletarConta() {
        try {
            // Resetar mensagens de erro
            emailErroLabel.setVisible(false);
            senhaErroLabel.setVisible(false);
            erroGeralLabel.setVisible(false);

            String email = emailField.getText().trim();
            String senha = senhaField.getText().trim();

            // Validação dos campos
            boolean camposValidos = true;

            if (email.isEmpty()) {
                emailErroLabel.setText("E-mail é obrigatório");
                emailErroLabel.setVisible(true);
                camposValidos = false;
            } else if (!email.matches("^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                emailErroLabel.setText("E-mail inválido");
                emailErroLabel.setVisible(true);
                camposValidos = false;
            }

            if (senha.isEmpty()) {
                senhaErroLabel.setText("Senha é obrigatória");
                senhaErroLabel.setVisible(true);
                camposValidos = false;
            }

            if (!camposValidos) {
                return;
            }

            // Confirmação antes de deletar
            if (!mostrarConfirmacao("Confirmação", "Tem certeza que deseja deletar sua conta permanentemente?")) {
                return;
            }

            UserController userController = new UserController();
            boolean userDeletado = userController.deletarConta(email, senha);

            if (userDeletado) {
                mostrarAlerta("Sucesso", "Conta deletada com sucesso!", Alert.AlertType.INFORMATION);
                carregarTela("/com/example/aginvest/Logo.fxml", "Logo - Invest7");
            } else {
                erroGeralLabel.setText("Falha ao deletar conta. Verifique seus dados.");
                erroGeralLabel.setVisible(true);
            }
        } catch (Exception e) {
            System.err.println("Erro ao deletar conta: " + e.getMessage());
            erroGeralLabel.setText("Erro ao processar solicitação: " + e.getMessage());
            erroGeralLabel.setVisible(true);
        }
    }

    @FXML
    private void onClickVoltar() {
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.close();
    }

    private void carregarTela(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) homeButton.getScene().getWindow();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            erroGeralLabel.setText("Não foi possível carregar a tela: " + e.getMessage());
            erroGeralLabel.setVisible(true);
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private boolean mostrarConfirmacao(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }
}