package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.user.UserController;
import com.example.aginvest.controller.user.UserSession;
import com.example.aginvest.model.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

public class QuestionarioPerfilController {
    private UserModel user;
    // Botões de cada pergunta
    @FXML private RadioButton pergunta1A;
    @FXML private RadioButton pergunta1B;
    @FXML private RadioButton pergunta1C;

    @FXML private RadioButton pergunta2A;
    @FXML private RadioButton pergunta2B;
    @FXML private RadioButton pergunta2C;

    @FXML private RadioButton pergunta3A;
    @FXML private RadioButton pergunta3B;
    @FXML private RadioButton pergunta3C;

    @FXML private RadioButton pergunta4A;
    @FXML private RadioButton pergunta4B;
    @FXML private RadioButton pergunta4C;

    @FXML private RadioButton pergunta5A;
    @FXML private RadioButton pergunta5B;
    @FXML private RadioButton pergunta5C;

    @FXML private RadioButton pergunta6A;
    @FXML private RadioButton pergunta6B;
    @FXML private RadioButton pergunta6C;

    @FXML private RadioButton pergunta7A;
    @FXML private RadioButton pergunta7B;
    @FXML private RadioButton pergunta7C;

    @FXML private RadioButton pergunta8A;
    @FXML private RadioButton pergunta8B;
    @FXML private RadioButton pergunta8C;

    @FXML private RadioButton pergunta9A;
    @FXML private RadioButton pergunta9B;
    @FXML private RadioButton pergunta9C;

    @FXML private RadioButton pergunta10A;
    @FXML private RadioButton pergunta10B;
    @FXML private RadioButton pergunta10C;

    @FXML private Button finalizarCadastro;
    @FXML private Button homeButton;


    @FXML
    private void onClickVoltar() {
        carregarTela("/com/example/aginvest/Conta.fxml", "Conta - Invest7");
    }

    @FXML
    private void onClickHome() {
        carregarTela("/com/example/aginvest/home.fxml", "Home - Invest7");
    }


    @FXML
    private void confirmarRespostas() {
        // Verificar se todas as perguntas foram respondidas
        if (!validarRespostas()) {
            mostrarAlerta("Atenção", "Por favor, responda todas as perguntas antes de continuar.");
            return;
        }

        /// Processar respostas
        String[] respostas = {
                obterResposta(pergunta1A, pergunta1B, pergunta1C),
                obterResposta(pergunta2A, pergunta2B, pergunta2C),
                obterResposta(pergunta3A, pergunta3B, pergunta3C),
                obterResposta(pergunta4A, pergunta4B, pergunta4C),
                obterResposta(pergunta5A, pergunta5B, pergunta5C),
                obterResposta(pergunta6A, pergunta6B, pergunta6C),
                obterResposta(pergunta7A, pergunta7B, pergunta7C),
                obterResposta(pergunta8A, pergunta8B, pergunta8C),
                obterResposta(pergunta9A, pergunta9B, pergunta9C),
                obterResposta(pergunta10A, pergunta10B, pergunta10C)
        };

        // Calcular perfil
        int perfil = calcularPerfil(respostas);
        UserController userController = new UserController();
        if (user != null) {
            boolean userCreate = userController.criarUser(
                    user.getNome(),
                    user.getCpf(),
                    user.getEndereco(),
                    user.getEmail(),
                    user.getSenhaHash(),
                    user.getGenero(),
                    user.getDt_nasc(),
                    perfil
            );

            if (userCreate) {
                carregarTelaHome();
            } else {
                mostrarAlerta("Erro", "Falha ao cadastrar usuário.");
            }
        }
    }

