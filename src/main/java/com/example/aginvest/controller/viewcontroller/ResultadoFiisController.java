package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.CalculadoraVariavel;
import com.example.aginvest.model.produtos.Fiis;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.PrintWriter;
import java.util.List;

public class ResultadoFiisController {
    @FXML
    private VBox ativosContainer;
    @FXML
    private VBox ativosSection;
    @FXML
    private VBox barChartContainer; // Novo container para o gráfico

    private List<Fiis> resultadosSimulacao;
    @FXML
    public void initialize() {
        // Inicialização se necessário
    }

    public void CalcularFiis(Fiis fiis) {
        CalculadoraVariavel calculadoraVariavel = new CalculadoraVariavel();
        List<Fiis> resultados = calculadoraVariavel.simularFundoImobiliario(fiis);

        this.resultadosSimulacao = resultados;

        // Limpa os containers antes de adicionar novos elementos
        ativosContainer.getChildren().clear();
        barChartContainer.getChildren().clear();

        // Cria e adiciona o gráfico de barras
        criarGraficoBarras(resultados);

        // Adiciona cada ativo ao container
        for (Fiis fii : resultados) {
            VBox ativoBox = criarFiiBox(fii);
            ativosContainer.getChildren().add(ativoBox);
        }
    }

    private void criarGraficoBarras(List<Fiis> fiisList) {
        // Define os eixos
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Fundos Imobiliários");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Dividendos Mensais (R$)");

        // Cria o gráfico de barras
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Dividendos Mensais por Fundo");
        barChart.setLegendVisible(false);
        barChart.setPrefHeight(250);
        barChart.setPrefWidth(300);
        barChart.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8;");

        // Cria a série de dados
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (Fiis fii : fiisList) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(
                    fii.getNome(),
                    fii.getDividendosMensais()
            );

            // Adiciona um nó para mostrar o valor em cima da barra
            data.setNode(createDataLabel(fii.getDividendosMensais()));

            series.getData().add(data);
        }

        barChart.getData().add(series);
        barChartContainer.getChildren().add(barChart);
    }

    private Node createDataLabel(double value) {
        Label label = new Label(String.format("R$%,.2f", value));
        label.setStyle("-fx-text-fill: #333333; -fx-font-size: 7; -fx-font-weight: bold;");
        return label;
    }

    private VBox criarFiiBox(Fiis fii) {
        VBox box = new VBox();
        box.setSpacing(4);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPrefWidth(300);
        box.setStyle("-fx-border-color: #1E90FF; -fx-border-radius: 8; -fx-border-width: 1; -fx-padding: 8;");

        // Nome do FII
        Label nomeLabel = new Label(fii.getNome());
        nomeLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 14; -fx-font-weight: bold;");

        // Quantidade de cotas
        Label qtdLabel = new Label("Quantidade Total Cotas: " + fii.getQtdCotas());
        qtdLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");

        // Saldo cotas
        Label saldoCotasLabel = new Label("Saldo Cotas: R$" + String.format("%,.2f", fii.getSaldoCotas()));
        saldoCotasLabel.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");

        // Saldo dividendos
        Label saldoDividendosLabel = new Label("Saldo Dividendos: R$" + String.format("%,.2f", fii.getSaldoDividendos()));
        saldoDividendosLabel.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");

        // Dividendos Mensais
        Label dividendosMensaisLabel = new Label("Dividendos Mensais: R$" + String.format("%,.2f", fii.getDividendosMensais()));
        dividendosMensaisLabel.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");

        // Adiciona todos os labels ao VBox
        box.getChildren().addAll(
                nomeLabel,
                qtdLabel,
                saldoCotasLabel,
                saldoDividendosLabel,
                dividendosMensaisLabel
        );

        return box;
    }

    @FXML
    private void gerarCsv() {
        if (resultadosSimulacao == null || resultadosSimulacao.isEmpty()) {
            mostrarAlerta("Erro", "Nenhum dado para exportar. Realize uma simulação primeiro.", Alert.AlertType.WARNING);
            return;
        }

        try {
            String nomeArquivo = "resultado_fiis.csv";
            PrintWriter writer = new PrintWriter(nomeArquivo);

            // Cabeçalho
            writer.println("Nome,Quantidade de Cotas,Saldo Cotas,Saldo Dividendos,Dividendos Mensais");

            // Dados
            for (Fiis fiis : resultadosSimulacao) {
                writer.printf("%s,%d,%.2f,%.2f,%.2f%n",
                        fiis.getNome(),
                        fiis.getQtdCotas(),
                        fiis.getSaldoCotas(),
                        fiis.getSaldoDividendos(),
                        fiis.getDividendosMensais());
            }

            writer.close();

            mostrarAlerta("Sucesso", "CSV gerado com sucesso: " + nomeArquivo, Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao gerar o CSV: " + e.getMessage(), Alert.AlertType.ERROR);
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
    public void onClickHome(ActionEvent actionEvent) {
        // Implementação faltando
    }
}
