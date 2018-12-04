package com.iaglourenco;

import com.iaglourenco.exceptions.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

class Sistema {

    private final Estacionamento estacionamento = Estacionamento.getInstance();
    private static Sistema system=null;
    private double precoCaminhonete;
    private double precoCarro;
    private double precoMoto;

    private final String csvVagas = "vagas.csv";
    private final String csvHistorico = "history.csv";

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

        double pagamento;
        String id;
        try{
                   reader = new BufferedReader(new FileReader(csvHistorico));

                   String[] file = reader.readLine().split(",");

                   while(!file[2].equals(veiculo.getPlaca())){
                       file = reader.readLine().split(",");
                   }
                    Date dEntra;
                    Date dSai;
                    long difHoras=0;
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    try{
                        dEntra = format.parse(file[0]+" "+file[1]);
                        dSai = format.parse(data[0]+" "+data[1]);
                        long dif = dSai.getTime() - dEntra.getTime();
                        difHoras = dif / (60 * 60 * 1000);

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                   pagamento = Math.abs(difHoras);
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
                if(id == null){
                    throw new PlacaInexistenteException();
                }
                String info = data[0] +","+ data[1] +","+ veiculo.getPlaca() +","+ id+","+pagamento+'\n';
                writer = new BufferedWriter(new FileWriter(csvHistorico,true));
                writer.append(info);
                writer.flush();
                updateVagasFile();
            }catch (IOException excp){
                throw new WriteFileException();
            }


        }catch (IOException e){
            throw new ReadFileException();
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


        //todo analizar todas as transaçoes baseado na data passada

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
