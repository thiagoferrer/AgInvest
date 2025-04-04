package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.user.UserController;
import com.example.aginvest.model.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class DadosDaContaController {

    // Botões do cabeçalho
    @FXML
    private Button homeButton;
    @FXML
    private Button faqButton;
    @FXML
    private Button contaButton;

    // Labels de informação
    @FXML
    private Label nomeLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label aniversarioLabel;
    @FXML
    private Label generoLabel;
    @FXML
    private Label enderecoLabel;
    @FXML
    private Label cpfLabel;
    @FXML
    private Label perfilLabel;

    // Botões de ação
    @FXML
    private Button voltarButton;

    @FXML
    private void onClickHome() {
        carregarTela("/com/example/aginvest/home.fxml", "Home - Invest7");
    }

    @FXML
    public void initialize() {
        setDados();
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

    // Método para o botão "Voltar"
    @FXML
    public void onClickVoltar() {
        try {
            java.net.URL fxmlLocation = getClass().getResource("/com/example/aginvest/Conta.fxml");
            if (fxmlLocation == null) {
                throw new IOException("Não foi possível encontrar o arquivo Conta.fxml");
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            Scene contaScene = new Scene(root);
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            stage.setScene(contaScene);
            stage.setTitle("Configurações de Conta - Invest7");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela Configurações de Conta: " + e.getMessage());
        }
    }

    // Método para atualizar os dados exibidos (opcional, caso os dados sejam dinâmicos)
    public void setDados()
    {

        UserController userController = new UserController();

        UserModel user = userController.lerUser();

        nomeLabel.setText("Nome Completo: " + user.getNome());
        emailLabel.setText("E-mail: " + user.getEmail());
        aniversarioLabel.setText("Data nascimento: " + user.getDt_nasc());
        generoLabel.setText("Gênero: " + user.getGenero());
        enderecoLabel.setText("Endereço: " + user.getEndereco());
        cpfLabel.setText("CPF: " + user.getCpf());
        perfilLabel.setText("Perfil Investidor: " + user.getDescricao_perfil());
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