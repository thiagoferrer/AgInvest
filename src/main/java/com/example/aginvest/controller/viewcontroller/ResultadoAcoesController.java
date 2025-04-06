package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.CalculadoraVariavel;
import com.example.aginvest.model.produtos.Acoes;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import java.util.List;

public class ResultadoAcoesController {
    @FXML
    private VBox ativosContainer;
    @FXML
    private VBox ativosSection;

    @FXML
    public void initialize() {
        // Inicialização se necessário
    }

    public void CalcularAcoes(Acoes acoes) {
        CalculadoraVariavel calculadoraVariavel = new CalculadoraVariavel();
        List<Acoes> resultados = calculadoraVariavel.simularAcao(acoes);

        // Limpa o container antes de adicionar novos ativos
        ativosContainer.getChildren().clear();

        // Adiciona cada ativo ao container
        for (Acoes acao : resultados) {
            VBox ativoBox = criarAtivoBox(acao);
            ativosContainer.getChildren().add(ativoBox);
        }
    }

    private VBox criarAtivoBox(Acoes acao) {
        VBox box = new VBox();
        box.setSpacing(4);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPrefWidth(300);
        box.setStyle("-fx-border-color: #1E90FF; -fx-border-radius: 8; -fx-border-width: 1; -fx-padding: 8;");

        // Nome do ativo
        Label nomeLabel = new Label(acao.getNome());
        nomeLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 14; -fx-font-weight: bold;");

        // Quantidade de ações
        Label qtdLabel = new Label("Quantidade Total Cotas: " + acao.getQtdAcoes());
        qtdLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        // Valor investido
        Label valorInvestidoLabel = new Label("Valor Investido: R$" + String.format("%,.2f", acao.getValorInvestido()));
        valorInvestidoLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        // Total compra
        Label totalCompraLabel = new Label("Total compra: R$" + String.format("%,.2f", acao.getCustoTotalCompra()));
        totalCompraLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        // Total venda
        Label totalVendaLabel = new Label("Total venda: R$" + String.format("%,.2f", acao.getValorTotalVenda()));
        totalVendaLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        // Saldo final
        Label saldoFinalLabel = new Label("Saldo Final: R$" + String.format("%,.2f", acao.getSaldoFinal()));
        saldoFinalLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        // Troco
        Label trocoLabel = new Label("Troco Valor Investido: R$" + String.format("%,.2f", acao.getTroco()));
        trocoLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");

        // Adiciona todos os labels ao VBox
        box.getChildren().addAll(
                nomeLabel,
                qtdLabel,
                valorInvestidoLabel,
                totalCompraLabel,
                totalVendaLabel,
                saldoFinalLabel,
                trocoLabel
        );

        return box;
    }
}