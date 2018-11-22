package com.iaglourenco;

public class Vaga {

    private Automovel veiculo;
    private int tipo;
    private String horaEntrada;


    public Vaga(Automovel veiculo,int tipo,String hora){
        this.veiculo=veiculo;
        this.tipo=tipo;
        this.horaEntrada=hora;
    }


    public String getHoraEntrada() {
        return horaEntrada;
    }

    public int getTipo() {
        return tipo;
    }

    public Automovel getVeiculo() {
        return veiculo;
    }
}
