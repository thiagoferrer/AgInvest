package com.example.aginvest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class FaqController {

    @FXML
    private ListView<String> listaFaq;

    // Método para o botão Home
    @FXML
    private void manipularBotaoHome() {
        carregarTela("Home.fxml", "Invest7 - Home");
    }

    // Método para o botão FAQ
    @FXML
    private void manipularBotaoFaq() {
        // Já estamos na tela FAQ
        mostrarAlerta("Informação", "Você já está na tela de Perguntas Frequentes");
    }

    // Método para o botão Conta
    @FXML
    private void manipularBotaoConta() {
        carregarTela("Conta.fxml", "Invest7 - Minha Conta");
    }

    // Método para o botão Voltar
    @FXML
    private void voltarParaHome() {
        carregarTela("Home.fxml", "Invest7 - Home");
    }

    private void carregarTela(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) listaFaq.getScene().getWindow();
            stage.setScene(new Scene(root, 360, 640));
            stage.setTitle(title);
            stage.show();

        } catch (IOException e) {
            mostrarErro("Erro ao carregar a tela", e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void initialize() {
        // Inicialização da lista de FAQs
        String[] itensFaq = {
                "1. O que é um simulador de investimentos?",
                "   É uma ferramenta que permite calcular os possíveis retornos de um investimento com base em diferentes parâmetros.",
                "2. Como calcular o retorno de um investimento?",
                "3. Quais são os tipos de investimentos simulados?",
                "4. O simulador garante retornos reais?",
                "5. Posso confiar nos cálculos do simulador?",
                "6. O que é perfil investidor?",
                "7. Quais são os perfis investidores?",
                "8. Como posso alterar meu perfil investidor?",
                "9. Por que preciso responder ao questionário durante o cadastro?",
                "10. Posso refazer o questionário?"
        };

        listaFaq.getItems().addAll(itensFaq);
    }
}