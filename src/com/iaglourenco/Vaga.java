package com.iaglourenco;

public class Vaga {

    private Automovel veiculo;
    private String horaEntrada;


    Vaga(){
        veiculo=null;

    }
    Vaga(Automovel veiculo,String hora){
        this.veiculo=veiculo;
        this.horaEntrada=hora;
    }


    public String getHoraEntrada() {
        return horaEntrada;
    }

    int getTipo() {
        return veiculo.getTipo();
    }

    Automovel getVeiculo() {
        return veiculo;
    }
}
