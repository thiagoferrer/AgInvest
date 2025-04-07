package com.example.aginvest.controller;


import com.example.aginvest.model.Endereco;
import com.example.aginvest.util.ViaCep;

public class BuscarCep {
    public String buscarApi(String cep){
        try {
            Endereco endereco = ViaCep.buscarCep(cep);

            // Construindo a string formatada
            StringBuilder resultado = new StringBuilder();
            resultado.append(endereco.getLogradouro())
                    .append(", Bairro: ").append(endereco.getBairro())
                    .append(", Cidade: ").append(endereco.getLocalidade());

            System.out.println("resultado api: " + resultado);
            return resultado.toString();
        } catch (Exception e) {
            return "Erro ao buscar o CEP: " + e.getMessage();
        }
    }
}
