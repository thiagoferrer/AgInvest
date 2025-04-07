package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.CalculadoraFixa;
import com.example.aginvest.model.produtos.RendaFixa;
import com.example.aginvest.util.NumberUtils; // Assuming you have this utility class
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public class RendaFixaController {

    // Header buttons
    @FXML private Button homeButton;
    @FXML private Button faqButton;
    @FXML private Button contaButton;

    // Input fields
    @FXML private TextField capitalInicialField;
    @FXML private TextField aporteMensalField;
    @FXML private TextField prazoField;

    // Selection buttons (Functionality not implemented in this example)
    @FXML private Button selicButton;
    @FXML private Button cdiButton;
    @FXML private Button ipcaButton;

    // Action buttons
    @FXML private Button voltarButton;
    @FXML private Button calcularButton;

    // Corrected onClickCalcular method - calls the modified showResultsChart
    @FXML
    public void onClickCalcular() {
        try {
            BigDecimal capitalInicial = parseFieldToBigDecimal(capitalInicialField.getText(), "Capital Inicial");
            BigDecimal aporteMensal = parseFieldToBigDecimal(aporteMensalField.getText(), "Aporte Mensal");
            int prazo = parsePrazoField(prazoField.getText()); // Use helper for prazo parsing

            // Stop if prazo parsing showed an error
            if (prazo <= 0) {
                return;
            }

            // Run simulation
            CalculadoraFixa calculadora = new CalculadoraFixa();
            List<RendaFixa> resultados = calculadora.simularInvestimento(
                    capitalInicial,
                    aporteMensal,
                    prazo
            );

            // Pass the inputs along with results to the chart screen
            showResultsChart(resultados, capitalInicial, aporteMensal, prazo);

        } catch (ParseException e) {
            // Error message now includes the specific field and value from parseFieldToBigDecimal
            showError("Formato inválido", e.getMessage());
        } catch (IOException e) {
            showError("Erro de Sistema", "Não foi possível exibir os resultados: " + e.getMessage());
            e.printStackTrace(); // Log the error for debugging
        } catch (Exception e) {
            // Catch any other unexpected errors during calculation or navigation
            showError("Erro Inesperado", "Ocorreu um erro: " + e.getMessage());
            e.printStackTrace(); // Log the error for debugging
        }
    }

    // Parses currency fields, returning BigDecimal.ZERO for empty fields
    private BigDecimal parseFieldToBigDecimal(String value, String fieldName) throws ParseException {
        if (value == null || value.trim().isEmpty()) {
            return BigDecimal.ZERO; // Treat empty fields as zero
        }
        try {
            // Ensure NumberUtils exists and handles locale-specific formats (e.g., "1.000,50")
            return NumberUtils.parseCurrency(value);
        } catch (ParseException e) {
            // Throw a more informative exception
            throw new ParseException("Valor inválido para '" + fieldName + "'. Use o formato 1000,00.", e.getErrorOffset());
        }
    }

    // Parses the prazo field, shows error if invalid, returns -1 on error
    private int parsePrazoField(String text) {
        if (text == null || text.trim().isEmpty()) {
            showError("Prazo inválido", "O campo 'Prazo' não pode estar vazio.");
            return -1; // Indicate error
        }
        try {
            int prazo = Integer.parseInt(text.trim());
            if (prazo <= 0) {
                showError("Prazo inválido", "O prazo deve ser um número positivo de meses.");
                return -1; // Indicate error
            }
            return prazo;
        } catch (NumberFormatException e) {
            showError("Prazo inválido", "Por favor, insira um número inteiro válido de meses (ex: 12).");
            return -1; // Indicate error
        }
    }


    // Corrected showResultsChart method - takes input parameters
    private void showResultsChart(List<RendaFixa> resultados, BigDecimal capitalInicial, BigDecimal aporteMensal, int prazo) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/ResultadoChart.fxml"));
            if (loader.getLocation() == null) {
                System.err.println("Error loading FXML: /com/example/aginvest/ResultadoChart.fxml. Resource not found.");
                showError("Erro de Interface", "Não foi possível encontrar o arquivo da tela de resultados.");
                return; // Stop execution if FXML not found
            }

            Parent root = loader.load();
            ResultadoChartController controller = loader.getController();

            // Pass results AND the input parameters used for the simulation
            controller.initData(resultados, capitalInicial, aporteMensal, prazo);

            // Get the current stage from the button that triggered the action
            Stage stage = (Stage) calcularButton.getScene().getWindow();
            if (stage == null) {
                System.err.println("Error: Could not retrieve the Stage from the calcularButton.");
                showError("Erro de Interface", "Não foi possível obter a janela atual.");
                return; // Stop if stage is somehow null
            }

            // Set the new scene on the existing stage
            // Consider setting min/max width/height based on ResultadoChart.fxml pref sizes
            stage.setScene(new Scene(root)); // Let JavaFX determine size or set explicitly (e.g., new Scene(root, 380, 800))
            stage.setTitle("Resultados da Simulação");
            stage.centerOnScreen(); // Optional: Center the window
            stage.show();

        } catch (IOException e) {
            System.err.println("Error loading or initializing ResultadoChart.fxml: " + e.getMessage());
            e.printStackTrace(); // Log the full stack trace
            showError("Erro de Interface", "Não foi possível carregar ou exibir a tela de resultados.");
            // Do not re-throw here if you've already shown an error to the user
        }
    }

    // Displays error messages in a standard Alert dialog
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text
        alert.setContentText(message);
        alert.showAndWait();
    }

    // --- Other button handlers ---

    @FXML
    public void onClickHome() {
        navigateTo("/com/example/aginvest/Home.fxml", "Home - Invest7");
    }

    @FXML
    public void onClickFaq() {
        System.out.println("Botão FAQ clicado!");
        // Add FAQ navigation logic or show information
        showError("Info", "Funcionalidade FAQ ainda não implementada.");
    }

    @FXML
    public void onClickConta() {
        System.out.println("Botão Conta clicado!");
        // Add account navigation logic
        showError("Info", "Funcionalidade Conta ainda não implementada.");
    }

    // --- Placeholder handlers for selection buttons ---
    @FXML
    public void onClickSelic() {
        System.out.println("Botão Selic clicado!");
        // Logic to potentially use Selic value in calculations if needed
    }

    @FXML
    public void onClickCdi() {
        System.out.println("Botão CDI clicado!");
        // Logic to potentially use CDI value in calculations if needed
    }

    @FXML
    public void onClickIpca() {
        System.out.println("Botão IPCA clicado!");
        // Logic to potentially use IPCA value in calculations if needed
    }

    // --- Navigation back ---
    @FXML
    public void onClickVoltar() {
        // Navigate back to Home or previous screen, assuming Home is the main entry
        navigateTo("/com/example/aginvest/Home.fxml", "Home - Invest7");
    }

    // --- Reusable Navigation Method ---
    private void navigateTo(String fxmlPath, String title) {
        try {
            // Use any button's scene on this controller to get the current stage
            Stage stage = (Stage) calcularButton.getScene().getWindow(); // Or use voltarButton, etc.
            if (stage == null) {
                System.err.println("Error navigating: Could not get stage.");
                showError("Erro de Navegação", "Não foi possível obter a janela atual para navegação.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            if (loader.getLocation() == null) {
                System.err.println("Error loading FXML for navigation: " + fxmlPath + ". Resource not found.");
                showError("Erro de Navegação", "Não foi possível encontrar o arquivo: " + fxmlPath);
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
            showError("Erro de Navegação", "Não foi possível carregar a tela: " + title);
        } catch (IllegalStateException e) {
            e.printStackTrace(); // Log the full error
            System.err.println("Erro ao obter a janela/cena durante a navegação. " + e.getMessage());
            showError("Erro de Navegação", "Erro interno ao tentar navegar.");
        }
    }
}