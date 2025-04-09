package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.model.produtos.Fiis;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;

public class FiisController {

    // Botões do cabeçalho
    @FXML
    private Button homeButton;
    @FXML
    private Button faqButton;
    @FXML
    private Button contaButton;

    // Campos de entrada
    @FXML
    private TextField aporteMensalField;
    @FXML
    private TextField quantidadeCotasField;
    @FXML
    private TextField prazoField;

    // CheckBox para reinvestir dividendos
    @FXML
    private CheckBox reinvestirCheckBox;
    @FXML
    private CheckBox naoReinvestirCheckBox;

    // Botões de ação
    @FXML
    private Button voltarButton;
    @FXML
    private Button calcularButton;

    @FXML
    private void onClickHome() {
        carregarTela("/com/example/aginvest/home.fxml", "Home - Invest7");
    }


    // Método de inicialização
    @FXML
    public void initialize() {
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

    // Método para o botão "FAQ"
    @FXML
    public void onClickFaq() {
        System.out.println("Botão FAQ clicado!");
        // Adicione lógica para navegar para a tela de FAQ
    }

    // Método para o botão "Conta"
    @FXML
    public void onClickConta() {
        System.out.println("Botão Conta clicado!");
        // Adicione lógica para navegar para a tela de Conta
    }

    // Método para o botão "Voltar"
    @FXML
    public void onClickVoltar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent root = loader.load();
            Scene homeScene = new Scene(root);
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            stage.setScene(homeScene);
            stage.setTitle("Home - Invest7");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela Home: " + e.getMessage());
        }
    }

    @FXML
    public void onClickCalcular() {
        System.out.println("Botão Calcular clicado!");
        try {
            double aporteMensal = Double.parseDouble(aporteMensalField.getText());
            int quantidadeCotas = Integer.parseInt(quantidadeCotasField.getText());
            int prazo = Integer.parseInt(prazoField.getText());
            // Verifica se o reinvestimento está marcado
            int reinvestirDividendos;

            if (reinvestirCheckBox != null && reinvestirCheckBox.isSelected())
                reinvestirDividendos = 1; // Usuário escolheu REINVESTIR
            else if (naoReinvestirCheckBox != null && naoReinvestirCheckBox.isSelected())
                reinvestirDividendos = 0; // Usuário escolheu NÃO REINVESTIR
            else reinvestirDividendos = 0; // Padrão (caso nenhum esteja selecionado)


            carregarTelaResultado(new Fiis(aporteMensal, quantidadeCotas, prazo, reinvestirDividendos ));
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter valores numéricos: " + e.getMessage());
            // Aqui você pode mostrar um alerta para o usuário também
        }
    }

    // Método para garantir que apenas um CheckBox seja selecionado
    @FXML
    public void onReinvestirSelected() {
        if (reinvestirCheckBox.isSelected()) naoReinvestirCheckBox.setSelected(false);
        else naoReinvestirCheckBox.setSelected(true);

    }

    @FXML
    public void onNaoReinvestirSelected() {
        if (naoReinvestirCheckBox.isSelected()) reinvestirCheckBox.setSelected(false);
        else reinvestirCheckBox.setSelected(true);

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

    private void carregarTelaResultado(Fiis fiis) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/ResultadoFiis.fxml"));
            Parent root = loader.load();

            // Aqui está a correção: pegue o controller correto do loader
            ResultadoFiisController fundos = loader.getController();
            fundos.CalcularFiis(fiis);

            Stage stage = (Stage) calcularButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar próxima tela: " + e.getMessage());
        }
    }

}