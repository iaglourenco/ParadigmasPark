package com.iaglourenco;

class Automovel {
    static final int CAMINHONETE=0,CARRO=1,MOTO=2;


    private final String placa;
    private final int tipo;

    Automovel(String placa, int tipo){
       this.placa=placa;
       this.tipo=tipo;
    }

    String getPlaca(){return this.placa;}
    int getTipo(){return this.tipo;}


}
