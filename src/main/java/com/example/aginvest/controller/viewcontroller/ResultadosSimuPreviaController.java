package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.CalculadoraRFprovisoria;
import com.example.aginvest.model.produtos.Acoes;
import com.example.aginvest.model.produtos.Previa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultadosSimuPreviaController {

    @FXML private VBox ativosContainer;

    @FXML private Button voltarButton;
    @FXML private Button calcularButton;

    private double capitalInicialField;
    private double aporteMensalField;
    private int prazoField;
    @FXML private ImageView logoImage;


    public void setDados(double capitalInicial, double aporteMensal, int prazo) {
        this.capitalInicialField = capitalInicial;
        this.aporteMensalField = aporteMensal;
        this.prazoField = prazo;


        calcularPreviaSim();
    }

    @FXML
    public void initialize() {

        Image logo = new Image(getClass().getResource("/image/Logo.png").toExternalForm());
        logoImage.setImage(logo);

        calcularPreviaSim();


        // Configurar ações dos botões
        voltarButton.setOnAction(e -> onClickVoltar());
    }


    @FXML
    private void onClickVoltar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/SimuPrevia.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) voltarButton.getScene().getWindow();
            stage.setScene(new Scene(root, 360, 640));
            stage.setTitle("Invest7");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /*Previa cdb = new Previa("CDB", 0.155);
    Previa tesouro_pre = new Previa("Tesouro Prefixado", 0.1426);
    Previa tesouro_ipca = new Previa("Tesouro IPCA+ 2029", 0.1543);
    Previa tesouro_selic = new Previa("Tesouro Selic", 0.1467);


    private List <Previa> lista_bd = new ArrayList<>(List.of(
            cdb,
            tesouro_pre,
            tesouro_ipca,
            tesouro_selic
    ));*/
    private List<Previa> lista_bd;

    {
        Previa cdb = new Previa("CDB", 0.155);
        Previa tesouro_pre = new Previa("Tesouro Prefixado", 0.1426);
        Previa tesouro_ipca = new Previa("Tesouro IPCA+ 2029", 0.1543);
        Previa tesouro_selic = new Previa("Tesouro Selic", 0.1467);

        lista_bd = new ArrayList<>(List.of(
                cdb,
                tesouro_pre,
                tesouro_ipca,
                tesouro_selic
        ));
    }

    public void calcularPreviaSim () {

        CalculadoraRFprovisoria calculadora = new CalculadoraRFprovisoria();
        List<Previa> listprevia = new ArrayList<>();
        ativosContainer.getChildren().clear();
        double aliq = calculadora.calculoImpRenda(prazoField);

        for (Previa p : lista_bd) {

            double tx = p.getRentabilidadeBruta();
            double rendBruto = calculadora.calculoRendBruto(capitalInicialField, aporteMensalField, tx, prazoField);
            double iR = (rendBruto-capitalInicialField) * aliq;
            double rendLiq = calculadora.calculoRendLiq(rendBruto, capitalInicialField, aliq);

            Previa resultados =  new Previa(p.getNome());
            resultados.setRendimentoBruto(rendBruto);
            resultados.setImpostoIR(iR);
            resultados.setRendimentoLiquido(rendLiq);
            listprevia.add(resultados);


            VBox ativoBox = createAssetCard(resultados);
            ativosContainer.getChildren().add(ativoBox);
        }
    }

    private VBox createAssetCard(Previa p) {
        VBox card = new VBox();
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPrefWidth(300);
        card.setSpacing(4);
        card.setStyle("-fx-border-color: #1E90FF; -fx-border-radius: 8; -fx-border-width: 1; -fx-padding: 8;");

        Label nomeLabel = new Label(p.getNome());
        nomeLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 14; -fx-font-weight: bold;");

        Label RBLabel = new Label("Rendimento Bruto:");
        RBLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label RBColorLabel = new Label(String.valueOf(String.format("R$  %.2f", p.getRendimentoBruto())));
        RBColorLabel.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");
        TextFlow linhaRB = new TextFlow(RBLabel, RBColorLabel);

        Label IRLabel = new Label("Imposto de Renda: ");
        IRLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label IRColorLabel = new Label(String.valueOf(String.format("R$  %.2f", p.getImpostoIR())));
        IRColorLabel.setStyle("-fx-text-fill: #FF4C4C; -fx-font-size: 12;");
        TextFlow linhaIR = new TextFlow(IRLabel, IRColorLabel);

        Label RLLabel = new Label("Rendimento Líquido: ");
        RLLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12;");
        Label RLColorLabel = new Label(String.valueOf(String.format("R$  %.2f", p.getRendimentoLiquido())));
        RLColorLabel.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");
        TextFlow linhaRL = new TextFlow(RLLabel, RLColorLabel);

        card.getChildren().addAll(
                nomeLabel,
                linhaRB,
                linhaIR,
                linhaRL
        );

        return card;
    }


}