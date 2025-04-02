
package com.example.aginvest.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataValidate {
    public String validarData(String dataString) {
        if (dataString == null || dataString.equals("")) {
            System.out.println("Data não pode ser nula.");
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date data = null;
        String dt_str = null;
        try {
            data = dateFormat.parse(dataString);
            dt_str = dateFormat.format(data);
        } catch (ParseException e) {
            // Caso a data não esteja no formato correto, exibe uma mensagem de erro
            System.out.println("Formato de data inválido. Use o formato yyyy/MM/dd.");
        }

        return dt_str;
    }
}