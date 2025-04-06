package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.CalculadoraVariavel;
import com.example.aginvest.model.produtos.Fiis;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class ResultadoFiisController {
    @FXML
    private VBox ativosContainer;
    @FXML
    private VBox ativosSection;

    @FXML
    public void initialize() {
        // Inicialização se necessário
    }

    public void CalcularFiis(Fiis fiis) {
        CalculadoraVariavel calculadoraVariavel = new CalculadoraVariavel();
        List<Fiis> resultados = calculadoraVariavel.simularFundoImobiliario(fiis);

        // Limpa o container antes de adicionar novos ativos
        ativosContainer.getChildren().clear();

        // Adiciona cada ativo ao container
        for (Fiis fii : resultados) {
            VBox ativoBox = criarFiiBox(fii);
            ativosContainer.getChildren().add(ativoBox);
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
        nomeLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 14; -fx-font-weight: bold;");

        // Quantidade de cotas
        Label qtdLabel = new Label("Quantidade Total Cotas: " + fii.getQtdCotas());
        qtdLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        // Saldo cotas
        Label saldoCotasLabel = new Label("Saldo Cotas: R$" + String.format("%,.2f", fii.getSaldoCotas()));
        saldoCotasLabel.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");

        // Saldo dividendos
        Label saldoDividendosLabel = new Label("Saldo Dividendos: R$" + String.format("%,.2f", fii.getSaldoDividendos()));
        saldoDividendosLabel.setStyle("-fx-text-fill: #1FCE52; -fx-font-size: 12;");

        // Adiciona todos os labels ao VBox
        box.getChildren().addAll(
                nomeLabel,
                qtdLabel,
                saldoCotasLabel,
                saldoDividendosLabel
        );

        return box;
    }
}