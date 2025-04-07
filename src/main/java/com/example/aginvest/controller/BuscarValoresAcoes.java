package com.example.aginvest.controller;

import com.example.aginvest.model.ValoresAcoes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.example.aginvest.util.ApiBrapi;

public class BuscarValoresAcoes {
    public List<ValoresAcoes> buscarAcoes() {
        List<ValoresAcoes> acoes = new ArrayList<>();
        List<String> acoesDesejadas = Arrays.asList("PETR4", "AMZN", "VIVA3", "VALE3", "SBSP3", "TSLA");

        for (String nomeAcao : acoesDesejadas) {
            try {
                // Busca o valor da ação individual
                ValoresAcoes acao = ApiBrapi.BuscarValor(nomeAcao);
                // Adiciona à lista de resultados
                acoes.add(acao);

                // Pequena pausa entre requisições para evitar ser bloqueado
                Thread.sleep(500);
            } catch (Exception e) {
                System.err.println("Erro ao buscar ação " + nomeAcao + ": " + e.getMessage());
                // Você pode optar por adicionar um objeto com valores padrão ou continuar
            }
        }

        return acoes;
    }
}
