package com.example.aginvest.controller.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class QuestionarioPerfilController {

    // Grupos de toggle para cada pergunta
    @FXML private ToggleGroup pergunta1Group;
    @FXML private ToggleGroup pergunta2Group;
    @FXML private ToggleGroup pergunta3Group;
    @FXML private ToggleGroup pergunta4Group;
    @FXML private ToggleGroup pergunta5Group;
    @FXML private ToggleGroup pergunta6Group;
    @FXML private ToggleGroup pergunta7Group;
    @FXML private ToggleGroup pergunta8Group;
    @FXML private ToggleGroup pergunta9Group;
    @FXML private ToggleGroup pergunta10Group;

    @FXML
    private void confirmarRespostas() {
        // Verificar se todas as perguntas foram respondidas
        if (!validarRespostas()) {
            mostrarAlerta("Atenção", "Por favor, responda todas as perguntas antes de continuar.");
            return;
        }

        // Processar respostas
        String[] respostas = new String[10];
        respostas[0] = ((RadioButton) pergunta1Group.getSelectedToggle()).getUserData().toString();
        respostas[1] = ((RadioButton) pergunta2Group.getSelectedToggle()).getUserData().toString();
        respostas[2] = ((RadioButton) pergunta3Group.getSelectedToggle()).getUserData().toString();
        respostas[3] = ((RadioButton) pergunta4Group.getSelectedToggle()).getUserData().toString();
        respostas[4] = ((RadioButton) pergunta5Group.getSelectedToggle()).getUserData().toString();
        respostas[5] = ((RadioButton) pergunta6Group.getSelectedToggle()).getUserData().toString();
        respostas[6] = ((RadioButton) pergunta7Group.getSelectedToggle()).getUserData().toString();
        respostas[7] = ((RadioButton) pergunta8Group.getSelectedToggle()).getUserData().toString();
        respostas[8] = ((RadioButton) pergunta9Group.getSelectedToggle()).getUserData().toString();
        respostas[9] = ((RadioButton) pergunta10Group.getSelectedToggle()).getUserData().toString();

        // Calcular perfil
        String perfil = calcularPerfil(respostas);

        // Mostrar resultado
        mostrarAlerta("Seu Perfil Investidor",
                "Com base em suas respostas, seu perfil é: " + perfil + "\n\n" +
                        "Conservador: Prioriza segurança acima de tudo\n" +
                        "Moderado: Equilíbrio entre risco e retorno\n" +
                        "Arrojado: Aceita maiores riscos por retornos potenciais mais altos");
    }

    private boolean validarRespostas() {
        return pergunta1Group.getSelectedToggle() != null &&
                pergunta2Group.getSelectedToggle() != null &&
                pergunta3Group.getSelectedToggle() != null &&
                pergunta4Group.getSelectedToggle() != null &&
                pergunta5Group.getSelectedToggle() != null &&
                pergunta6Group.getSelectedToggle() != null &&
                pergunta7Group.getSelectedToggle() != null &&
                pergunta8Group.getSelectedToggle() != null &&
                pergunta9Group.getSelectedToggle() != null &&
                pergunta10Group.getSelectedToggle() != null;
    }

    private String calcularPerfil(String[] respostas) {
        int conservador = 0;
        int moderado = 0;
        int arrojado = 0;

        for (String resposta : respostas) {
            switch (resposta) {
                case "A": conservador++; break;
                case "B": moderado++; break;
                case "C": arrojado++; break;
            }
        }

        if (arrojado > moderado && arrojado > conservador) return "Arrojado";
        if (moderado > conservador) return "Moderado";
        return "Conservador";
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}