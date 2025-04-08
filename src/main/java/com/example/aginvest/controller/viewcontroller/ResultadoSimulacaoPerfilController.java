package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.CalculadoraVariavel;
import com.example.aginvest.controller.user.UserSession;
import com.example.aginvest.dao.UserDAO;
import com.example.aginvest.model.UserModel;
import com.example.aginvest.model.produtos.Acoes;
import com.example.aginvest.model.produtos.Fiis;
import com.example.aginvest.model.produtos.RendaFixa;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultadoSimulacaoPerfilController {


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
    private void onClickHome() {
        carregarTela("/com/example/aginvest/home.fxml", "Home - Invest7");
    }


    @FXML private void onClickVoltar(){carregarTela("/com/example/aginvest/SimulacaoPorPerfil.fxml", "Simulacao por Perfil- Invest7"); }


    @FXML
    private VBox ativosContainer;
    private String descricao_perfil;
    private  int userId = UserSession.getLoggedInUserId();


        @FXML
        public void initialize() {
            UserDAO userDAO= new UserDAO();
            UserModel user = userDAO.readUser(new UserModel(userId));

            this.descricao_perfil = user.getDescricao_perfil();

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


    public void CalcularAtivos(Acoes acoes, Fiis fiis){
        CalculadoraVariavel calculadora = new CalculadoraVariavel();
        List<Acoes> acoesSimuladas= new ArrayList<>();
        List<Fiis> fiisSimuladas = new ArrayList<>();
        List<RendaFixa> rendaSimuladas = new ArrayList<>();

        switch (descricao_perfil){
            case "moderado":
                acoesSimuladas= calculadora.simularAcaoPerfil(acoes.getValorInvestido(), acoes.getMeses(), 2);
                fiisSimuladas = calculadora.simularFundoImobiliarioPerfil(fiis, 5);
                //rendaSimulada;



                break;

            case "arrojado":
                acoesSimuladas = calculadora.simularAcaoPerfil(acoes.getValorInvestido(), acoes.getMeses(), 6);
                fiisSimuladas = calculadora.simularFundoImobiliarioPerfil(fiis, 2);
                //rendaSimulada;
                break;

            case "conservador":
                acoesSimuladas = calculadora.simularAcaoPerfil(acoes.getValorInvestido(), acoes.getMeses(), 2);
                fiisSimuladas = calculadora.simularFundoImobiliarioPerfil(fiis, 2);
                //rendaSimulada;

                // Adiciona cada ativo ao container
                for (Fiis fii : fiisSimuladas ) {
                    VBox ativoBox = criarFiiBox(fii);
                    ativosContainer.getChildren().add(ativoBox);
                }

                // Adiciona cada ativo ao container
                for (Acoes acao : acoesSimuladas ) {
                    VBox ativoBox = criarAtivoBox(acao);
                    ativosContainer.getChildren().add(ativoBox);
                }
                break;
        }

    }

    public void CalcularFiis(Fiis fiis) {
        CalculadoraVariavel calculadoraVariavel = new CalculadoraVariavel();
        List<Fiis> resultados = calculadoraVariavel.simularFundoImobiliario(fiis);


        // Adiciona cada ativo ao container
        for (Fiis fii : resultados) {
            VBox ativoBox = criarFiiBox(fii);
            ativosContainer.getChildren().add(ativoBox);
        }
    }

    public void CalcularAcoes(Acoes acoes) {
        CalculadoraVariavel calculadoraVariavel = new CalculadoraVariavel();
        List<Acoes> resultados = calculadoraVariavel.simularAcao(acoes);
        // Limpa os containers antes de adicionar novos elementos
        ativosContainer.getChildren().clear();


        // Adiciona cada ativo ao container
        for (Acoes acao : resultados) {
            VBox ativoBox = criarAtivoBox(acao);
            ativosContainer.getChildren().add(ativoBox);
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


    private VBox criarFiiBox(Fiis fii) {
        VBox box = new VBox();
        box.setSpacing(4);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPrefWidth(300);
        box.setStyle("-fx-border-color: #1E90FF; -fx-border-radius: 8; -fx-border-width: 1; -fx-padding: 8;");

        // Nome do FII
        Label nomeLabel = new Label(fii.getNome());
        nomeLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 14; -fx-font-weight: bold;");

        // Quantidade de cotas
        Label qtdLabel = new Label("Quantidade Total Cotas: ");
        qtdLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label qtdColorLabel = new Label(String.valueOf(fii.getQtdCotas()));
        qtdColorLabel.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");
        TextFlow linhaQtd = new TextFlow(qtdLabel, qtdColorLabel);

        // Saldo cotas
        Label saldoCotasLabel = new Label("Saldo Cotas: ");
        saldoCotasLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label saldoCotasColorLabel = new Label(String.format("R$ %,.2f", fii.getSaldoCotas()));
        saldoCotasColorLabel.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");
        TextFlow linhaSaldoCotas = new TextFlow(saldoCotasLabel, saldoCotasColorLabel);


        // Saldo dividendos
        Label saldoDividendosLabel = new Label("Saldo Dividendos: ");
        saldoDividendosLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label saldoDividendosColorLabel = new Label(String.format("R$ %,.2f", fii.getSaldoDividendos()));
        saldoDividendosColorLabel.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");
        TextFlow linhaSaldoDivs = new TextFlow(saldoDividendosLabel, saldoDividendosColorLabel);

        // Dividendos Mensais
        Label dividendosMensaisLabel = new Label("Dividendos Mensais: ");
        dividendosMensaisLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label divColorMensaisLabel = new Label("R$" + String.format("%,.2f", fii.getDividendosMensais()));
        divColorMensaisLabel.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");
        TextFlow linhaDivsMensais = new TextFlow(dividendosMensaisLabel, divColorMensaisLabel);

        // Adiciona todos os labels ao VBox
        box.getChildren().addAll(
                nomeLabel,
                linhaQtd,
                linhaSaldoCotas,
                linhaSaldoDivs,
                linhaDivsMensais
        );

        return box;
    }

    private VBox criarAtivoBox(Acoes acao) {
        VBox box = new VBox();
        box.setSpacing(4);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPrefWidth(300);
        box.setStyle("-fx-border-color: #1E90FF; -fx-border-radius: 8; -fx-border-width: 1; -fx-padding: 8;");

        Label nomeLabel = new Label(acao.getNome());
        nomeLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 14; -fx-font-weight: bold;");

        Label qtdLabel = new Label("Quantidade Total Cotas: ");
        qtdLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label qtdColorLabel = new Label(String.valueOf(acao.getQtdAcoes()));
        qtdColorLabel.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");
        TextFlow linhaQtd = new TextFlow(qtdLabel, qtdColorLabel);

        Label valorInvestidoLabel = new Label("Valor Investido: ");
        valorInvestidoLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label vlrInvestColor = new Label("R$ "+ String.format("%,.2f", acao.getValorInvestido()));
        vlrInvestColor.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");
        TextFlow linhaVlrInvest = new TextFlow(valorInvestidoLabel, vlrInvestColor);

        Label totalCompraLabel = new Label("Total compra: ");
        totalCompraLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label totalCompraColor = new Label("R$" + String.format("%,.2f", acao.getCustoTotalCompra()));
        totalCompraColor.setStyle("-fx-text-fill: #EA7A5D; -fx-font-size: 12;");
        TextFlow linhaTotalCompra = new TextFlow(totalCompraLabel, totalCompraColor);

        Label totalVendaLabel = new Label("Total venda: ");
        totalVendaLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label totalVendaColor = new Label("R$"+ String.format("%,.2f", acao.getValorTotalVenda()));
        totalVendaColor.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");
        TextFlow linhaTotalVenda = new TextFlow(totalVendaLabel, totalVendaColor);

        Label saldoFinalLabel = new Label("Saldo Final: ");
        saldoFinalLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label saldoFinalColor = new Label("R$ " + String.format("%,.2f", acao.getSaldoFinal()));
        String corTexto = acao.getSaldoFinal() >= 0 ? "#1FCE52" : "#FF4C4C";
        saldoFinalColor.setStyle("-fx-text-fill: " + corTexto + "; -fx-font-size: 12;");
        TextFlow linhaSaldoFinal = new TextFlow(saldoFinalLabel, saldoFinalColor);

        Label trocoLabel = new Label("Troco Valor Investido: ");
        trocoLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label trocoColor = new Label("R$ " + String.format("%,.2f", acao.getTroco()));
        String corTextoTroco = acao.getTroco() >= 0 ? "#1FCE52" : "#FF4C4C";
        trocoColor.setStyle("-fx-text-fill: " + corTextoTroco + "; -fx-font-size: 12;");
        TextFlow linhaTroco = new TextFlow(trocoLabel, trocoColor);

        box.getChildren().addAll(
                nomeLabel,
                linhaQtd ,
                linhaVlrInvest ,
                linhaTotalCompra,
                linhaTotalVenda ,
                linhaSaldoFinal,
                linhaTroco
        );

        return box;
    }


}
