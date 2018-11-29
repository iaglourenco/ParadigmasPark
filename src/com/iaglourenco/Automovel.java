package com.iaglourenco;

public class Automovel {
    static final int CAMINHONETE=0,CARRO=1,MOTO=2;


    private String placa;
    private int tipo;

    public Automovel(String placa,int tipo){
       this.placa=placa;
       this.tipo=tipo;
    }

    String getPlaca(){return this.placa;}
    int getTipo(){return this.tipo;}


}
