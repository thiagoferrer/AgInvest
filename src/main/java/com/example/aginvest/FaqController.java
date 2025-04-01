package com.example.aginvest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FaqController {

    @FXML
    private Button homeButton;
    @FXML
    private Button faqButton;
    @FXML
    private Button contaButton;
    @FXML
    private VBox faqContainer;

    @FXML
    public void initialize() {
        criarFaqDropdown();
    }

    private void criarFaqDropdown() {
        List<String> perguntasRespostas = Arrays.asList(
                "1. O que é um simulador de investimentos?",
                "É uma ferramenta que permite calcular os possíveis retornos de um investimento com base em diferentes parâmetros.",

                "2. Como calcular o retorno de um investimento?",
                "O retorno pode ser calculado considerando o valor inicial investido, a taxa de juros ou rendimento, e o período de tempo.",

                "3. Quais são os tipos de investimentos simulados?",
                "Simulamos renda fixa (CDB, LCI, LCA), tesouro direto, fundos de investimento e ações.",

                "4. O simulador garante retornos reais?",
                "Não, o simulador mostra projeções baseadas em dados históricos e premissas, que podem não refletir os resultados futuros.",

                "5. Posso confiar nos cálculos do simulador?",
                "Nossos cálculos seguem padrões financeiros, mas recomendamos sempre consultar um especialista antes de investir.",

                "6. O que é perfil investidor?",
                "É uma classificação que indica seu nível de tolerância a riscos nos investimentos (conservador, moderado ou arrojado).",

                "7. Quais são os perfis investidores?",
                "Conservador (baixo risco), Moderado (risco médio) e Arrojado (alto risco, com potencial de maior retorno).",

                "8. Como posso alterar meu perfil investidor?",
                "Você pode refazer o questionário de perfil investidor na seção de configurações da sua conta.",

                "9. Por que preciso responder ao questionário durante o cadastro?",
                "O questionário ajuda a determinar seu perfil para recomendarmos os investimentos mais adequados para você.",

                "10. Posso refazer o questionário?",
                "Sim, você pode refazer o questionário a qualquer momento na seção de perfil da sua conta."
        );

        for (int i = 0; i < perguntasRespostas.size(); i += 2) {
            String pergunta = perguntasRespostas.get(i);
            String resposta = perguntasRespostas.get(i + 1);

            adicionarItemFaq(pergunta, resposta);
        }
    }

    private void adicionarItemFaq(String pergunta, String resposta) {
        ToggleButton toggleButton = new ToggleButton(pergunta);
        toggleButton.setFont(Font.font(14));
        toggleButton.setStyle("-fx-background-color: transparent; -fx-border-color: #E0E0E0; -fx-border-width: 0 0 1 0; -fx-alignment: CENTER_LEFT;");
        toggleButton.setMaxWidth(Double.MAX_VALUE);

        TextFlow respostaFlow = new TextFlow(new Text(resposta));
        respostaFlow.setPadding(new Insets(8, 0, 16, 16));
        respostaFlow.setStyle("-fx-background-color: #F9F9F9; -fx-padding: 8; -fx-background-radius: 4;");
        respostaFlow.setVisible(false);

        VBox itemBox = new VBox(toggleButton, respostaFlow);
        itemBox.setSpacing(4);

        toggleButton.selectedProperty().addListener((obs, oldVal, newVal) -> {
            respostaFlow.setVisible(newVal);
            toggleButton.setStyle(newVal ?
                    "-fx-background-color: #E3F2FD; -fx-border-color: #E0E0E0; -fx-border-width: 0 0 1 0; -fx-alignment: CENTER_LEFT;" :
                    "-fx-background-color: transparent; -fx-border-color: #E0E0E0; -fx-border-width: 0 0 1 0; -fx-alignment: CENTER_LEFT;");
        });

        faqContainer.getChildren().add(itemBox);
    }

    @FXML
    private void onClickHome() {
        carregarTela("/com/example/aginvest/Home.fxml");
    }

    @FXML
    private void onClickFaq() {
        // Já estamos na tela de FAQ
    }

    @FXML
    private void onClickConta() {
        carregarTela("/com/example/aginvest/Conta.fxml");
    }

    @FXML
    private void onClickVoltar() {
        onClickHome();
    }

    private void carregarTela(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) homeButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}