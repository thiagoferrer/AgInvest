package com.example.aginvest.controller;

import java.text.DecimalFormat;

public class CalculadoraRFprovisoria {
    static DecimalFormat df = new DecimalFormat("#,##0.00");


    public double calculoImpRenda(int meses) {
        double aliqIR;
        if (meses <= 6) aliqIR = 0.225;
        else if (meses <= 12) aliqIR = 0.20;
        else if (meses <= 25) aliqIR = 0.175;
        else aliqIR = 0.15;
        return aliqIR;
    }

    //calculadora RF provisória
    public double calculoRendBruto(double valorInicial, double aporteM, double tx, int meses) {
        return valorInicial * Math.pow(1 + tx, meses) + aporteM * ((Math.pow(1 + tx, meses) - 1) / tx);
    }

    public double calculoRendLiq(double rendBrutoRF, double valorInicial, double iR) {
        return rendBrutoRF - ((rendBrutoRF - valorInicial) * iR);
    }

}


