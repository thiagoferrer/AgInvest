package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.model.produtos.Acoes;
import com.example.aginvest.model.produtos.Fiis;
import com.example.aginvest.model.produtos.RendaFixa;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;

public class SimulacaoPorPerfilController {

    // Campos de entrada
    @FXML
    private TextField aporteMensalField;
    @FXML
    private TextField quantidadeCotasField;
    @FXML
    private TextField prazoField;
    @FXML
    private TextField capitalInicialField;
    // CheckBox para reinvestir dividendos
    @FXML
    private CheckBox reinvestirCheckBox;
    @FXML
    private CheckBox naoReinvestirCheckBox;

    @FXML
    private Button homeButton;
    @FXML
    private Button faqButton;
    @FXML
    private Button contaButton;


    // Botões de ação
    @FXML
    private Button voltarButton;
    @FXML
    private Button calcularButton;


    @FXML
    private void onClickHome() {
        carregarTela("/com/example/aginvest/home.fxml", "Home - Invest7");
    }


    @FXML
    public void initialize() {
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
    }
    // Método para o botão "Calcular"
    @FXML
    public void onClickCalcular() {
        System.out.println("Botão Calcular clicado!");
        System.out.println("Botão Calcular clicado!");
        try {
            double aporteMensal = Double.parseDouble(aporteMensalField.getText());
            int quantidadeCotas = Integer.parseInt(quantidadeCotasField.getText());
            double capitalInicial = Double.parseDouble(capitalInicialField.getText());
            int prazo = Integer.parseInt(prazoField.getText());

            int reinvestirDividendos;
            if (reinvestirCheckBox != null && reinvestirCheckBox.isSelected())
                reinvestirDividendos = 1; // Usuário escolheu REINVESTIR
            else if (naoReinvestirCheckBox != null && naoReinvestirCheckBox.isSelected())
                reinvestirDividendos = 0; // Usuário escolheu NÃO REINVESTIR
            else reinvestirDividendos = 0; // Padrão (caso nenhum esteja selecionado)


            // Transformando o capital inicial em BigDecimal
            BigDecimal capitalInicialBD = BigDecimal.valueOf(Double.parseDouble(capitalInicialField.getText()));
            // Transformando o aporte mensal em BigDecimal
            BigDecimal aporteMensalBD = BigDecimal.valueOf(Double.parseDouble(aporteMensalField.getText()));
            Fiis fiis = new Fiis(aporteMensal, quantidadeCotas, prazo, reinvestirDividendos);

            Acoes acoes = new Acoes(capitalInicial,prazo);


            // DEBUG: Verifique os dados antes de enviar
            System.out.println("Dados antes de enviar:");
            System.out.println("Fiis: " + fiis);
            System.out.println("Acoes: " + acoes);

           // RendaFixa renda = new RendaFixa(capitalInicial, aporteMensal, prazo);
            carregarTelaResultado(acoes, fiis, capitalInicialBD,aporteMensalBD, prazo );
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter valores numéricos: " + e.getMessage());
            // Aqui você pode mostrar um alerta para o usuário também
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

    private void carregarTelaResultado(Acoes acoes, Fiis fiis,  BigDecimal capitalInicialBD, BigDecimal aporteMensalBD,  int prazo ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/ResultadoSimulacaoPerfil.fxml"));
            Parent root = loader.load();

            // Obtém o controller que foi criado pelo FXMLLoader
            ResultadoSimulacaoPerfilController simulacaoPerfil = loader.getController();
            simulacaoPerfil.setDadosSimulacao(acoes, fiis, capitalInicialBD,aporteMensalBD, prazo);

            Stage stage = (Stage) calcularButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar próxima tela: " + e.getMessage());
        }
    }

}
