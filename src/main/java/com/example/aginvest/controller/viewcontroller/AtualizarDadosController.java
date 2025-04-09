package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.DataValidate;
import com.example.aginvest.controller.user.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AtualizarDadosController {
    // Botões do cabeçalho
    @FXML private Button homeButton;
    @FXML private Button faqButton;
    @FXML private Button contaButton;
    @FXML private Button voltarButton;

    // Campos do formulário
    @FXML private TextField nomeField;
    @FXML private ComboBox<String> generoCombo;
    @FXML private TextField dataNascField;
    @FXML private TextField enderecoField;
    @FXML private TextField emailField;

    // Labels de erro
    @FXML private Label errorMessage;
    @FXML private Label nomeError;
    @FXML private Label generoError;
    @FXML private Label dataNascError;
    @FXML private Label enderecoError;

    @FXML
    public void initialize() {
        // Configura listeners para limpar erros quando o usuário começa a digitar
        nomeField.setOnKeyTyped(e -> clearFieldError(nomeField, nomeError));
        generoCombo.setOnAction(e -> clearFieldError(generoCombo, generoError));
        dataNascField.setOnKeyTyped(e -> clearFieldError(dataNascField, dataNascError));
        enderecoField.setOnKeyTyped(e -> clearFieldError(enderecoField, enderecoError));
    }

    @FXML
    private void onClickHome() {
        carregarTela("/com/example/aginvest/home.fxml", "Home - Invest7");
    }

    @FXML
    private void onClickConfirmar() {
        atualizarDados();
    }

    @FXML
    private void onClickVoltar() {
        carregarTela("/com/example/aginvest/Conta.fxml", "Conta - Invest7");
    }

    private void atualizarDados() {
        try {
            // Limpa todos os erros anteriores
            clearAllErrors();

            // Obtém os valores dos campos
            String nome = nomeField.getText().trim();
            String genero = generoCombo.getValue();
            String dataNasc = dataNascField.getText().trim();
            String endereco = enderecoField.getText().trim();

            // Validação dos campos
            boolean isValid = true;

            if (nome.isEmpty()) {
                showFieldError(nomeField, nomeError, "Nome é obrigatório");
                isValid = false;
            }

            if (genero == null) {
                showFieldError(generoCombo, generoError, "Gênero é obrigatório");
                isValid = false;
            }

            DataValidate dataValidator = new DataValidate();
            String dataValidada = dataValidator.validarData(dataNasc);
            if (dataValidada == null) {
                showFieldError(dataNascField, dataNascError, "Data inválida (use aaaa/mm/dd)");
                isValid = false;
            }

            if (endereco.isEmpty()) {
                showFieldError(enderecoField, enderecoError, "Endereço é obrigatório");
                isValid = false;
            } else if (endereco.length() < 10) {
                showFieldError(enderecoField, enderecoError, "Endereço muito curto");
                isValid = false;
            }

            if (!isValid) {
                showError("Por favor, corrija os campos destacados");
                return;
            }

            // Tenta atualizar os dados
            UserController userController = new UserController();
            boolean atualizado = userController.uptadeUser(nome, genero, endereco, dataValidada);

            if (atualizado) carregarTela("/com/example/aginvest/Conta.fxml", "Conta - Invest7");
            else showError("Erro ao atualizar dados. Tente novamente.");


        } catch (Exception e) {
            e.printStackTrace();
            showError("Erro inesperado: " + e.getMessage());
        }
    }

    // Métodos auxiliares para tratamento de erros
    private void showFieldError(TextField field, Label errorLabel, String message) {
        field.setStyle("-fx-border-color: #FF0000; -fx-border-radius: 8;");
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void showFieldError(ComboBox<?> combo, Label errorLabel, String message) {
        combo.setStyle("-fx-border-color: #FF0000; -fx-border-radius: 8;");
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void clearFieldError(TextField field, Label errorLabel) {
        field.setStyle("-fx-border-color: #D3D3D3; -fx-border-radius: 8;");
        errorLabel.setVisible(false);
    }

    private void clearFieldError(ComboBox<?> combo, Label errorLabel) {
        combo.setStyle("-fx-border-color: #D3D3D3; -fx-border-radius: 8;");
        errorLabel.setVisible(false);
    }

    private void clearAllErrors() {
        errorMessage.setVisible(false);

        clearFieldError(nomeField, nomeError);
        clearFieldError(generoCombo, generoError);
        clearFieldError(dataNascField, dataNascError);
        clearFieldError(enderecoField, enderecoError);
    }

    private void showError(String message) {
        errorMessage.setText(message);
        errorMessage.setVisible(true);
    }

    // Método genérico para carregar telas
    private void carregarTela(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) homeButton.getScene().getWindow();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showError("Erro ao carregar a tela: " + e.getMessage());
        }
    }
}