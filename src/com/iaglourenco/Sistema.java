package com.iaglourenco;

import com.iaglourenco.exceptions.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class Sistema {

    private Estacionamento estacionamento = Estacionamento.getInstance();
    private static Sistema system=null;
    private double precoCaminhonete;
    private double precoCarro;
    private double precoMoto;

    private String csvVagas = "vagas.csv";
    private String csvHistorico = "history.csv";

    private BufferedReader reader;
    private BufferedWriter writer;

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
    */

    /*
    * Arquivos para salvamento
    *
    *  Arquivo de vagas = vagas.csv
    *   Guarda todas as vagas ocupadas e vagas livres
    *
    *   Arquivo de contabilidade = history.csv
    *       guarda todas as entradas e saidas efetuadas
    *
    */

    void setup(){

        try{
            reader = new BufferedReader(new FileReader(csvVagas));
        }catch (FileNotFoundException e){

            try{
                writer = new BufferedWriter(new FileWriter(csvVagas));
            }catch (IOException ev ){
                ev.printStackTrace();
            }
        }
        try{
            reader = new BufferedReader(new FileReader(csvHistorico));
        }catch (FileNotFoundException e){

            try{
                writer = new BufferedWriter(new FileWriter(csvHistorico));
            }catch (IOException ev ){
                ev.printStackTrace();
            }
        }

    }

    String registraEntrada(Automovel veiculo,String time) throws WriteFileException, VagaOcupadaException, EstacionamentoCheioException {


        String[] data = time.split("&");

        String vagaOcupada = estacionamento.entra(new Vaga(veiculo, data[1]));
        if(vagaOcupada == null){
            throw new VagaOcupadaException();
        }

        String info = data[0]+","+ data[1]+ "," +veiculo.getPlaca()+","+ vagaOcupada+","+0+'\n';
        try {
            writer = new BufferedWriter(new FileWriter(csvHistorico,true));
            writer.append(info);
            writer.flush();
            updateVagasFile();
            return vagaOcupada;

        }catch (IOException e) {
            throw new WriteFileException();
        }

    }

    String registraSaida(Automovel veiculo,String time) throws WriteFileException,ReadFileException,PlacaInexistenteException{


        String[] data = time.split("&");

        String hora = data[1];
        String dia = data[0];

        double pagamento;
        String id;
        try{
                   reader = new BufferedReader(new FileReader(csvHistorico));

                   String[] file = reader.readLine().split(",");

                   while(!file[2].equals(veiculo.getPlaca())){
                       file = reader.readLine().split(",");
                   }
                   String[] hEntr = file[1].split(":");
                   double entrada = Double.parseDouble(hEntr[0]) + (Double.parseDouble(hEntr[1]) / 100);
                   String[] hSai = hora.split(":");
                   double saida = Double.parseDouble(hSai[0]) + (Double.parseDouble(hSai[1])/100);



                   pagamento = Math.abs(entrada-saida);
                   switch (veiculo.getTipo()) {
                case Automovel.CAMINHONETE:
                    pagamento *= precoCaminhonete;
                    break;
                case Automovel.CARRO:
                    pagamento *= precoCarro;
                    break;
                case Automovel.MOTO:
                    pagamento *= precoMoto;
                    break;
            }

            try {
                id = estacionamento.sai(veiculo);
                String day = new SimpleDateFormat("dd/MM/yy").format(new Date(System.currentTimeMillis()));
                String info = day +","+ hora +","+ veiculo.getPlaca() +","+ id+","+pagamento+'\n';
                writer = new BufferedWriter(new FileWriter(csvHistorico,true));
                writer.append(info);
                writer.flush();
                updateVagasFile();
            }catch (IOException excp){
                throw new WriteFileException();
            }


        }catch (IOException e){
            throw new ReadFileException();
        }catch (NullPointerException ex) {
            throw new PlacaInexistenteException();
        }


        return pagamento + ";" + id;

    }

    void updateVagasFile() throws WriteFileException {

        String info;

        try {
            writer = new BufferedWriter(new FileWriter(csvVagas,false));
        } catch (IOException e) {
            throw new WriteFileException();
        }


        for (Vaga v : estacionamento.getPiso1()){

            try {
                writer = new BufferedWriter(new FileWriter(csvVagas,true));
                if(v.getVeiculo() == null){
                     info = v.getVagaID()+","+null+","+null+'\n';
                }else {
                     info = v.getVagaID() + "," + v.getVeiculo().getPlaca() + "," + v.getTipoVeiculo() + '\n';
                }
                writer.append(info);
                writer.flush();

            }catch (IOException e){
                throw new WriteFileException();
            }

        }
        for (Vaga v : estacionamento.getTerreoCarro()){

            try {
                writer = new BufferedWriter(new FileWriter(csvVagas,true));
                if(v.getVeiculo() == null){

                    info = v.getVagaID()+","+null+","+null+'\n';
                }else {
                    info = v.getVagaID() + "," + v.getVeiculo().getPlaca() + "," + v.getTipoVeiculo() + '\n';
                }
                writer.append(info);
                writer.flush();


            }catch (IOException e){
                throw new WriteFileException();
            }

        }
        for (Vaga v : estacionamento.getTerreoMoto()){

            try {
                writer = new BufferedWriter(new FileWriter(csvVagas,true));
                if(v.getVeiculo() == null){

                    info = v.getVagaID()+","+null+","+null+'\n';
                }else {
                    info = v.getVagaID() + "," + v.getVeiculo().getPlaca() + "," + v.getTipoVeiculo() + '\n';
                }
                writer.append(info);
                writer.flush();


            }catch (IOException e){
                throw new WriteFileException();
            }

        }

        for (Vaga v : estacionamento.getTerreoCaminhonete()){

            try {
                writer = new BufferedWriter(new FileWriter(csvVagas,true));
                if(v.getVeiculo() == null){

                    info = v.getVagaID()+","+null+","+null+'\n';
                }else {
                    info = v.getVagaID() + "," + v.getVeiculo().getPlaca() + "," + v.getTipoVeiculo() + '\n';
                }
                writer.append(info);
                writer.flush();

            }catch (IOException e){
                throw new WriteFileException();
            }

        }




    }

    synchronized static Sistema getInstance(){
        if(system == null){
            system = new Sistema();
        }
        return system;
    }

    String contabilize(String range) throws ReadFileException {

        String[] datas = range.split(";");
        String inicio = datas[0];
        String fim = datas[1];


        double lucro;
        int saidas;

        try{
            reader = new BufferedReader(new FileReader(csvHistorico));



        }catch (IOException e ){
            throw new ReadFileException();
        }

        return null;
    }

    int sizePiso1(){return estacionamento.sizeP1();}

    int sizeTerreoCarros(){return estacionamento.sizeTCarros();}

    int sizeTerreoCaminhonetes(){return estacionamento.sizeTCaminhonete();}

    int sizeTerreoMotos(){return estacionamento.sizeTMoto();}

    double getPrecoCaminhonete() {
        return precoCaminhonete;
    }

    void setPrecoCaminhonete(double precoCaminhonete) {
        this.precoCaminhonete = precoCaminhonete;
    }

    double getPrecoCarro() {
        return precoCarro;
    }

    void setPrecoCarro(double precoCarro) {

        this.precoCarro = precoCarro;

    }

    double getPrecoMoto() {
        return precoMoto;
    }

    void setPrecoMoto(double precoMoto) {

        this.precoMoto = precoMoto;
    }
}
