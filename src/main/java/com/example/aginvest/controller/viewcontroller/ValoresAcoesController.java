package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.BuscarValoresAcoes;
import com.example.aginvest.model.ValoresAcoes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ValoresAcoesController {
    // Botões do cabeçalho
    @FXML private Button homeButton;
    @FXML private Button faqButton;
    @FXML private Button contaButton;
    @FXML private Button backButton;

    @FXML private Label acao1Ticker;
    @FXML private Label acao1Preco;
    @FXML private Label acao1Maxima;
    @FXML private Label acao1Variacao;

    @FXML
    private void onClickHome() {
        carregarTela(homeButton, "/com/example/aginvest/home.fxml", "Home - Invest7");
    }

    @FXML
    private void onClickFaq() {
        carregarTela(faqButton, "/com/example/aginvest/faq.fxml", "FAQ - Invest7");
    }

    @FXML
    private void onClickConta() {
        carregarTela(contaButton, "/com/example/aginvest/Conta.fxml", "Conta - Invest7");
    }


    @FXML private void onClickBackToHome() {
        carregarTela(backButton, "/com/example/aginvest/home.fxml", "Home - Invest7");
    }

    @FXML
    private void initialize() throws Exception {
        setDados();
    }
    private void carregarTela(Button botaoOrigem, String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) botaoOrigem.getScene().getWindow();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            mostrarAlerta("Erro", "Não foi possível carregar a tela: " + fxmlPath + "\nErro: " + e.getMessage(), Alert.AlertType.INFORMATION);
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType information) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void setDados() throws Exception {
        BuscarValoresAcoes valoresAcoes = new BuscarValoresAcoes();
        List<ValoresAcoes> acoes = valoresAcoes.buscarAcoes();

        String acao = acao1Ticker.getText();
        String valorMax = acao1Maxima.getText();
        String preco = acao1Preco.getText();
        String variacao = acao1Variacao.getText();

        acao1Ticker.setText(acao);
        acao1Maxima.setText(valorMax);
        acao1Preco.setText(preco);
        acao1Variacao.setText(variacao);


    }
}
