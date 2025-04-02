package com.example.aginvest.controller;

/*
import com.invest7.controller.user.UserSession;
import com.invest7.dao.AcoesDao;
import com.invest7.dao.FiisDAO;
import com.invest7.model.produtos.Acoes;
import com.invest7.model.produtos.Fiis;

 */

import com.example.aginvest.model.produtos.Acoes;
import com.example.aginvest.model.produtos.Fiis;
import com.example.aginvest.dao.AcoesDao;
import com.example.aginvest.dao.FiisDAO;
import com.example.aginvest.controller.user.UserSession;

import java.util.ArrayList;
import java.util.List;




public class CalculadoraVariavel {
    public static final int REINVESTIR_SIM = 1;
    public static final int REINVESTIR_NAO = 2;
    private static final double LIMITE_ISENCAO_IR = 20000.0;




    //calculadora de FIIs
    public List<Fiis> simularFundoImobiliario( Fiis calculadoraV, boolean historico) {
        FiisDAO dao = new FiisDAO();
        List<Fiis> resultados = dao.buscarFiis();
        if (resultados == null || resultados.isEmpty()) {
            throw new RuntimeException("Nenhum FII encontrado para simulação");
        }

        List<Fiis> fiisSimulados = new ArrayList<>();

        for (Fiis fii : resultados) {
            double saldoDividendos = 0;
            double dividendoPorCota = fii.getDividendYield();
            double valorAporte = calculadoraV.getAporte();
            double precoCota = fii.getPrecoFiis();
            double desvioCotas = fii.getDesvioCotas() / 100.0;
            double desvioDividendos = fii.getDesvioDividendos() / 100.0;

            int meses = calculadoraV.getMeses();
            int quantidadeCotas = calculadoraV.getQtdCotas();
            int reinvestir = calculadoraV.getReinvestir();

            for (int mes = 1; mes <= meses; mes++) {
                double dividendosRecebidos = quantidadeCotas * dividendoPorCota;
                saldoDividendos += dividendosRecebidos + valorAporte;
                if (reinvestir == REINVESTIR_SIM) {
                    int novasCotas = (int) (saldoDividendos / precoCota);
                    quantidadeCotas += novasCotas;
                    saldoDividendos -= novasCotas * precoCota;
                } if (reinvestir == REINVESTIR_NAO) {
                    int novasCotasAporte = (int) (saldoDividendos / precoCota);
                    quantidadeCotas += novasCotasAporte;
                    saldoDividendos -= novasCotasAporte * precoCota;
                }
            }


            Fiis fiiSimulado = new Fiis(
                    fii.getNome(),
                    fii.getPrecoFiis(),
                    fii.getDividendYield(),
                    fii.getDesvioCotas(),
                    fii.getDesvioDividendos()
            );

            fiiSimulado.setSaldoCotas((quantidadeCotas*precoCota));
            fiiSimulado.setSaldoDividendos(saldoDividendos);
            fiiSimulado.setId_fiis(fii.getId_fiis());
            fiisSimulados.add(fiiSimulado);
        }
        if (historico){
            int userId = UserSession.getLoggedInUserId();
            dao.salvarHistoricoFiis(fiisSimulados, userId, calculadoraV.getAporte(), calculadoraV.getMeses());
        }

        return fiisSimulados;
    }


    //calculadora de FIIs
    public List<Fiis> simularFundoImobiliarioPerfil( Fiis calculadoraV, boolean historico, int quantidade) {
        FiisDAO dao = new FiisDAO();
        List<Fiis> resultados = dao.buscarPorQuantidade(quantidade);
        if (resultados == null || resultados.isEmpty()) {
            throw new RuntimeException("Nenhum FII encontrado para simulação");
        }

        List<Fiis> fiisSimulados = new ArrayList<>();

        for (Fiis fii : resultados) {
            double saldoDividendos = 0;
            double dividendoPorCota = fii.getDividendYield();
            double valorAporte = calculadoraV.getAporte();
            double precoCota = fii.getPrecoFiis();
            double desvioCotas = fii.getDesvioCotas() / 100.0;
            double desvioDividendos = fii.getDesvioDividendos() / 100.0;

            int meses = calculadoraV.getMeses();
            int quantidadeCotas = calculadoraV.getQtdCotas();
            int reinvestir = calculadoraV.getReinvestir();

            for (int mes = 1; mes <= meses; mes++) {
                double dividendosRecebidos = quantidadeCotas * dividendoPorCota;
                saldoDividendos += dividendosRecebidos + valorAporte;
                if (reinvestir == REINVESTIR_SIM) {
                    int novasCotas = (int) (saldoDividendos / precoCota);
                    quantidadeCotas += novasCotas;
                    saldoDividendos -= novasCotas * precoCota;
                } if (reinvestir == REINVESTIR_NAO) {
                    int novasCotasAporte = (int) (saldoDividendos / precoCota);
                    quantidadeCotas += novasCotasAporte;
                    saldoDividendos -= novasCotasAporte * precoCota;
                }
            }


            Fiis fiiSimulado = new Fiis(
                    fii.getNome(),
                    fii.getPrecoFiis(),
                    fii.getDividendYield(),
                    fii.getDesvioCotas(),
                    fii.getDesvioDividendos()
            );

            fiiSimulado.setSaldoCotas((quantidadeCotas*precoCota));
            fiiSimulado.setSaldoDividendos(saldoDividendos);
            fiiSimulado.setId_fiis(fii.getId_fiis());
            fiisSimulados.add(fiiSimulado);
        }
        if (historico){
            int userId = UserSession.getLoggedInUserId();
            dao.salvarHistoricoFiis(fiisSimulados, userId, calculadoraV.getAporte(), calculadoraV.getMeses());
        }

        return fiisSimulados;
    }


