package com.example.aginvest.dao;

import com.example.aginvest.model.produtos.Acoes;
import com.example.aginvest.util.ConnectionFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;




public class AcoesDao {
    public List<Acoes> buscarAcao() {
        List<Acoes> acoesAll = new ArrayList<>();




        String sql = "SELECT * FROM acoes";




        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {








            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Acoes acoes = new Acoes(
                            rs.getString("nome_prod"),
                            rs.getInt("id_acao"),
                            rs.getDouble("tx_ir"),
                            rs.getDouble("preco_acao"),
                            rs.getDouble("desvio")
                    );
                    acoesAll.add(acoes);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao buscar ação '" + e.getMessage());
        }
        return acoesAll;
    }




    public void salvarHistoricoAcoes(List<Acoes> acoesSimuladas, int id_user, double capital, int prazo){
        String sql = "INSERT INTO acoes_hist(data, id_user,id_acao,  capital, prazo, preco_acao, qtd_acoes, custo_total, saldo_acoes)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?); ";


        LocalDate data = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (Acoes acoes : acoesSimuladas) {
                stmt.setString(1, data.format(formatter));
                stmt.setInt(2, id_user);
                stmt.setInt(3, acoes.getId_acao());
                stmt.setDouble(4, capital);
                stmt.setInt(5, prazo);
                stmt.setDouble(6, acoes.getPrecoAcao());
                stmt.setInt(7,acoes.getQtdAcoes());
                stmt.setDouble(8, acoes.getCustoTotal());
                stmt.setDouble(9, acoes.getSaldoFinal());


                stmt.executeUpdate();
            }




        } catch (SQLException e) {
            System.err.println("Erro SQL: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public List<Acoes> buscarPorQuantidade(int quantidade) {
        List<Acoes> acoesAll = new ArrayList<>();
        String sql = "SELECT * FROM acoes ORDER BY RAND() LIMIT ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantidade);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Acoes acoes = new Acoes(
                            rs.getString("nome_prod"),
                            rs.getInt("id_acao"),
                            rs.getDouble("tx_ir"),
                            rs.getDouble("preco_acao"),
                            rs.getDouble("desvio")
                    );
                    acoesAll.add(acoes);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar FIIs: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar FIIs no banco de dados", e);
        }
        return acoesAll;
    }
}
