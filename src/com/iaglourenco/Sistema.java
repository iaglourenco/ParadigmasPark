package com.iaglourenco;

public class Sistema {

    private Estacionamento estacionamento = Estacionamento.getInstance();
    private static Sistema system=null;
    private double precoCaminhonete;
    private double precoCarro;
    private double precoMoto;

    /*
    * Regra estacionamento
    *
    * - tamanho piso 1 e terreo = 10X10
    *
    * - Carros ficam em qualquer vaga do piso 1
    *   e em uma submatriz de 6X10 do terreo
    *
    * - motos e caminhonete ficam cada uma em submatrizes de 2X10
    *
    * */

    boolean registraEntrada(Automovel veiculo,String hora){


        //todo salvar a transacao no arquivo e atualizar o arquivo de vagas


        return estacionamento.entra(new Vaga(veiculo,hora));

    }

    public boolean registraSaida(Automovel veiculo,String hora) {

        //todo salvar a transacao no arquivo e atualizar o arquivo de vagas


        return estacionamento.sai(veiculo);

    }

    synchronized static Sistema getInstance(){
        if(system == null){
            system = new Sistema();
        }
        return system;
    }


    public double getPrecoCaminhonete() {
        return precoCaminhonete;
    }

    public void setPrecoCaminhonete(double precoCaminhonete) {
        this.precoCaminhonete = precoCaminhonete;
    }

    public double getPrecoCarro() {
        return precoCarro;
    }

    public void setPrecoCarro(double precoCarro) {
        this.precoCarro = precoCarro;
    }

    public double getPrecoMoto() {
        return precoMoto;
    }

    public void setPrecoMoto(double precoMoto) {
        this.precoMoto = precoMoto;
    }
}