    @FXML
    private void confirmarRefazer() {
        // Verificar se todas as perguntas foram respondidas
        if (!validarRespostas()) {
            mostrarAlerta("Atenção", "Por favor, responda todas as perguntas antes de continuar.");
            return;
        }

        // Processar respostas
        String[] respostas = {
                obterResposta(pergunta1A, pergunta1B, pergunta1C),
                obterResposta(pergunta2A, pergunta2B, pergunta2C),
                obterResposta(pergunta3A, pergunta3B, pergunta3C),
                obterResposta(pergunta4A, pergunta4B, pergunta4C),
                obterResposta(pergunta5A, pergunta5B, pergunta5C),
                obterResposta(pergunta6A, pergunta6B, pergunta6C),
                obterResposta(pergunta7A, pergunta7B, pergunta7C),
                obterResposta(pergunta8A, pergunta8B, pergunta8C),
                obterResposta(pergunta9A, pergunta9B, pergunta9C),
                obterResposta(pergunta10A, pergunta10B, pergunta10C)
        };

        // Calcular perfil
        int perfil = calcularPerfil(respostas);
        UserController userController = new UserController();
        int userId = UserSession.getLoggedInUserId();

        if (userId != 0) { // Verifica se há um usuário logado
          UserModel atualizado = userController.atualizarPerfil(new UserModel(userId, perfil));

            if (atualizado != null) {
                carregarTela("/com/example/aginvest/Conta.fxml", "Conta - Invest7");
            } else {
                mostrarAlerta("Erro", "Falha ao atualizar perfil. Tente novamente.");
            }
        } else {
            mostrarAlerta("Erro", "Nenhum usuário logado encontrado.");
        }
    }
    private String obterResposta(RadioButton opcao1, RadioButton opcao2, RadioButton opcao3) {
        if (opcao1 != null && opcao1.isSelected()) return opcao1.getUserData().toString();
        if (opcao2 != null && opcao2.isSelected()) return opcao2.getUserData().toString();
        if (opcao3 != null && opcao3.isSelected()) return opcao3.getUserData().toString();
        return ""; // Não deveria acontecer, pois validamos antes
    }

    private boolean validarRespostas() {
        return verificarSelecionado(pergunta1A, pergunta1B, pergunta1C) &&
                verificarSelecionado(pergunta2A, pergunta2B, pergunta2C) &&
                verificarSelecionado(pergunta3A, pergunta3B, pergunta3C) &&
                verificarSelecionado(pergunta4A, pergunta4B, pergunta4C) &&
                verificarSelecionado(pergunta5A, pergunta5B, pergunta5C) &&
                verificarSelecionado(pergunta6A, pergunta6B, pergunta6C) &&
                verificarSelecionado(pergunta7A, pergunta7B, pergunta7C) &&
                verificarSelecionado(pergunta8A, pergunta8B, pergunta8C) &&
                verificarSelecionado(pergunta9A, pergunta9B, pergunta9C) &&
                verificarSelecionado(pergunta10A, pergunta10B, pergunta10C);
    }

    private int calcularPerfil(String[] respostas) {
        int pontuacao = 0;
        for (String resposta : respostas) {
            if (resposta.equals("A")) pontuacao += 1;
            if (resposta.equals("B")) pontuacao += 2;
            if (resposta.equals("C")) pontuacao += 3;
        }
        // Mapear para 1, 2 ou 3 baseado na pontuação
        if (pontuacao <= 15) {          // 10-15 pontos = Conservador
            return 1;
        } else if (pontuacao <= 25) {    // 16-25 pontos = Moderado
            return 2;
        } else {                         // 26-30 pontos = Arrojado
            return 3;
        }
    }
    private boolean verificarSelecionado(RadioButton opcao1, RadioButton opcao2, RadioButton opcao3) {
        if (opcao1 == null || opcao2 == null || opcao3 == null) {
            System.err.println("Erro: Um dos RadioButtons não foi inicializado!");
            return false;
        }
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


    private void carregarTelaHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/Home.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage) finalizarCadastro.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar tela principal: " + e.getMessage());
        }
    }

    // Método genérico para carregar telas
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
            System.out.println("Erro ao carregar a tela: " + e.getMessage());
        }
    }
}