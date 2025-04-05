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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ValoresAcoesController {
    // Botões do cabeçalho
    @FXML private Button homeButton;
    @FXML private Button faqButton;
    @FXML private Button contaButton;
    @FXML private Button backButton;

    // Container para as ações
    @FXML private VBox acoesContainer;

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

    @FXML
    private void onClickBackToHome() {
        carregarTela(backButton, "/com/example/aginvest/home.fxml", "Home - Invest7");
    }

    @FXML
    private void initialize() {
        try {
            setDados();
        } catch (Exception e) {
            mostrarAlerta("Erro", "Não foi possível carregar os dados das ações: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
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
            mostrarAlerta("Erro", "Não foi possível carregar a tela: " + fxmlPath + "\nErro: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void setDados() throws Exception {
        BuscarValoresAcoes valoresAcoes = new BuscarValoresAcoes();
        List<ValoresAcoes> acoes = valoresAcoes.buscarAcoes();

        if (acoes.isEmpty()) {
            System.out.println("Nenhuma ação retornada pela API");
            return;
        }

        GridPane gridPane = new GridPane();
        gridPane.setHgap(16);
        gridPane.setVgap(16);
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);

        int colunas = 2; // Sempre 2 colunas
        int linhas = (int) Math.ceil((double) acoes.size() / colunas);

        for (int i = 0; i < acoes.size(); i++) {
            ValoresAcoes acao = acoes.get(i);
            int linha = i / colunas;
            int coluna = i % colunas;

            VBox cardAcao = criarCardAcao(acao);
            gridPane.add(cardAcao, coluna, linha);
        }

        acoesContainer.getChildren().add(gridPane);
    }

    private VBox criarCardAcao(ValoresAcoes acao) {
        VBox card = new VBox(8);
        card.setPrefWidth(140);
        card.setPrefHeight(140);
        card.setAlignment(javafx.geometry.Pos.CENTER);
        card.setStyle("-fx-background-color: #1E90FF; -fx-background-radius: 12;");

        // Formata os valores
        String precoFormatado = String.format("Preço: R$%.2f", acao.getRegularMarketPrice());
        String maximaFormatada = String.format("Máxima: R$%.2f", acao.getRegularMarketDayHigh());
        String variacaoFormatada = String.format("Variação: %.2f%%", acao.getRegularMarketChange());

        // Define a cor da variação
        String corVariacao = acao.getRegularMarketChange() >= 0 ? "#1FCE52" : "#FF4C4C";

        Label tickerLabel = new Label(acao.getSymbol());
        tickerLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 16; -fx-font-weight: bold;");

        Label precoLabel = new Label(precoFormatado);
        precoLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 14;");

        Label maximaLabel = new Label(maximaFormatada);
        maximaLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 14;");

        Label variacaoLabel = new Label(variacaoFormatada);
        variacaoLabel.setStyle("-fx-text-fill: " + corVariacao + "; -fx-font-size: 14;");

        card.getChildren().addAll(tickerLabel, precoLabel, maximaLabel, variacaoLabel);
        return card;
    }
}