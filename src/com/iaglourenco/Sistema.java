package com.iaglourenco;

import com.iaglourenco.exceptions.PlacaInexistenteException;
import com.iaglourenco.exceptions.ReadFileException;
import com.iaglourenco.exceptions.VagaOcupadaException;
import com.iaglourenco.exceptions.WriteFileException;

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


    void registraEntrada(Automovel veiculo,String hora) throws WriteFileException, VagaOcupadaException {

        String vagaOcupada =estacionamento.entra(new Vaga(veiculo,hora));
        if(vagaOcupada == null){
            throw new VagaOcupadaException();
        }
        String day = new SimpleDateFormat("dd/MM/yy").format(new Date(System.currentTimeMillis()));

        String info = day+","+ hora+ "," +veiculo.getPlaca()+","+ vagaOcupada+","+0+'\n';


        try {
            writer = new BufferedWriter(new FileWriter(csvHistorico,true));
            writer.append(info);
            writer.flush();
            updateVagasFile();
        }catch (IOException e){
            throw new WriteFileException();
        }

    }

    double registraSaida(Automovel veiculo,String hora) throws WriteFileException,ReadFileException,PlacaInexistenteException{

        double pagamento;
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
                String day = new SimpleDateFormat("dd/MM/yy").format(new Date(System.currentTimeMillis()));
                String info = day +","+ hora +","+ veiculo.getPlaca() +","+ estacionamento.sai(veiculo)+","+pagamento+'\n';
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

        return pagamento;
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
