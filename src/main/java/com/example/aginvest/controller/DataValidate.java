
package com.example.aginvest.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataValidate {
    public String validarData(String dataString) {
        if (dataString == null || dataString.trim().isEmpty()) {
            System.out.println("Data não pode ser nula.");
            return null;
        }

        SimpleDateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        userDateFormat.setLenient(false); // Isso faz com que a validação seja rigorosa

        SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        try {

            Date data = userDateFormat.parse(dataString);

            return dbDateFormat.format(data);
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Use o formato dd/MM/yyyy.");
            return null;
        }
    }
}