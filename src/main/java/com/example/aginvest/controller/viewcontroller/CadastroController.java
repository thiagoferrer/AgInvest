package com.example.aginvest.controller.viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CadastroController {

    @FXML
    private TextField nomeField;

    @FXML
    private TextField cpfField;

    @FXML
    private ComboBox<String> generoCombo;

    @FXML
    private TextField dataNascimentoField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField enderecoField;

    @FXML
    private TextField cepField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Button voltarButton;

    @FXML
    private Button cadastrarButton;

    @FXML
    private void handleVoltar() {
        try {
            // Carrega a tela inicial (Logo.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/Logo.fxml"));
            Parent root = loader.load();

            // Obtém o palco atual a partir do botão
            Stage stage = (Stage) voltarButton.getScene().getWindow();

            // Cria uma nova cena e define no palco
            Scene scene = new Scene(root, 360, 640);
            stage.setScene(scene);
            stage.setTitle("Invest7");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela inicial: " + e.getMessage());
        }
    }

    @FXML
    private void handleCadastrar() {
        // Coleta os dados dos campos
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String genero = generoCombo.getValue();
        String dataNascimento = dataNascimentoField.getText();
        String email = emailField.getText();
        String endereco = enderecoField.getText();
        String cep = cepField.getText();
        String senha = senhaField.getText();

        // Imprime os dados no console (você pode adicionar lógica de cadastro aqui)
        System.out.println("Botão Cadastrar clicado!");
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Gênero: " + genero);
        System.out.println("Data de Nascimento: " + dataNascimento);
        System.out.println("E-mail: " + email);
        System.out.println("Endereço: " + endereco);
        System.out.println("CEP: " + cep);
        System.out.println("Senha: " + senha);
    }
}