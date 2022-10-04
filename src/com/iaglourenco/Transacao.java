package com.iaglourenco;


class Transacao {


    private final String dia;
    private final String hora;
    private final String placa;
    private final String vagaID;
    private final int tipoVeiculo;
    private final double valor;

    Transacao(String dia, String hora, String placa, String vagaID, double valor, int tipoVeiculo){
        this.dia=dia;
        this.hora=hora;
        this.placa = placa;
        this.vagaID=vagaID;
        this.valor=valor;
        this.tipoVeiculo = tipoVeiculo;
    }


    public String getDia() {
        return dia;
    }

    public String getHora() {
        return hora;
    }

    public String getVagaID() {
        return vagaID;
    }

    public double getValor() {
        return valor;
    }

    public int getTipoVeiculo() {
        return tipoVeiculo;
    }

    public String getPlaca() {
        return placa;
    }
}
