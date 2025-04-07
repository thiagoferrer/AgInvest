package com.example.aginvest.dao;

import com.example.aginvest.model.produtos.Fiis;
import com.example.aginvest.util.ConnectionFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class FiisDAO {


    public List<Fiis> buscarFiis() {
        List<Fiis> fiisAll = new ArrayList<>();
        String sql = "SELECT * " +
                " FROM fiis";


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {


            while (rs.next()) {
                Fiis fii = new Fiis(
                        rs.getString("nome_prod"),
                        rs.getInt("id_fiis"),
                        rs.getDouble("preco_fiis"),
                        rs.getDouble("dividend_yeld"),
                        rs.getDouble("desvio_cotas"),
                        rs.getDouble("desvio_dividendos")
                );
                fiisAll.add(fii);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar FIIs: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar FIIs no banco de dados", e);
        }
        return fiisAll;
    }


    public void salvarHistoricoFiis(List<Fiis> fiisSimuladas, int id_user, double aporte, int prazo){
        String sql = "INSERT INTO fiis_hist(data, id_user,id_fiis, aporte, prazo, preco_fiis, qts_cotas, saldo_cotas, saldo_div)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?); ";


        LocalDate data = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (Fiis fiis : fiisSimuladas) {
                stmt.setString(1, data.format(formatter));
                stmt.setInt(2, id_user);
                stmt.setInt(3, fiis.getId_fiis());
                stmt.setDouble(4, aporte);
                stmt.setInt(5, prazo);
                stmt.setDouble(6, fiis.getPrecoFiis());
                stmt.setInt(7,fiis.getQtdCotas());
                stmt.setDouble(8, fiis.getSaldoCotas());
                stmt.setDouble(9, fiis.getSaldoDividendos());


                stmt.executeUpdate();
            }




        } catch (SQLException e) {
            System.err.println("Erro SQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Fiis> buscarPorQuantidade(int quantidade) {
        List<Fiis> fiisAll = new ArrayList<>();
        String sql = "SELECT * FROM fiis ORDER BY RAND() LIMIT ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantidade);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Fiis fii = new Fiis(
                            rs.getString("nome_prod"),
                            rs.getInt("id_fiis"),
                            rs.getDouble("preco_fiis"),
                            rs.getDouble("dividend_yeld"),
                            rs.getDouble("desvio_cotas"),
                            rs.getDouble("desvio_dividendos")
                    );
                    fiisAll.add(fii);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar FIIs: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar FIIs no banco de dados", e);
        }
        return fiisAll;
    }
}
