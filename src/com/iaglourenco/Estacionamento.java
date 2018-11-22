package com.iaglourenco;

import java.util.ArrayList;

class Estacionamento {

    ArrayList<Vaga> piso1 = new ArrayList<>();
    ArrayList<Vaga> terreo = new ArrayList<>();
    private static Estacionamento instance;







    public static synchronized Estacionamento getInstance(){
        if(instance == null){
            instance = new Estacionamento();
        }
        return instance;
    }

}
