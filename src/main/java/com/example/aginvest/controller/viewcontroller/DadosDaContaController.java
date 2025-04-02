package com.example.aginvest.controller.viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class DadosDaContaController {

    // Botões do cabeçalho
    @FXML
    private Button homeButton;
    @FXML
    private Button faqButton;
    @FXML
    private Button contaButton;

    // Labels de informação
    @FXML
    private Label nomeLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label aniversarioLabel;
    @FXML
    private Label generoLabel;
    @FXML
    private Label enderecoLabel;
    @FXML
    private Label cpfLabel;
    @FXML
    private Label perfilLabel;

    // Botões de ação
    @FXML
    private Button voltarButton;
    @FXML
    private Button editarButton;

    // Método para o botão "Home"
    @FXML
    public void onClickHome() {
        try {
            java.net.URL fxmlLocation = getClass().getResource("Home.fxml");
            if (fxmlLocation == null) {
                throw new IOException("Não foi possível encontrar o arquivo Home.fxml");
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            Scene homeScene = new Scene(root);
            Stage stage = (Stage) homeButton.getScene().getWindow();
            stage.setScene(homeScene);
            stage.setTitle("Home - Invest7");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela Home: " + e.getMessage());
        }
    }

    // Método para o botão "FAQ"
    @FXML
    public void onClickFaq() {
        System.out.println("Botão FAQ clicado!");
        // Adicione lógica para navegar para a tela de FAQ (ex.: FAQ.fxml)
    }

    // Método para o botão "Conta"
    @FXML
    public void onClickConta() {
        try {
            java.net.URL fxmlLocation = getClass().getResource("Conta.fxml");
            if (fxmlLocation == null) {
                throw new IOException("Não foi possível encontrar o arquivo Conta.fxml");
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            Scene contaScene = new Scene(root);
            Stage stage = (Stage) contaButton.getScene().getWindow();
            stage.setScene(contaScene);
            stage.setTitle("Configurações de Conta - Invest7");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela Configurações de Conta: " + e.getMessage());
        }
    }

    // Método para o botão "Voltar"
    @FXML
    public void onClickVoltar() {
        try {
            java.net.URL fxmlLocation = getClass().getResource("Conta.fxml");
            if (fxmlLocation == null) {
                throw new IOException("Não foi possível encontrar o arquivo Conta.fxml");
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            Scene contaScene = new Scene(root);
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            stage.setScene(contaScene);
            stage.setTitle("Configurações de Conta - Invest7");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela Configurações de Conta: " + e.getMessage());
        }
    }

    // Método para o botão "Editar dados"
    @FXML
    public void onClickEditar() {
        try {
            java.net.URL fxmlLocation = getClass().getResource("EditarDados.fxml");
            if (fxmlLocation == null) {
                throw new IOException("Não foi possível encontrar o arquivo EditarDados.fxml");
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            Scene editarDadosScene = new Scene(root);
            Stage stage = (Stage) editarButton.getScene().getWindow();
            stage.setScene(editarDadosScene);
            stage.setTitle("Editar Dados - Invest7");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela Editar Dados: " + e.getMessage());
        }
    }

    // Método para atualizar os dados exibidos (opcional, caso os dados sejam dinâmicos)
    public void setDados(String nome, String email, String aniversario, String genero, String endereco, String cpf, String perfil) {
        nomeLabel.setText("Nome Completo: " + nome);
        emailLabel.setText("E-mail: " + email);
        aniversarioLabel.setText("Aniversário: " + aniversario);
        generoLabel.setText("Gênero: " + genero);
        enderecoLabel.setText("Endereço: " + endereco);
        cpfLabel.setText("CPF: " + cpf);
        perfilLabel.setText("Perfil Investidor: " + perfil);
    }
}