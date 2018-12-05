package com.iaglourenco;

import java.util.Objects;

class Vaga {

    private final Automovel veiculo;
    private String data;
    private String vagaID;


    Vaga(String vagaID){
        veiculo=null;
        this.vagaID = vagaID;
    }
    Vaga(Automovel veiculo,String data){
        this.veiculo=veiculo;
        this.data=data;
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

    String getData() {
        return data;
    }
}
