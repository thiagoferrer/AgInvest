package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.model.produtos.RendaFixa;
import com.example.aginvest.util.TaxService; // Needed for recalculation if necessary
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.util.Duration;


import java.io.IOException;
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

    // CDB Card Elements
    @FXML private VBox cdbCard;
    @FXML private Label cdbTotalInvestidoLabel;
    @FXML private Label cdbGrossReturnLabel;
    @FXML private Label cdbTaxLabel;
    @FXML private Label cdbNetProfitLabel;
    @FXML private Label cdbNetTotalLabel;
    @FXML private Label cdbProfitPercentLabel;

    // LCI/LCA Card Elements
    @FXML private VBox lciLcaCard;
    @FXML private Label lciLcaTotalInvestidoLabel;
    @FXML private Label lciLcaGrossReturnLabel;
    @FXML private Label lciLcaTaxLabel;
    @FXML private Label lciLcaNetProfitLabel;
    @FXML private Label lciLcaNetTotalLabel;
    @FXML private Label lciLcaProfitPercentLabel;

    // Poupança Card Elements (Optional)
    @FXML private VBox poupancaCard;
    @FXML private Label poupancaTotalInvestidoLabel;
    @FXML private Label poupancaGrossReturnLabel;
    @FXML private Label poupancaTaxLabel;
    @FXML private Label poupancaNetProfitLabel;
    @FXML private Label poupancaNetTotalLabel;
    @FXML private Label poupancaProfitPercentLabel;


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
        populateBarChart();
        populateAssetCards();
    }

    @FXML
    private void initialize() {
        // Initial setup if needed, before data is loaded
        lineChart.setAnimated(false); // Optional: Improve performance for many series
        barChart.setAnimated(true);
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


    private void populateBarChart() {
        barChart.getData().clear();
        if (simulationResults == null || simulationResults.isEmpty()) {
            return;
        }

        // Set y-axis to start at zero
        NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
        yAxis.setAutoRanging(true);
        yAxis.setLowerBound(0);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (RendaFixa produto : simulationResults) {
            BigDecimal netProfit = produto.getRendimentoLiquido() != null
                    ? produto.getRendimentoLiquido()
                    : BigDecimal.ZERO;
            series.getData().add(new XYChart.Data<>(produto.getNome(), netProfit));
        }

        barChart.getData().add(series);
    }

    private void populateAssetCards() {
        // Hide all cards initially
        cdbCard.setVisible(false); cdbCard.setManaged(false);
        lciLcaCard.setVisible(false); lciLcaCard.setManaged(false);
        poupancaCard.setVisible(false); poupancaCard.setManaged(false);

        if (simulationResults == null) return;

        // Find the *first* representative for each category
        Optional<RendaFixa> cdbOpt = simulationResults.stream()
                .filter(p -> "CDB".equalsIgnoreCase(p.getTipoProduto()))
                .findFirst();

        Optional<RendaFixa> lciLcaOpt = simulationResults.stream()
                .filter(p -> "LCI".equalsIgnoreCase(p.getTipoProduto()) || "LCA".equalsIgnoreCase(p.getTipoProduto()))
                .findFirst();

        Optional<RendaFixa> poupancaOpt = simulationResults.stream()
                .filter(p -> "POUPANCA".equalsIgnoreCase(p.getTipoProduto()))
                .findFirst();

        // Populate CDB Card if found
        cdbOpt.ifPresent(cdb -> {
            fillCardData(cdbCard, cdbTotalInvestidoLabel, cdbGrossReturnLabel, cdbTaxLabel, cdbNetProfitLabel, cdbNetTotalLabel, cdbProfitPercentLabel, cdb);
            cdbCard.setVisible(true);
            cdbCard.setManaged(true);
            ((Label) cdbCard.getChildren().get(0)).setText("CDB: " + cdb.getNome()); // Set specific name
        });

        // Populate LCI/LCA Card if found
        lciLcaOpt.ifPresent(lciLca -> {
            fillCardData(lciLcaCard, lciLcaTotalInvestidoLabel, lciLcaGrossReturnLabel, lciLcaTaxLabel, lciLcaNetProfitLabel, lciLcaNetTotalLabel, lciLcaProfitPercentLabel, lciLca);
            lciLcaCard.setVisible(true);
            lciLcaCard.setManaged(true);
            ((Label) lciLcaCard.getChildren().get(0)).setText(lciLca.getTipoProduto() + ": " + lciLca.getNome()); // Set specific name
        });

        // Populate Poupança Card if found
        poupancaOpt.ifPresent(poupanca -> {
            fillCardData(poupancaCard, poupancaTotalInvestidoLabel, poupancaGrossReturnLabel, poupancaTaxLabel, poupancaNetProfitLabel, poupancaNetTotalLabel, poupancaProfitPercentLabel, poupanca);
            poupancaCard.setVisible(true);
            poupancaCard.setManaged(true);
        });
    }

    private void fillCardData(VBox card, Label totalInvestidoLabel, Label grossReturnLabel, Label taxLabel,
                              Label netProfitLabel, Label netTotalLabel, Label profitPercentLabel, RendaFixa produto) {

        BigDecimal totalInvestido = produto.getTotalInvestido() != null ? produto.getTotalInvestido() : BigDecimal.ZERO;
        BigDecimal rendimentoBruto = produto.getRendimentoBruto() != null ? produto.getRendimentoBruto() : BigDecimal.ZERO;
        BigDecimal impostoIR = produto.getImpostoIR() != null ? produto.getImpostoIR() : BigDecimal.ZERO;
        BigDecimal rendimentoLiquido = produto.getRendimentoLiquido() != null ? produto.getRendimentoLiquido() : BigDecimal.ZERO;
        BigDecimal valorTotal = produto.getValorTotal() != null ? produto.getValorTotal() : BigDecimal.ZERO;
        String percentualLucroStr = produto.getPercentualLucro() != null ? produto.getPercentualLucro() : "0,00%";


        totalInvestidoLabel.setText("Total Investido: " + currencyFormatter.format(totalInvestido));
        grossReturnLabel.setText("Rendimento Bruto: " + currencyFormatter.format(rendimentoBruto));
        taxLabel.setText(String.format("Imposto de Renda: %s%s",
                impostoIR.compareTo(BigDecimal.ZERO) > 0 ? "-" : "", // Add minus sign only if tax > 0
                currencyFormatter.format(impostoIR.abs()) + (produto.isTaxable() ? "" : " (Isento)")));
        netProfitLabel.setText("Lucro Líquido: " + currencyFormatter.format(rendimentoLiquido));
        netTotalLabel.setText("Valor Total Líquido: " + currencyFormatter.format(valorTotal));

        // Format percentage correctly
        try {
            // Assuming percentualLucro is like "12.34%"
            double percentValue = Double.parseDouble(percentualLucroStr.replace("%", "").replace(",", ".")) / 100.0;
            profitPercentLabel.setText("Lucratividade: " + percentFormatter.format(percentValue));
        } catch (NumberFormatException | NullPointerException e) {
            profitPercentLabel.setText("Lucratividade: " + percentualLucroStr); // Fallback to original string
        }

    }


    // --- Action Handlers ---

    @FXML
    private void onClickVoltar() {
        navigateTo("/com/example/aginvest/RendaFixa.fxml", "Simulador Renda Fixa"); // Go back to simulator
    }

    @FXML
    private void onClickNovaSimulacao() {
        navigateTo("/com/example/aginvest/RendaFixa.fxml", "Simulador Renda Fixa"); // Go back to simulator
    }

    @FXML
    private void onClickHome() {
        // Assuming you have a Home.fxml
        navigateTo("/com/example/aginvest/Home.fxml", "Home - Invest7");
        System.out.println("Botão Home clicado!");
    }

    @FXML
    private void onClickFaq() {
        // Implement FAQ navigation or action
        System.out.println("Botão FAQ clicado!");
        // Example: Show an alert or navigate to a new screen
    }

    @FXML
    private void onClickConta() {
        // Implement Account navigation or action
        System.out.println("Botão Conta clicado!");
        // Example: Navigate to account details screen
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
}