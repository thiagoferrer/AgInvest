package com.example.aginvest.util;

import com.example.aginvest.model.Endereco;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ViaCep {
    public static Endereco buscarCep(String cep) throws Exception {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        // Criando o cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Criando a requisição
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Enviando a requisição e capturando a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Resposta da API: " + response.body());


        if (response.statusCode() == 200) {
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

            Endereco endereco = new Endereco();
            endereco.setCep(jsonResponse.get("cep").getAsString());
            endereco.setLogradouro(jsonResponse.get("logradouro").getAsString());
            endereco.setComplemento(jsonResponse.has("complemento") ? jsonResponse.get("complemento").getAsString() : null);
            endereco.setBairro(jsonResponse.get("bairro").getAsString());
            endereco.setLocalidade(jsonResponse.get("localidade").getAsString());
            endereco.setUf(jsonResponse.get("uf").getAsString());
            endereco.setIbge(jsonResponse.get("ibge").getAsString());
            endereco.setGia(jsonResponse.get("gia").getAsString());
            endereco.setDdd(jsonResponse.get("ddd").getAsString());
            endereco.setSiafi(jsonResponse.get("siafi").getAsString());

            return endereco;
        } else {
            throw new Exception("Erro ao buscar CEP. Status code: " + response.statusCode());
        }
    }
}
