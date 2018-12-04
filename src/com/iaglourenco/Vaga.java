package com.iaglourenco;

import java.util.Objects;

class Vaga {

    private final Automovel veiculo;
    private String horaEntrada;
    private String vagaID;


    Vaga(String vagaID){
        veiculo=null;
        this.vagaID = vagaID;
    }
    Vaga(Automovel veiculo,String hora){
        this.veiculo=veiculo;
        this.horaEntrada=hora;
    }


    int getTipoVeiculo() { return Objects.requireNonNull(veiculo).getTipo(); }

    Automovel getVeiculo() {
        return veiculo;
    }

    String getVagaID() {
        return vagaID;
    }
    void setVagaID(String vagaID){
        this.vagaID = vagaID;
    }
}
