package com.example.aginvest.controller.viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class SimuPreviaController {

    @FXML private Button voltarButton;
    @FXML private Button calcularButton;

    @FXML private TextField capitalInicialField;
    @FXML private TextField aporteMensalField;
    @FXML private TextField prazoField;
    @FXML private TextField selicField;
    @FXML private TextField cdiField;
    @FXML private TextField ipcaField;

    @FXML
    public void initialize() {
        // Configurar ações dos botões
        voltarButton.setOnAction(e -> handleVoltar());
        calcularButton.setOnAction(e -> handleCalcular());
    }

    @FXML
    private void handleCalcular() {

    }

    @FXML
    private void handleVoltar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/Logo.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) voltarButton.getScene().getWindow();
            stage.setScene(new Scene(root, 360, 640));
            stage.setTitle("Invest7");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void calcularRendimento() {
        try {
            // Obter valores dos campos de texto
            double capitalInicial = parseCurrency(capitalInicialField.getText());
            double aporteMensal = parseCurrency(aporteMensalField.getText());
            int prazoMeses = Integer.parseInt(prazoField.getText().trim());
            double selicAnual = parsePercentage(selicField.getText());
            double cdiAnual = parsePercentage(cdiField.getText());
            double ipcaAnual = parsePercentage(ipcaField.getText());

            // Converter taxas anuais para mensais
            double selicMensal = Math.pow(1 + selicAnual, 1.0 / 12) - 1;
            double cdiMensal = Math.pow(1 + cdiAnual, 1.0 / 12) - 1;
            double ipcaMensal = Math.pow(1 + ipcaAnual, 1.0 / 12) - 1;

            // Calcular rendimentos
            double rendimentoSelic = calcularInvestimento(capitalInicial, aporteMensal, selicMensal, prazoMeses);
            double rendimentoCdi = calcularInvestimento(capitalInicial, aporteMensal, cdiMensal, prazoMeses);
            double rendimentoIpca = calcularInvestimento(capitalInicial, aporteMensal, ipcaMensal, prazoMeses);

            // Exibir resultados (pode ser adaptado para uma nova tela ou diálogo)
            System.out.printf("Rendimento Selic: R$ %.2f%n", rendimentoSelic);
            System.out.printf("Rendimento CDI: R$ %.2f%n", rendimentoCdi);
            System.out.printf("Rendimento IPCA+: R$ %.2f%n", rendimentoIpca);

        } catch (NumberFormatException e) {
            System.out.println("Erro: Insira valores válidos nos campos.");
        }
    }

    // Método para calcular o investimento com aportes mensais
    private double calcularInvestimento(double capitalInicial, double aporteMensal, double taxaMensal, int prazoMeses) {
        double montante = capitalInicial;
        for (int i = 0; i < prazoMeses; i++) {
            montante = montante * (1 + taxaMensal) + aporteMensal;
        }
        return montante;
    }

    // Método para converter texto de moeda (ex: "R$ 1.000,00") em double
    private double parseCurrency(String text) {
        String cleaned = text.replace("R$", "").replace(".", "").replace(",", ".").trim();
        return cleaned.isEmpty() ? 0.0 : Double.parseDouble(cleaned);
    }

    // Método para converter texto de porcentagem (ex: "13.15%") em double
    private double parsePercentage(String text) {
        String cleaned = text.replace("%", "").replace(",", ".").trim();
        return cleaned.isEmpty() ? 0.0 : Double.parseDouble(cleaned) / 100.0;
    }
}