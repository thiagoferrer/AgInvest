package com.example.aginvest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class DeletarContaController {

    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private Button homeButton;
    @FXML private Button faqButton;
    @FXML private Button contaButton;

    @FXML
    private void initialize() {
        System.out.println("DeletarContaController inicializado");
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
        if (!validarCampos()) {
            mostrarAlerta("Erro de Validação",
                    "Por favor, preencha todos os campos corretamente.",
                    Alert.AlertType.WARNING);
            return;
        }

        boolean confirmacao = mostrarConfirmacao("Confirmação",
                "Tem certeza que deseja deletar sua conta permanentemente?\n" +
                        "Todos os seus dados serão perdidos e esta ação não pode ser desfeita.");

        if (confirmacao) {
            if (deletarConta(emailField.getText(), senhaField.getText())) {
                mostrarAlerta("Conta Deletada",
                        "Sua conta foi removida com sucesso.",
                        Alert.AlertType.INFORMATION);
                carregarTela("/com/example/aginvest/login.fxml", "AgInvest - Login");
            } else {
                mostrarAlerta("Falha na Exclusão",
                        "Não foi possível deletar a conta. Verifique suas credenciais.",
                        Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void onClickVoltar() {
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.close();
    }

    private boolean validarCampos() {
        String email = emailField.getText().trim();
        String password = senhaField.getText().trim();

        return !email.isEmpty() &&
                !password.isEmpty() &&
                email.matches("^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    private boolean deletarConta(String email, String password) {
        try {
            // Simulação de operação demorada
            Thread.sleep(1500);

            // Aqui você implementaria a lógica real de deleção
            // Retornaria true se a deleção foi bem-sucedida
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    private void carregarTela(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Obtém a janela atual e a usa para a nova cena
            Stage stage = (Stage) homeButton.getScene().getWindow();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            mostrarAlerta("Erro",
                    "Não foi possível carregar a tela: " + e.getMessage(),
                    Alert.AlertType.ERROR);
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