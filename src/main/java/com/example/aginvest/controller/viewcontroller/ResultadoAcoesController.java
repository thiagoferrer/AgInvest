package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.CalculadoraVariavel;
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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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



    @FXML private void onClickVoltar(){
        carregarTela("/com/example/aginvest/Acoes.fxml", "Acoes- Invest7");
    }

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

        // Cria e adiciona o gráfico
        criarGraficoSaldoFinal(resultados, ativosContainer);

        // Adiciona cada ativo ao container
        for (Acoes acao : resultados) {
            VBox ativoBox = criarAtivoBox(acao);
            ativosContainer.getChildren().add(ativoBox);
        }
    }

    private void criarGraficoSaldoFinal(List<Acoes> resultados, VBox container) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        // Configurações de tamanho e espaçamento
        barChart.setId("grafico-acoes");
        barChart.setPrefSize(320, 340);
        barChart.setMaxSize(320, 340);
        barChart.setMinSize(320, 340);
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
        container.getChildren().addAll(barChart);
    }

    private VBox criarAtivoBox(Acoes acao) {
        VBox box = new VBox();
        box.setSpacing(4);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPrefWidth(300);
        box.setStyle("-fx-border-color: #1E90FF; -fx-border-radius: 8; -fx-border-width: 1; -fx-padding: 8;");

        Label nomeLabel = new Label(acao.getNome());
        nomeLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 14; -fx-font-weight: bold;");

        Label qtdLabel = new Label("Quantidade Total de Ações: ");
        qtdLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label qtdColorLabel = new Label(String.valueOf(acao.getQtdAcoes()));
        qtdColorLabel.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");
        TextFlow linhaQtd = new TextFlow(qtdLabel, qtdColorLabel);

        Label precoCompraLabel = new Label("Valor da ação na Compra: ");
        precoCompraLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label preCompraColorLabel = new Label("R$ "+ String.format("%,.2f", acao.getPrecoAcao()));
        preCompraColorLabel.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");
        TextFlow linhaPrecoCompra = new TextFlow(precoCompraLabel, preCompraColorLabel);

        Label precoVendaLabel = new Label("Valor da ação na Venda: ");
        precoVendaLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label precoVendaColorLabel = new Label("R$ "+ String.format("%,.2f", acao.getPrecoVenda()));
        precoVendaColorLabel.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");
        TextFlow linhaPrecoVenda = new TextFlow(precoVendaLabel, precoVendaColorLabel);

        Label valorInvestidoLabel = new Label("Valor Investido: ");
        valorInvestidoLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label vlrInvestColor = new Label("R$ "+ String.format("%,.2f", acao.getValorInvestido()));
        vlrInvestColor.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");
        TextFlow linhaVlrInvest = new TextFlow(valorInvestidoLabel, vlrInvestColor);

        Label totalCompraLabel = new Label("Total compra: ");
        totalCompraLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label totalCompraColor = new Label("R$" + String.format("%,.2f", acao.getCustoTotalCompra()));
        totalCompraColor.setStyle("-fx-text-fill: #FF4C4C; -fx-font-size: 12;");
        TextFlow linhaTotalCompra = new TextFlow(totalCompraLabel, totalCompraColor);

        Label totalVendaLabel = new Label("Total venda: ");
        totalVendaLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label totalVendaColor = new Label("R$"+ String.format("%,.2f", acao.getValorTotalVenda()));
        totalVendaColor.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");
        TextFlow linhaTotalVenda = new TextFlow(totalVendaLabel, totalVendaColor);

        Label saldoFinalLabel = new Label("Saldo Final: ");
        saldoFinalLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label saldoFinalColor = new Label("R$ " + String.format("%,.2f", acao.getSaldoFinal()));
        String corTexto = acao.getSaldoFinal() >= 0 ? "#1FCE52" : "#FF4C4C";
        saldoFinalColor.setStyle("-fx-text-fill: " + corTexto + "; -fx-font-size: 12;");
        TextFlow linhaSaldoFinal = new TextFlow(saldoFinalLabel, saldoFinalColor);

        Label trocoLabel = new Label("Troco Valor Investido: ");
        trocoLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label trocoColor = new Label("R$ " + String.format("%,.2f", acao.getTroco()));
        String corTextoTroco = acao.getTroco() >= 0 ? "#1FCE52" : "#FF4C4C";
        trocoColor.setStyle("-fx-text-fill: " + corTextoTroco + "; -fx-font-size: 12;");
        TextFlow linhaTroco = new TextFlow(trocoLabel, trocoColor);

        box.getChildren().addAll(
                nomeLabel,
                linhaQtd ,
                linhaPrecoCompra,
                linhaPrecoVenda,
                linhaVlrInvest ,
                linhaTotalCompra,
                linhaTotalVenda ,
                linhaSaldoFinal,
                linhaTroco
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