    public List<Acoes> simularAcao(double capital,int prazo,boolean historico) {
        AcoesDao dao = new AcoesDao();
        List<Acoes> resultados = dao.buscarAcao();
        List<Acoes> acoesFeitas = new ArrayList<>();


        for(Acoes acaoSimulada : resultados){
            double precoCompra = acaoSimulada.getPrecoAcao();
            double txIr = acaoSimulada.getTxIr();
            double desvio =  acaoSimulada.getDesvio()/100.0;
            int quantidadeAcao = (int) (capital / precoCompra);
            double custoTotalCompra = precoCompra * quantidadeAcao;
            double variacao  = (Math.random() * 2 * desvio) - desvio;
            double valorAcaoVenda = precoCompra * (1+variacao);
            double valorTotalVenda = valorAcaoVenda * quantidadeAcao;
            double saldo = ( valorTotalVenda - custoTotalCompra);
            double troco = capital - custoTotalCompra;


            if (saldo > 20000){
                saldo = ((custoTotalCompra - (saldo*txIr))- saldo) ;


            } else {
                saldo = (valorTotalVenda - custoTotalCompra) ;
            }


            Acoes acoesFinal =  new Acoes(acaoSimulada.getNome());
            acoesFinal.setId_acao(acaoSimulada.getId_acao());
            acoesFinal.setQtdAcoes(quantidadeAcao);
            acoesFinal.setValorInvestido(capital);
            acoesFinal.setSaldoFinal(saldo);
            acoesFinal.setCustoTotalCompra(custoTotalCompra);
            acoesFinal.setValorTotalVenda(valorTotalVenda);
            acoesFinal.setTroco(troco);
            acoesFinal.setPrecoAcao(acaoSimulada.getPrecoAcao());
            acoesFeitas.add(acoesFinal);

        }

        if (historico){
            int userId = UserSession.getLoggedInUserId();
            dao.salvarHistoricoAcoes(acoesFeitas, userId, capital, prazo);
        }

        return acoesFeitas;
    }


    public List<Acoes> simularAcaoPerfil(double capital,int prazo,boolean historico, int quantidade) {
        AcoesDao dao = new AcoesDao();
        List<Acoes> resultados = dao.buscarPorQuantidade(quantidade);
        List<Acoes> acoesFeitas = new ArrayList<>();


        for(Acoes acaoSimulada : resultados){
            double precoCompra = acaoSimulada.getPrecoAcao();
            double txIr = acaoSimulada.getTxIr();
            double desvio =  acaoSimulada.getDesvio()/100.0;
            int quantidadeAcao = (int) (capital / precoCompra);
            double custoTotalCompra = precoCompra * quantidadeAcao;
            double variacao  = (Math.random() * 2 * desvio) - desvio;
            double valorAcaoVenda = precoCompra * (1+variacao);
            double valorTotalVenda = valorAcaoVenda * quantidadeAcao;
            double saldo = ( valorTotalVenda - custoTotalCompra);
            double troco = capital - custoTotalCompra;


            if (saldo > 20000){
                saldo = ((custoTotalCompra - (saldo*txIr))- saldo) ;


            } else {
                saldo = (valorTotalVenda - custoTotalCompra) ;
            }


            Acoes acoesFinal =  new Acoes(acaoSimulada.getNome());
            acoesFinal.setId_acao(acaoSimulada.getId_acao());
            acoesFinal.setQtdAcoes(quantidadeAcao);
            acoesFinal.setValorInvestido(capital);
            acoesFinal.setSaldoFinal(saldo);
            acoesFinal.setCustoTotalCompra(custoTotalCompra);
            acoesFinal.setValorTotalVenda(valorTotalVenda);
            acoesFinal.setTroco(troco);
            acoesFinal.setPrecoAcao(acaoSimulada.getPrecoAcao());
            acoesFeitas.add(acoesFinal);

        }

        if (historico){
            int userId = UserSession.getLoggedInUserId();
            dao.salvarHistoricoAcoes(acoesFeitas, userId, capital, prazo);
        }

        return acoesFeitas;
    }



}
