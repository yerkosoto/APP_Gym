package com.example.gimnasio;

public class Consultas {

    public Double calculaImc(String peso, String altura) {
        Double peso_double = Double.parseDouble(peso);
        Double altura_double = Double.valueOf(altura)/100;
        Double imc = peso_double / (altura_double * altura_double);
        return imc;
    }


    


}
