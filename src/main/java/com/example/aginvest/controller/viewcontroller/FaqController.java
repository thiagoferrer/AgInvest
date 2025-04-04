package com.example.aginvest.controller.viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FaqController implements Initializable {

    @FXML
    private Accordion faqAccordion;

    @FXML
    private Button contaButton;

    @FXML
    private Button faqButton;

    @FXML
    private Button homeButton;

    @FXML
    private void onClickHome() {
        carregarTela("/com/example/aginvest/home.fxml", "Home - Invest7");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurando os TitledPanes com perguntas e respostas
        TitledPane tp1 = new TitledPane("1. O QUE É UM SIMULADOR DE INVESTIMENTOS?",
                createContent("É uma ferramenta que permite calcular os possíveis retornos de um investimento com base em diferentes parâmetros."));
        TitledPane tp2 = new TitledPane("2. COMO CALCULAR O RETORNO DE UM INVESTIMENTO?",
                createContent("O retorno é calculado com base no valor investido, taxa de juros e tempo de aplicação. Normalmente, usa-se a fórmula de juros compostos."));
        TitledPane tp3 = new TitledPane("3. QUAIS SÃO OS TIPOS DE INVESTIMENTOS SIMULADOS?",
                createContent("O simulador pode incluir renda fixa (CDB, Tesouro Direto) e renda variável (ações, fundos imobiliários)."));
        TitledPane tp4 = new TitledPane("4. O SIMULADOR GARANTE RETORNOS REAIS?",
                createContent("Não, ele apenas faz projeções com base nas informações inseridas, mas o mercado pode ter variações inesperadas."));
        TitledPane tp5 = new TitledPane("5. POSSO CONFIAR NOS CÁLCULOS DO SIMULADOR?",
                createContent("Os cálculos são baseados em fórmulas matemáticas conhecidas, mas é importante considerar que investimentos envolvem riscos."));
        TitledPane tp6 = new TitledPane("6. O QUE É PERFIL INVESTIDOR?",
                createContent("Perfil investidor é uma classificação que indica sua tolerância a riscos e objetivos financeiros, ajudando a escolher investimentos adequados."));
        TitledPane tp7 = new TitledPane("7. QUAIS SÃO OS PERFIS INVESTIDORES?",
                createContent("Os perfis típicos são Conservador, Moderado e Agressivo, variando conforme a disposição para risco."));
        TitledPane tp8 = new TitledPane("8. COMO POSSO ALTERAR MEU PERFIL INVESTIDOR?",
                createContent("Você pode alterar seu perfil acessando as configurações da conta e refazendo o questionário de perfil."));
        TitledPane tp9 = new TitledPane("9. POR QUE PRECISO RESPONDER AO QUESTIONÁRIO DURANTE O CADASTRO?",
                createContent("O questionário ajuda a identificar seu perfil investidor, garantindo recomendações personalizadas e seguras."));
        TitledPane tp10 = new TitledPane("10. POSSO REFAZER O QUESTIONÁRIO?",
                createContent("Sim, você pode refazer o questionário a qualquer momento nas configurações da conta."));

        // Adicionando os TitledPanes ao Accordion
        faqAccordion.getPanes().addAll(tp1, tp2, tp3, tp4, tp5, tp6, tp7, tp8, tp9, tp10);

        // Configurando para expandir apenas um item por vez
        faqAccordion.setExpandedPane(tp1);

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
    }

    // Método auxiliar para criar o conteúdo das respostas
    private VBox createContent(String text) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setStyle("-fx-font-size: 14; -fx-text-fill: #333333; -fx-font-family: 'Roboto';");

        VBox content = new VBox(label);
        content.setStyle("-fx-padding: 8; -fx-background-color: #F5F5F5;");
        return content;
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            // Carrega o FXML da tela inicial (Home)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/Home.fxml"));
            Parent homePage = loader.load();

            // Obtém o Stage atual a partir do evento
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Define a nova cena
            Scene scene = new Scene(homePage);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao voltar para a tela inicial: " + e.getMessage());
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