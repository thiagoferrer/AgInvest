package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.model.produtos.Acoes;
import com.example.aginvest.model.produtos.Previa;
import com.example.aginvest.model.produtos.RendaFixa;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class SimuPreviaController {

    @FXML private Button voltarButton;
    @FXML private Button calcularButton;

    @FXML private TextField capitalInicialField;
    @FXML private TextField aporteMensalField;
    @FXML private TextField prazoField;
    @FXML private ImageView logoImage;


    @FXML
    public void initialize() {

        Image logo = new Image(getClass().getResource("/image/Logo.png").toExternalForm());
        logoImage.setImage(logo);


        // Configurar ações dos botões
        voltarButton.setOnAction(e -> handleVoltar());
        calcularButton.setOnAction(e -> handleCalcular());
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

    @FXML
    private void handleCalcular() {
        try {
            // Obter valores dos campos de texto
            double capitalInicial = parseCurrency(capitalInicialField.getText());
            double aporteMensal = parseCurrency(aporteMensalField.getText());
            int prazoMeses = Integer.parseInt(prazoField.getText().trim());
            double selicAnual = 0.1315;
            double cdiAnual = 0.1415;
            double ipcaAnual = 0.0519;

            // Converter taxas anuais para mensais
            double selicMensal = Math.pow(1 + selicAnual, 1.0 / 12) - 1;
            double cdiMensal = Math.pow(1 + cdiAnual, 1.0 / 12) - 1;
            double ipcaMensal = Math.pow(1 + ipcaAnual, 1.0 / 12) - 1;

            // Calcular rendimentos
            double rendimentoSelic = calcularInvestimento(capitalInicial, aporteMensal, selicMensal, prazoMeses);
            double rendimentoCdi = calcularInvestimento(capitalInicial, aporteMensal, cdiMensal, prazoMeses);
            double rendimentoIpca = calcularInvestimento(capitalInicial, aporteMensal, ipcaMensal, prazoMeses);
            carregarTelaResultado(capitalInicial, aporteMensal, prazoMeses);


        } catch (NumberFormatException e) {
            System.out.println("Erro: Insira valores válidos nos campos.");
        }
    }

    private void carregarTelaResultado(double capitalInicial, double aporteMensal, int prazo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/ResultadosSimuPrev.fxml"));
            Parent root = loader.load();

            // Obtém o controller que foi criado pelo FXMLLoader
            ResultadosSimuPreviaController simsPrev = loader.getController();
            simsPrev.setDados(capitalInicial, aporteMensal, prazo);

            Stage stage = (Stage) calcularButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar próxima tela: " + e.getMessage());
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