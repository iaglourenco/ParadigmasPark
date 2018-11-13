package com.iaglourenco;

public class Automovel {
    public static final int CAMINHONETE=0,CARRO=1,MOTO=2;


    private String marca;
    private String modelo;
    private String placa;
    private int tipo;

    public Automovel(String marca,String modelo,String placa,int tipo){
       this.marca=marca;
       this.modelo=modelo;
       this.placa=placa;
       this.tipo=tipo;

    }

    String getMarca(){return this.marca;}
    String getModelo(){return this.modelo;}
    String getPlaca(){return this.placa;}
    int getTipo(){return this.tipo;}


}
