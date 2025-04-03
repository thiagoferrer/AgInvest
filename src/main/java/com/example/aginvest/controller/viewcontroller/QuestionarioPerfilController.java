package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.user.UserController;
import com.example.aginvest.model.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class QuestionarioPerfilController {
    private UserModel user;
    // Botões de cada pergunta
    @FXML private RadioButton pergunta1Opcao1;
    @FXML private RadioButton pergunta1Opcao2;
    @FXML private RadioButton pergunta1Opcao3;

    @FXML private RadioButton pergunta2Opcao1;
    @FXML private RadioButton pergunta2Opcao2;
    @FXML private RadioButton pergunta2Opcao3;

    @FXML private RadioButton pergunta3Opcao1;
    @FXML private RadioButton pergunta3Opcao2;
    @FXML private RadioButton pergunta3Opcao3;

    @FXML private RadioButton pergunta4Opcao1;
    @FXML private RadioButton pergunta4Opcao2;
    @FXML private RadioButton pergunta4Opcao3;

    @FXML private RadioButton pergunta5Opcao1;
    @FXML private RadioButton pergunta5Opcao2;
    @FXML private RadioButton pergunta5Opcao3;

    @FXML private RadioButton pergunta6Opcao1;
    @FXML private RadioButton pergunta6Opcao2;
    @FXML private RadioButton pergunta6Opcao3;

    @FXML private RadioButton pergunta7Opcao1;
    @FXML private RadioButton pergunta7Opcao2;
    @FXML private RadioButton pergunta7Opcao3;

    @FXML private RadioButton pergunta8Opcao1;
    @FXML private RadioButton pergunta8Opcao2;
    @FXML private RadioButton pergunta8Opcao3;

    @FXML private RadioButton pergunta9Opcao1;
    @FXML private RadioButton pergunta9Opcao2;
    @FXML private RadioButton pergunta9Opcao3;

    @FXML private RadioButton pergunta10Opcao1;
    @FXML private RadioButton pergunta10Opcao2;
    @FXML private RadioButton pergunta10Opcao3;

    @FXML private Button finalizarCadastro;



    @FXML
    private void confirmarRespostas() {
        // Verificar se todas as perguntas foram respondidas
        if (!validarRespostas()) {
            mostrarAlerta("Atenção", "Por favor, responda todas as perguntas antes de continuar.");
            return;
        }

        /// Processar respostas
        String[] respostas = {
                obterResposta(pergunta1Opcao1, pergunta1Opcao2, pergunta1Opcao3),
                obterResposta(pergunta2Opcao1, pergunta2Opcao2, pergunta2Opcao3),
                obterResposta(pergunta3Opcao1, pergunta3Opcao2, pergunta3Opcao3),
                obterResposta(pergunta4Opcao1, pergunta4Opcao2, pergunta4Opcao3),
                obterResposta(pergunta5Opcao1, pergunta5Opcao2, pergunta5Opcao3),
                obterResposta(pergunta6Opcao1, pergunta6Opcao2, pergunta6Opcao3),
                obterResposta(pergunta7Opcao1, pergunta7Opcao2, pergunta7Opcao3),
                obterResposta(pergunta8Opcao1, pergunta8Opcao2, pergunta8Opcao3),
                obterResposta(pergunta9Opcao1, pergunta9Opcao2, pergunta9Opcao3),
                obterResposta(pergunta10Opcao1, pergunta10Opcao2, pergunta10Opcao3)
        };

        // Calcular perfil
        int perfil = calcularPerfil(respostas);
        UserController userController = new UserController();
        if (user != null) {
            boolean userCreate = userController.criarUser(
                    user.getNome(),
                    user.getEmail(),
                    user.getSenhaHash(),
                    user.getCpf(),
                    user.getEndereco(),
                    user.getGenero(),
                    user.getDt_nasc(),
                    perfil
            );

            if (userCreate) {
                mostrarAlerta("Sucesso", "Usuário cadastrado com sucesso!");
            } else {
                mostrarAlerta("Erro", "Falha ao cadastrar usuário.");
            }
        }
    }
    private String obterResposta(RadioButton opcao1, RadioButton opcao2, RadioButton opcao3) {
        if (opcao1.isSelected()) return opcao1.getUserData().toString();
        if (opcao2.isSelected()) return opcao2.getUserData().toString();
        if (opcao3.isSelected()) return opcao3.getUserData().toString();
        return ""; // Não deveria acontecer, pois validamos antes
    }

    private boolean validarRespostas() {
        return verificarSelecionado(pergunta1Opcao1, pergunta1Opcao2, pergunta1Opcao3) &&
                verificarSelecionado(pergunta2Opcao1, pergunta2Opcao2, pergunta2Opcao3) &&
                verificarSelecionado(pergunta3Opcao1, pergunta3Opcao2, pergunta3Opcao3) &&
                verificarSelecionado(pergunta4Opcao1, pergunta4Opcao2, pergunta4Opcao3) &&
                verificarSelecionado(pergunta5Opcao1, pergunta5Opcao2, pergunta5Opcao3) &&
                verificarSelecionado(pergunta6Opcao1, pergunta6Opcao2, pergunta6Opcao3) &&
                verificarSelecionado(pergunta7Opcao1, pergunta7Opcao2, pergunta7Opcao3) &&
                verificarSelecionado(pergunta8Opcao1, pergunta8Opcao2, pergunta8Opcao3) &&
                verificarSelecionado(pergunta9Opcao1, pergunta9Opcao2, pergunta9Opcao3) &&
                verificarSelecionado(pergunta10Opcao1, pergunta10Opcao2, pergunta10Opcao3);
    }

    private int calcularPerfil(String[] respostas) {
        int pontuacao = 0;
        for (String resposta : respostas) {
            if (resposta.equals("A")) pontuacao += 1;
            if (resposta.equals("B")) pontuacao += 2;
            if (resposta.equals("C")) pontuacao += 3;
        }
        return pontuacao;
    }
    private boolean verificarSelecionado(RadioButton opcao1, RadioButton opcao2, RadioButton opcao3) {
        return opcao1.isSelected() || opcao2.isSelected() || opcao3.isSelected();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public void setUser(UserModel user) {
        this.user = user;
        System.out.println("Usuário recebido: " + user.getNome());
    }
    private void carregarTelaQuestionario(UserModel user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/Home.fxml"));
            Scene mainScene = new Scene(loader.load(), 360, 640);


            // Aplicar CSS se necessário
            String css = getClass().getResource("/com/example/aginvest/styles.css").toExternalForm();
            if (css != null) {
                mainScene.getStylesheets().add(css);
            }


            Stage stage = (Stage) finalizarCadastro.getScene().getWindow();
            stage.setScene(mainScene);
            stage.setTitle("Tela Principal");
            stage.setWidth(360);
            stage.setHeight(640);
            stage.centerOnScreen();
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar tela principal: " + e.getMessage());
        }
    }
}