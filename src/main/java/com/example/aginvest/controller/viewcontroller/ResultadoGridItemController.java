package com.example.aginvest.controller.viewcontroller;


import com.example.aginvest.model.produtos.RendaFixa;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.chart.PieChart;
import javafx.util.Duration;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class ResultadoGridItemController {
    @FXML private VBox rootContainer;
    @FXML private Label nomeLabel;
    @FXML private Label tipoLabel;
    @FXML private Label totalLabel;
    @FXML private Label lucroLabel;
    @FXML private Label irLabel;
    @FXML private PieChart donutChart;

    private final NumberFormat currencyFormatter;
    private final NumberFormat percentFormatter;

    public ResultadoGridItemController() {
        Locale brazil = new Locale("pt", "BR");
        currencyFormatter = NumberFormat.getCurrencyInstance(brazil);
        percentFormatter = NumberFormat.getPercentInstance(brazil);
        percentFormatter.setMinimumFractionDigits(2);
        percentFormatter.setMaximumFractionDigits(2);
    }

    public void setRendaFixa(RendaFixa rendaFixa) {
        if (rendaFixa == null) return;

        // Initialize components if null (defensive programming)
        if (nomeLabel != null) nomeLabel.setText(rendaFixa.getNome());
        if (tipoLabel != null) tipoLabel.setText(rendaFixa.getTipoProduto());

        if (totalLabel != null) {
            totalLabel.setText("Total: " + currencyFormatter.format(rendaFixa.getValorTotal()));
        }

        BigDecimal lucro = rendaFixa.getRendimentoLiquido();
        BigDecimal percentualLucro = lucro.divide(rendaFixa.getTotalInvestido(), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        if (lucroLabel != null) {
            lucroLabel.setText("Lucro: " + currencyFormatter.format(lucro) +
                    " (" + percentFormatter.format(percentualLucro.divide(BigDecimal.valueOf(100))) + ")");
        }

        if (irLabel != null) {
            irLabel.setText(rendaFixa.isTaxable() ?
                    "IR: " + currencyFormatter.format(rendaFixa.getImpostoIR()) :
                    "IR: Isento");
        }

        if (donutChart != null) {
            setupDonutChart(rendaFixa);
        }

        if (rootContainer != null) {
            animateCard();
        }
    }

    private void setupDonutChart(RendaFixa rendaFixa) {
        donutChart.getData().clear();

        BigDecimal aporte = rendaFixa.getTotalInvestido();
        BigDecimal lucro = rendaFixa.getRendimentoLiquido();
        BigDecimal taxas = rendaFixa.isTaxable() ? rendaFixa.getImpostoIR() : BigDecimal.ZERO;

        PieChart.Data aporteData = new PieChart.Data("Aporte", aporte.doubleValue());
        PieChart.Data lucroData = new PieChart.Data("Lucro", lucro.doubleValue());
        PieChart.Data taxasData = new PieChart.Data("Taxas", taxas.doubleValue());

        donutChart.getData().addAll(aporteData, lucroData, taxasData);
        donutChart.setLegendVisible(false);
        donutChart.setTitle("");
    }

    private void animateCard() {
        rootContainer.setOpacity(0);
        rootContainer.setScaleX(0.8);
        rootContainer.setScaleY(0.8);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), rootContainer);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(300), rootContainer);
        scaleUp.setFromX(0.8);
        scaleUp.setFromY(0.8);
        scaleUp.setToX(1);
        scaleUp.setToY(1);

        fadeIn.play();
        scaleUp.play();
    }
}