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

    // Labels das ações
    @FXML private Label acao1Ticker;
    @FXML private Label acao1Preco;
    @FXML private Label acao1Maxima;
    @FXML private Label acao1Variacao;

    @FXML private Label acao2Ticker;
    @FXML private Label acao2Preco;
    @FXML private Label acao2Maxima;
    @FXML private Label acao2Variacao;

    @FXML private Label acao3Ticker;
    @FXML private Label acao3Preco;
    @FXML private Label acao3Maxima;
    @FXML private Label acao3Variacao;

    @FXML private Label acao4Ticker;
    @FXML private Label acao4Preco;
    @FXML private Label acao4Maxima;
    @FXML private Label acao4Variacao;

    @FXML private Label acao5Ticker;
    @FXML private Label acao5Preco;
    @FXML private Label acao5Maxima;
    @FXML private Label acao5Variacao;

    @FXML private Label acao6Ticker;
    @FXML private Label acao6Preco;
    @FXML private Label acao6Maxima;
    @FXML private Label acao6Variacao;

    // Arrays para facilitar o acesso aos componentes
    private Label[] tickerLabels;
    private Label[] precoLabels;
    private Label[] maximaLabels;
    private Label[] variacaoLabels;

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
        // Inicializa os arrays
        tickerLabels = new Label[]{acao1Ticker, acao2Ticker, acao3Ticker, acao4Ticker, acao5Ticker, acao6Ticker};
        precoLabels = new Label[]{acao1Preco, acao2Preco, acao3Preco, acao4Preco, acao5Preco, acao6Preco};
        maximaLabels = new Label[]{acao1Maxima, acao2Maxima, acao3Maxima, acao4Maxima, acao5Maxima, acao6Maxima};
        variacaoLabels = new Label[]{acao1Variacao, acao2Variacao, acao3Variacao, acao4Variacao, acao5Variacao, acao6Variacao};

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

        // Preenche os dados para cada ação
        for (int i = 0; i < Math.min(acoes.size(), 6); i++) {
            ValoresAcoes acao = acoes.get(i);

            // Formata os valores
            String precoFormatado = String.format("Preço: R$%.2f", acao.getRegularMarketPrice());
            String maximaFormatada = String.format("Máxima: R$%.2f", acao.getRegularMarketDayHigh());
            String variacaoFormatada = String.format("Variação: %.2f%%", acao.getRegularMarketChange());

            // Define a cor da variação
            String corVariacao = acao.getRegularMarketChange() >= 0 ? "#1FCE52" : "#FF4C4C";
            String estiloVariacao = "-fx-text-fill: " + corVariacao + "; -fx-font-size: 14;";

            // Atualiza os labels
            tickerLabels[i].setText(acao.getSymbol());
            precoLabels[i].setText(precoFormatado);
            maximaLabels[i].setText(maximaFormatada);
            variacaoLabels[i].setText(variacaoFormatada);
            variacaoLabels[i].setStyle(estiloVariacao);
        }
    }
}