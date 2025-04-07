package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.CalculadoraVariavel;
import com.example.aginvest.controller.user.UserController;
import com.example.aginvest.model.produtos.Acoes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class ResultadoAcoesController {
    @FXML
    private VBox ativosContainer;
    @FXML
    private VBox ativosSection;
    @FXML
    private VBox graficosContainer;

    // Botões do cabeçalho
    @FXML private Button homeButton;
    @FXML private Button faqButton;
    @FXML private Button contaButton;

    // Botões de ação
    @FXML private Button backButton;
    @FXML private Button newSimulationButton;


    @FXML
    private void onClickHome() {
        carregarTela("/com/example/aginvest/home.fxml", "Home - Invest7");
    }

    @FXML private void onClickVoltar(){carregarTela("/com/example/aginvest/Acoes.fxml", "Acoes- Invest7"); }

    @FXML
    public void initialize() {
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
    }


    private List<Acoes> resultadosSimulacao;

    private void mostrarAlerta(String titulo, String mensagem, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public void CalcularAcoes(Acoes acoes) {
        CalculadoraVariavel calculadoraVariavel = new CalculadoraVariavel();
        List<Acoes> resultados = calculadoraVariavel.simularAcao(acoes);
        this.resultadosSimulacao = resultados;

        // Limpa os containers antes de adicionar novos elementos
        ativosContainer.getChildren().clear();
        graficosContainer.getChildren().clear();

        // Cria e adiciona o gráfico
        criarGraficoSaldoFinal(resultados);

        // Adiciona cada ativo ao container
        for (Acoes acao : resultados) {
            VBox ativoBox = criarAtivoBox(acao);
            ativosContainer.getChildren().add(ativoBox);
        }
    }

    private void criarGraficoSaldoFinal(List<Acoes> resultados) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        // Configurações de tamanho e espaçamento
        barChart.setPrefSize(400, 250);
        barChart.setCategoryGap(10);
        barChart.setBarGap(3);

        barChart.setTitle("Saldo Final por Ativo");
        barChart.setLegendVisible(false);
        barChart.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8;");

        xAxis.setLabel("Ativo");
        yAxis.setLabel("Valor (R$)");

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (Acoes acao : resultados) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(acao.getNome(), acao.getSaldoFinal());
            series.getData().add(data);

            data.nodeProperty().addListener((ov, oldNode, newNode) -> {
                if (newNode != null) {
                    String cor = acao.getSaldoFinal() >= 0 ? "#1FCE52" : "#FF4500";
                    newNode.setStyle("-fx-bar-fill: " + cor + "; -fx-pref-width: 30px;");
                }
            });
        }

        barChart.getData().add(series);
        graficosContainer.getChildren().add(barChart);
    }

    private VBox criarAtivoBox(Acoes acao) {
        VBox box = new VBox();
        box.setSpacing(4);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPrefWidth(300);
        box.setStyle("-fx-border-color: #1E90FF; -fx-border-radius: 8; -fx-border-width: 1; -fx-padding: 8;");

        Label nomeLabel = new Label(acao.getNome());
        nomeLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 14; -fx-font-weight: bold;");

        Label qtdLabel = new Label("Quantidade Total Cotas: " + acao.getQtdAcoes());
        qtdLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        Label valorInvestidoLabel = new Label("Valor Investido: R$" + String.format("%,.2f", acao.getValorInvestido()));
        valorInvestidoLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        Label totalCompraLabel = new Label("Total compra: R$" + String.format("%,.2f", acao.getCustoTotalCompra()));
        totalCompraLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        Label totalVendaLabel = new Label("Total venda: R$" + String.format("%,.2f", acao.getValorTotalVenda()));
        totalVendaLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        Label saldoFinalLabel = new Label("Saldo Final: R$" + String.format("%,.2f", acao.getSaldoFinal()));
        saldoFinalLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        Label trocoLabel = new Label("Troco Valor Investido: R$" + String.format("%,.2f", acao.getTroco()));
        trocoLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        box.getChildren().addAll(
                nomeLabel,
                qtdLabel,
                valorInvestidoLabel,
                totalCompraLabel,
                totalVendaLabel,
                saldoFinalLabel,
                trocoLabel
        );

        return box;
    }

    @FXML
    private void gerarCsv() {
        if (resultadosSimulacao == null || resultadosSimulacao.isEmpty()) {
            mostrarAlerta("Erro", "Nenhum dado para exportar. Realize uma simulação primeiro.", AlertType.WARNING);
            return;
        }

        try {
            String nomeArquivo = "resultado_acoes.csv";
            PrintWriter writer = new PrintWriter(nomeArquivo);

            // Cabeçalho
            writer.println("Nome,Quantidade Total Cotas,Valor Investido,Total Compra,Total Venda,Saldo Final,Troco");

            // Dados
            for (Acoes acao : resultadosSimulacao) {
                writer.printf("%s,%d,%.2f,%.2f,%.2f,%.2f,%.2f%n",
                        acao.getNome(),
                        acao.getQtdAcoes(),
                        acao.getValorInvestido(),
                        acao.getCustoTotalCompra(),
                        acao.getValorTotalVenda(),
                        acao.getSaldoFinal(),
                        acao.getTroco());
            }

            writer.close();

            mostrarAlerta("Sucesso", "CSV gerado com sucesso: " + nomeArquivo, AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao gerar o CSV: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
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