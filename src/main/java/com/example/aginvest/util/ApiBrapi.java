package com.example.aginvest.util;

import com.example.aginvest.model.ValoresAcoes;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.List;

public class ApiBrapi {
    private static final String API_TOKEN = "gRCqKmdqmfWauhKdrcUZdB";
 public static ValoresAcoes BuscarValor(String nomeAcao) throws Exception {

     String url = "https://brapi.dev/api/quote/" + nomeAcao + "?token=" + API_TOKEN;

        // Criando o cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Criando a requisição
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Enviando a requisição e capturando a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        Map<String, Object> jsonMap = new Gson().fromJson(response.body(), Map.class);
        List<Map<String, Object>> results = (List<Map<String, Object>>) jsonMap.get("results");

        if (results == null || results.isEmpty()) {
            throw new Exception("Nenhum resultado encontrado para a ação: " + nomeAcao);
        }

        Map<String, Object> acaoData = results.get(0);
        ValoresAcoes valores = new ValoresAcoes();

        // Fazendo o cast de forma segura para cada campo
        valores.setRegularMarketPrice(getDoubleSafe(acaoData, "regularMarketPrice"));
        valores.setRegularMarketDayHigh(getDoubleSafe(acaoData, "regularMarketDayHigh"));
        valores.setSymbol(getStringSafe(acaoData, "symbol"));
        valores.setRegularMarketChange(getDoubleSafe(acaoData, "regularMarketChange"));
        // Convertendo JSON para objeto Java
        return valores;
    }

    // Método auxiliar para converter para double com segurança
    private static double getDoubleSafe(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return 0.0; // ou lance uma exceção se preferir
    }

    private static String getStringSafe(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return (value != null) ? value.toString() : "";
    }



}
