package com.iaglourenco;

public class Vaga {

    private Automovel veiculo;
    private String horaEntrada;
    private String vagaID;


    Vaga(){
        veiculo =null;
    }

    Vaga(String vagaID){
        veiculo=null;
        this.vagaID = vagaID;
    }
    Vaga(Automovel veiculo,String hora){
        this.veiculo=veiculo;
        this.horaEntrada=hora;
    }


    public String getHoraEntrada() {
        return horaEntrada;
    }

    int getTipoVeiculo() {
        return veiculo.getTipo();
    }

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
