package com.example.aginvest.controller.viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class RendaFixaController {

    // Botões do cabeçalho
    @FXML
    private Button homeButton;
    @FXML
    private Button faqButton;
    @FXML
    private Button contaButton;

    // Campos de entrada
    @FXML
    private TextField capitalInicialField;
    @FXML
    private TextField aporteMensalField;
    @FXML
    private TextField prazoField;

    // Botões de seleção
    @FXML
    private Button selicButton;
    @FXML
    private Button cdiButton;
    @FXML
    private Button ipcaButton;

    // Botões de ação
    @FXML
    private Button voltarButton;
    @FXML
    private Button calcularButton;

    @FXML
    private void onClickHome() {
        carregarTela("/com/example/aginvest/home.fxml", "Home - Invest7");
    }

    @FXML
    public void initialize() {
        contaButton.setOnAction(actionEvent -> {
            try {
                // 1. Carrega o novo arquivo FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/Conta.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                // 4. Define a nova cena no palco
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erro ao clicar no botao de menu de dados do usuario");
            }
        });

        faqButton.setOnAction(actionEvent -> {
            try {
                // 1. Carrega o novo arquivo FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/Faq.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                // 4. Define a nova cena no palco
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erro ao clicar no botao de menu de FAQ");
            }
        });
    }

    // Métodos para os botões de seleção
    @FXML
    public void onClickSelic() {
        System.out.println("Selic selecionado: 13.15%");
        // Adicione lógica para selecionar a taxa Selic
    }

    @FXML
    public void onClickCdi() {
        System.out.println("CDI selecionado: 14.15%");
        // Adicione lógica para selecionar a taxa CDI
    }

    @FXML
    public void onClickIpca() {
        System.out.println("IPCA selecionado: 5.19%");
        // Adicione lógica para selecionar a taxa IPCA
    }

    // Método para o botão "Voltar"
    @FXML
    public void onClickVoltar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent root = loader.load();
            Scene homeScene = new Scene(root);
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            stage.setScene(homeScene);
            stage.setTitle("Home - Invest7");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela Home: " + e.getMessage());
        }
    }

    // Método para o botão "Calcular"
    @FXML
    public void onClickCalcular() {
        System.out.println("Botão Calcular clicado!");
        // Adicione lógica para realizar o cálculo com base nos valores inseridos
        String capitalInicial = capitalInicialField.getText();
        String aporteMensal = aporteMensalField.getText();
        String prazo = prazoField.getText();
        System.out.println("Capital Inicial: " + capitalInicial);
        System.out.println("Aporte Mensal: " + aporteMensal);
        System.out.println("Prazo: " + prazo + " meses");
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
            e.printStackTrace();
        }
    }
}