package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.model.produtos.Acoes;
import com.example.aginvest.model.produtos.RendaFixa;
import com.example.aginvest.util.TaxService; // Needed for recalculation if necessary
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.util.Duration;


import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class ResultadoChartController {

    // --- FXML Elements ---

    // Top Bar
    @FXML private Button homeButton;
    @FXML private Button faqButton;
    @FXML private Button contaButton;

    // Header
    @FXML private Label simulationDetailsLabel;

    // Charts
    @FXML private LineChart<Number, Number> lineChart;
    @FXML private BarChart<String, Number> barChart;

    // Asset Cards Container
    @FXML private VBox assetCardsContainer;

    // Bottom Buttons
    @FXML private Button voltarButton;
    @FXML private Button novaSimulacaoButton;

    // --- Instance Variables ---
    private List<RendaFixa> simulationResults;
    private BigDecimal initialCapital;
    private BigDecimal monthlyContribution;
    private int termInMonths;
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private final NumberFormat percentFormatter = NumberFormat.getPercentInstance(new Locale("pt", "BR"));

    @FXML
    private Label csvStatusLabel;

    // --- Initialization ---

    public void initData(List<RendaFixa> results, BigDecimal capitalInicial, BigDecimal aporteMensal, int prazo) {
        this.simulationResults = results;
        this.initialCapital = capitalInicial;
        this.monthlyContribution = aporteMensal;
        this.termInMonths = prazo;

        percentFormatter.setMaximumFractionDigits(2);
        percentFormatter.setMinimumFractionDigits(2);

        populateHeader();
        populateLineChart();
        populateAssetCards();
    }

    @FXML
    private void initialize() {
        // Initial setup if needed, before data is loaded
        lineChart.setAnimated(false); // Optional: Improve performance for many series

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

    // --- Data Population Methods ---

    private void populateHeader() {
        String details = String.format(
                "Simulação com Capital Inicial: %s, Aporte Mensal: %s, Prazo: %d meses.",
                currencyFormatter.format(initialCapital),
                currencyFormatter.format(monthlyContribution),
                termInMonths
        );
        simulationDetailsLabel.setText(details);
    }

    private void populateLineChart() {
        lineChart.getData().clear();

        if (simulationResults == null || simulationResults.isEmpty() || termInMonths <= 0) {
            return; // No data or invalid term
        }

        for (RendaFixa produto : simulationResults) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(produto.getNome());
            lineChart.getData().add(series);

            List<XYChart.Data<Number, Number>> dataPoints = new ArrayList<>();
            BigDecimal currentCapital = initialCapital;

            // Calculate monthly rate
            double annualRateDouble = produto.getRentabilidadeBruta() / 100.0;
            double monthlyRateDouble = Math.pow(1.0 + annualRateDouble, 1.0 / 12.0) - 1.0;
            BigDecimal monthlyRate = BigDecimal.valueOf(monthlyRateDouble);

            // Initial data point (month 0)
            dataPoints.add(new XYChart.Data<>(0, currentCapital.setScale(2, RoundingMode.HALF_UP)));

            // Calculate subsequent months
            for (int month = 1; month <= termInMonths; month++) {
                if (monthlyContribution.compareTo(BigDecimal.ZERO) > 0) {
                    currentCapital = currentCapital.add(monthlyContribution);
                }
                currentCapital = currentCapital.multiply(BigDecimal.ONE.add(monthlyRate));
                dataPoints.add(new XYChart.Data<>(month, currentCapital.setScale(2, RoundingMode.HALF_UP)));
            }

            // Create Timeline to add data points with delay
            Timeline timeline = new Timeline();
            for (int i = 0; i < dataPoints.size(); i++) {
                final XYChart.Data<Number, Number> data = dataPoints.get(i);
                Duration delay = Duration.millis(i * 50); // 50ms per point
                KeyFrame keyFrame = new KeyFrame(delay, e -> series.getData().add(data));
                timeline.getKeyFrames().add(keyFrame);
            }
            timeline.play();
        }
    }


    private void populateAssetCards() {
        assetCardsContainer.getChildren().clear(); // Clear any existing cards

        if (simulationResults == null || simulationResults.isEmpty()) {
            return;
        }

        for (RendaFixa produto : simulationResults) {
            // Create a new card for each product
            VBox card = createAssetCard(produto);
            assetCardsContainer.getChildren().add(card);
        }
    }

    private VBox createAssetCard(RendaFixa produto) {
        // Create the card container
        VBox card = new VBox();
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPrefWidth(300);
        card.setSpacing(4);
        card.setStyle("-fx-border-color: #1E90FF; -fx-border-radius: 8; -fx-border-width: 1; -fx-padding: 8;");


        // Determine color based on product type
        String color;
        switch (produto.getTipoProduto().toUpperCase()) {
            case "CDB":
                color = "#1E88E5"; // Blue
                break;
            case "LCI":
            case "LCA":
                color = "#4CAF50"; // Green
                break;
            case "POUPANCA":
                color = "#FF9800"; // Orange
                break;
            default:
                color = "#9C27B0"; // Purple (default for other types)
        }

        // Create and add product name label
        Label nameLabel = new Label(produto.getNome());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: " + color + "; -fx-font-size: 16px;");
        card.getChildren().add(nameLabel);

        // Create labels for static text and dynamic values
        Label totalInvestidoLabel = new Label("Total Investido: ");
        Label totalInvestidoValue = new Label();

        Label grossReturnLabel = new Label("Rendimento Bruto: ");
        Label grossReturnValue = new Label();

        Label taxLabel = new Label("Imposto de Renda: ");
        Label taxValue = new Label();

        Label netProfitLabel = new Label("Lucro Líquido: ");
        Label netProfitValue = new Label();

        Label netTotalLabel = new Label("Valor Total Líquido: ");
        Label netTotalValue = new Label();

        Label profitPercentLabel = new Label("Lucratividade: ");
        Label profitPercentValue = new Label();

        // Style static labels
        String staticStyle = "-fx-text-fill: #666666; -fx-font-size: 12;";
        totalInvestidoLabel.setStyle(staticStyle);
        grossReturnLabel.setStyle(staticStyle);
        taxLabel.setStyle(staticStyle);
        netProfitLabel.setStyle(staticStyle);
        netTotalLabel.setStyle(staticStyle);
        profitPercentLabel.setStyle(staticStyle);

        // Create TextFlows
        TextFlow totalInvest = new TextFlow(totalInvestidoLabel, totalInvestidoValue);
        TextFlow grossReturn = new TextFlow(grossReturnLabel, grossReturnValue);
        TextFlow tax = new TextFlow(taxLabel, taxValue);
        TextFlow netProfit = new TextFlow(netProfitLabel, netProfitValue);
        TextFlow netTotal = new TextFlow(netTotalLabel, netTotalValue);
        TextFlow profitPercent = new TextFlow(profitPercentLabel, profitPercentValue);

        // Add to card
        card.getChildren().addAll(
                totalInvest, grossReturn, tax, netProfit, netTotal, profitPercent
        );

        // Fill data with colors
        fillCardData(produto, totalInvestidoValue, grossReturnValue, taxValue,
                netProfitValue, netTotalValue, profitPercentValue);

        return card;
    }

    private void fillCardData(RendaFixa produto, Label totalInvestidoValue,
                              Label grossReturnValue, Label taxValue,
                              Label netProfitValue, Label netTotalValue,
                              Label profitPercentValue) {
        // Total Investido (always positive, green)
        totalInvestidoValue.setText(currencyFormatter.format(produto.getTotalInvestido()));
        totalInvestidoValue.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");

        // Rendimento Bruto (green)
        grossReturnValue.setText(currencyFormatter.format(produto.getRendimentoBruto()));
        grossReturnValue.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");

        // Imposto (red if > 0)
        taxValue.setText(currencyFormatter.format(produto.getImpostoIR().abs()));
        taxValue.setStyle("-fx-text-fill: " +
                (produto.getImpostoIR().compareTo(BigDecimal.ZERO) > 0 ? "#FF4500" : "#666666") +
                "; -fx-font-size: 12;");

        // Lucro Líquido (green/red based on value)
        String netProfitColor = produto.getRendimentoLiquido().compareTo(BigDecimal.ZERO) >= 0
                ? "#1FCE52" : "#FF4500";
        netProfitValue.setText(currencyFormatter.format(produto.getRendimentoLiquido()));
        netProfitValue.setStyle("-fx-text-fill: " + netProfitColor + "; -fx-font-size: 12;");

        // Valor Total (green)
        netTotalValue.setText(currencyFormatter.format(produto.getValorTotal()));
        netTotalValue.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");

        // Lucratividade (parse percentage)
        try {
            double percent = Double.parseDouble(produto.getPercentualLucro().replace("%", "")) / 100;
            profitPercentValue.setText(percentFormatter.format(percent));
        } catch (Exception e) {
            profitPercentValue.setText(produto.getPercentualLucro());
        }
        profitPercentValue.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");
    }


    // --- Action Handlers ---

    @FXML
    private void onClickVoltar() {
        navigateTo("/com/example/aginvest/RendaFixa.fxml", "Simulador Renda Fixa"); // Go back to simulator
    }



    @FXML
    private void onClickHome() {
        // Assuming you have a Home.fxml
        navigateTo("/com/example/aginvest/Home.fxml", "Home - Invest7");
        System.out.println("Botão Home clicado!");
    }


    // --- Navigation Helper ---

    private void navigateTo(String fxmlPath, String title) {
        try {
            // Use any button's scene to get the current stage
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            if (stage == null) {
                System.err.println("Error: Could not get stage from button.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            if (loader.getLocation() == null) {
                System.err.println("Error loading FXML: " + fxmlPath + ". Resource not found.");
                // Show error alert to user
                return;
            }

            Parent root = loader.load();
            Scene scene = new Scene(root); // Adjust size if needed
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Log the full error
            System.err.println("Erro ao carregar a tela: " + fxmlPath + " - " + e.getMessage());
            // Consider showing an Alert dialog to the user here
        } catch (IllegalStateException e) {
            e.printStackTrace(); // Log the full error
            System.err.println("Erro ao obter a janela/cena. Certifique-se que o botão pertence a uma cena ativa. " + e.getMessage());
        }
    }

    @FXML
    private void gerarCsv() {
        if (simulationResults == null || simulationResults.isEmpty()) {
            csvStatusLabel.setText("Erro: Nenhum dado para exportar. Realize uma simulação primeiro.");
            csvStatusLabel.setStyle("-fx-text-fill: #FF0000;");
            csvStatusLabel.setVisible(true);
            return;
        }

        try {
            String nomeArquivo = "resultado_rendafixa.csv";
            PrintWriter writer = new PrintWriter(nomeArquivo);

            // Cabeçalho
            writer.println("Nome, Rendimento Bruto, Rendimento Liquido, Imposto de Renda, Valor Total");

            // Dados
            for (RendaFixa rendaFixa : simulationResults) {
                writer.printf("%s, %.2f, %.2f, %.2f, %.2f%n",
                        rendaFixa.getNome(),
                        rendaFixa.getRendimentoBruto(),
                        rendaFixa.getRendimentoLiquido(),
                        rendaFixa.getImpostoIR(),
                        rendaFixa.getValorTotal());
            }

            writer.close();

            csvStatusLabel.setText("CSV gerado com sucesso !! ");
            csvStatusLabel.setStyle("-fx-text-fill: #2CC158;");
            csvStatusLabel.setVisible(true);

            // Opcional: esconder a mensagem após alguns segundos
            new Timeline(new KeyFrame(Duration.seconds(5), e -> csvStatusLabel.setVisible(false))).play();

        } catch (Exception e) {
            csvStatusLabel.setText("Falha ao gerar o CSV: " + e.getMessage());
            csvStatusLabel.setStyle("-fx-text-fill: #FF0000;");
            csvStatusLabel.setVisible(true);
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
}