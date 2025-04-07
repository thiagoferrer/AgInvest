package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.CalculadoraVariavel;
import com.example.aginvest.model.produtos.Acoes;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import java.util.List;

public class ResultadoAcoesController {
    @FXML
    private VBox ativosContainer;
    @FXML
    private VBox ativosSection;
    @FXML
    private VBox graficosContainer;

    @FXML
    public void initialize() {
        // Inicialização se necessário
    }

    public void CalcularAcoes(Acoes acoes) {
        CalculadoraVariavel calculadoraVariavel = new CalculadoraVariavel();
        List<Acoes> resultados = calculadoraVariavel.simularAcao(acoes);

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
        barChart.setPrefSize(400, 250);  // Largura e altura maiores
        barChart.setCategoryGap(10);     // Menor espaço entre categorias
        barChart.setBarGap(3);           // Menor espaço entre barras

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
                    // Aplica cor e tamanho
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

        // Nome do ativo
        Label nomeLabel = new Label(acao.getNome());
        nomeLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 14; -fx-font-weight: bold;");

        // Quantidade de ações
        Label qtdLabel = new Label("Quantidade Total Cotas: " + acao.getQtdAcoes());
        qtdLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        // Valor investido
        Label valorInvestidoLabel = new Label("Valor Investido: R$" + String.format("%,.2f", acao.getValorInvestido()));
        valorInvestidoLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        // Total compra
        Label totalCompraLabel = new Label("Total compra: R$" + String.format("%,.2f", acao.getCustoTotalCompra()));
        totalCompraLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        // Total venda
        Label totalVendaLabel = new Label("Total venda: R$" + String.format("%,.2f", acao.getValorTotalVenda()));
        totalVendaLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        // Saldo final
        Label saldoFinalLabel = new Label("Saldo Final: R$" + String.format("%,.2f", acao.getSaldoFinal()));
        saldoFinalLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        // Troco
        Label trocoLabel = new Label("Troco Valor Investido: R$" + String.format("%,.2f", acao.getTroco()));
        trocoLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        // Adiciona todos os labels ao VBox
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
}