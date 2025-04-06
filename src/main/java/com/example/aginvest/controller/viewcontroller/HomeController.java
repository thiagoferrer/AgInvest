package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.user.UserSession;
import com.example.aginvest.dao.UserDAO;
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

public class HomeController {
    private  int userId = UserSession.getLoggedInUserId();

    @FXML
    private Button btnFundoImobiliario;

    @FXML
    private Button btnRendaFixa;

    @FXML
    private Button btnAcoes;

    @FXML
    private Button btnFAQ;

    @FXML
    private Label bemVindoLabel;

    @FXML
    private Label perfilConservador;

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

    @FXML private void onClickValoresAcoes(){ carregarTela("/com/example/aginvest/ValoresAcoes.fxml", "Acoes - Inves7");}

    @FXML
    public void initialize() {
        UserDAO userDAO= new UserDAO();
        UserModel user = userDAO.readUser(new UserModel(userId));

        // Definir o texto do Label
        if (user != null) {
            bemVindoLabel.setText("BEM VINDO, " + user.getNome() + "!");
            perfilConservador.setText(user.getDescricao_perfil());
        } else {
            bemVindoLabel.setText("BEM VINDO, USUÁRIO!");
            perfilConservador.setText("Descubra seu perfil");
        }


        // Ação para o botão "Fundo Imobiliário"
        btnFundoImobiliario.setOnAction(event -> {
            System.out.println("Botão Fundo Imobiliário clicado!");
        });

        // Ação para o botão "Renda Fixa"
        btnRendaFixa.setOnAction(event -> {
            System.out.println("Botão Renda Fixa clicado!");
        });

        // Ação para o botão "Ações"
        btnAcoes.setOnAction(event -> {
            System.out.println("Botão Ações clicado!");
        });

        // Ação para o botão "FAQ"
        btnFAQ.setOnAction(event -> {
            System.out.println("Botão FAQ clicado!");
        });

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

        btnFAQ.setOnAction(actionEvent -> {
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

        btnFundoImobiliario.setOnAction(actionEvent -> {
            try {
                // 1. Carrega o novo arquivo FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/Fiis.fxml"));
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

        btnRendaFixa.setOnAction(actionEvent -> {
            try {
                // 1. Carrega o novo arquivo FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/RendaFixa.fxml"));
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

        btnAcoes.setOnAction(actionEvent -> {
            try {
                // 1. Carrega o novo arquivo FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/Acoes.fxml"));
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