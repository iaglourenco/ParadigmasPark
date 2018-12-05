package com.iaglourenco;

import com.iaglourenco.exceptions.*;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class Sistema {

    private final Estacionamento estacionamento = Estacionamento.getInstance();
    private static Sistema system=null;
    private double precoCaminhonete;
    private double precoCarro;
    private double precoMoto;

    private ArrayList<Transacao> historico = new ArrayList<>();

    private final String csvVagas = "vagas.csv";
    private final String csvHistorico = "history.csv";

    private BufferedReader reader;
    private BufferedWriter writer;
    boolean priceSeted = false;


    ArrayList<String> idsOcupados(){

        ArrayList<String> ret = new ArrayList<>();


        for(Vaga v : estacionamento.getPiso1()){

            if (v.getVeiculo() != null) {
                ret.add(v.getVagaID());
            }
        }

        for(Vaga v : estacionamento.getTerreoCaminhonete()){

            if (v.getVeiculo() != null) {
                ret.add(v.getVagaID());
            }
        }
        for(Vaga v : estacionamento.getTerreoCarro()){

            if (v.getVeiculo() != null) {
                ret.add(v.getVagaID());
            }
        }
        for(Vaga v : estacionamento.getTerreoMoto()){

            if (v.getVeiculo() != null) {
                ret.add(v.getVagaID());
            }
        }




        return ret;
    }

    Automovel findVeiculoById(String id){


        if(Integer.parseInt(id) >= 1 && Integer.parseInt(id) <= 100) {
            for (Vaga v : estacionamento.getPiso1()) {

                if (v.getVagaID().equals(id)) {
                    return v.getVeiculo();
                }
            }
        }
        if(Integer.parseInt(id) >= 101 && Integer.parseInt(id) <= 160) {
            for(Vaga v : estacionamento.getTerreoCarro()){
                if (v.getVagaID().equals(id)) {
                    return v.getVeiculo();
                }
            }
        }

        if(Integer.parseInt(id) >= 181 && Integer.parseInt(id) <= 200) {

            for (Vaga v : estacionamento.getTerreoCaminhonete()) {

                if (v.getVagaID().equals(id)) {
                    return v.getVeiculo();
                }
            }
        }
        if(Integer.parseInt(id) >= 161 && Integer.parseInt(id) <= 180) {

            for (Vaga v : estacionamento.getTerreoMoto()) {

                if (v.getVagaID().equals(id)) {
                    return v.getVeiculo();
                }
            }
        }

        return null;
    }

    void reset() throws WriteFileException, ReadFileException {
        try {
            writer = new BufferedWriter(new FileWriter(csvVagas,false));
            writer = new BufferedWriter(new FileWriter(csvHistorico,false));
            setup();
            estacionamento.populateEmpty();
            historico.clear();
        }catch (IOException e){
            throw new WriteFileException();
        }
    }

    void setup() throws ReadFileException, WriteFileException {
        String[] file;

        try{
            reader = new BufferedReader(new FileReader(csvVagas));

            try {
                file = reader.readLine().split(",");
            }catch (NullPointerException e){
                    //arquivo vazio
                throw new FileNotFoundException();
            }


            // arquivo tem dados, restaura do arquivo
            while(true) {
                //coloca no array linha a linha do arquivo
                if(!file[1].equals("null") && !file[0].equals("#")){
                    //posicao do veiculo eh diferente de "null"
                    estacionamento.put(file[0], new Vaga(new Automovel(file[1], Integer.parseInt(file[2])), file[3]));
                }
                if(file[0].equals("#")){
                    //        String price = "#"+","+getPrecoCarro()+","+getPrecoCaminhonete()+","+getPrecoMoto()+'\n';

                    setPrecoCarro(Double.parseDouble(file[1]));
                    setPrecoCaminhonete(Double.parseDouble(file[2]));
                    setPrecoMoto(Double.parseDouble(file[3]));
                    priceSeted = true;
                }
                try {
                    file = reader.readLine().split(",");
                } catch (NullPointerException e) {
                    //fim do arquivo
                    break;
                }
            }


        }catch (FileNotFoundException e){
            //arquivo nao encontrado criando novo
            try{
                writer = new BufferedWriter(new FileWriter(csvVagas,false));
            }catch (IOException ev ){
                //erro ao criar novo arquivo
                throw new WriteFileException();
            }
        }catch (IOException e){
            //erro ao ler o arquivo
            throw new ReadFileException();
        }


        String[] hFile;
        try{
            reader = new BufferedReader(new FileReader(csvHistorico));
            try {
                hFile = reader.readLine().split(",");
                if (hFile[0].isEmpty()){
                    throw new NullPointerException();
                }
            }catch (NullPointerException e){
                //arquivo vazio
                throw new FileNotFoundException();
            }
            while(true){
                try {
                    historico.add(new Transacao(hFile[0],hFile[1],hFile[2],hFile[3],Double.parseDouble(hFile[4]),Integer.parseInt(hFile[5])));
                    hFile = reader.readLine().split(",");
                }catch (NullPointerException e){
                    break;
                }
            }




        }catch (FileNotFoundException e){
            //arquivo nao encontrado criando novo
            try{
                writer = new BufferedWriter(new FileWriter(csvHistorico));

            }catch (IOException ev ){
                //erro ao criar novo arquivo
                throw new WriteFileException();
            }
        }catch (IOException e1){
            //erro ao ler o arquivo
            throw new ReadFileException();
        }

    }

    String registraEntrada(Automovel veiculo,String time) throws WriteFileException, VagaOcupadaException, EstacionamentoCheioException {


        String[] data = time.split("&");

        String vagaOcupadaID = estacionamento.entra(new Vaga(veiculo, time));
        if(vagaOcupadaID == null){
            throw new VagaOcupadaException();
        }

        String info = data[0]+","+ data[1]+ "," +veiculo.getPlaca()+","+ vagaOcupadaID+","+0+","+veiculo.getTipo()+'\n';
        historico.add(new Transacao(data[0],data[1],veiculo.getPlaca(),vagaOcupadaID,0,veiculo.getTipo()));
        try {
            writer = new BufferedWriter(new FileWriter(csvHistorico,true));
            writer.append(info);
            writer.flush();
            updateVagasFile();
            return vagaOcupadaID;

        }catch (IOException e) {
            throw new WriteFileException();
        }

    }

    String registraSaida(Automovel veiculo,String time) throws WriteFileException,ReadFileException,PlacaInexistenteException{

        String[] data = time.split("&");
        String[] file;
        double pagamento;
        String id;

        try{
            reader = new BufferedReader(new FileReader(csvHistorico));

            try{
                file = reader.readLine().split(",");

            }catch (NullPointerException e){
                throw new PlacaInexistenteException();
            }
            while(!file[2].equals(veiculo.getPlaca())){
                try{
                    file = reader.readLine().split(",");

                }catch (NullPointerException e){
                    throw new PlacaInexistenteException();
                }
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
            switch (Integer.parseInt(file[5])) {
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
                String info = data[0] +","+ data[1] +","+ veiculo.getPlaca() +","+ id+","+pagamento+","+-1+'\n';
                historico.add(new Transacao(data[0],data[1],veiculo.getPlaca(),id,pagamento,-1));
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


        return pagamento + ";" + id+";"+file[5];

    }

    void updateVagasFile() throws WriteFileException {

        String info;
        String price = "#"+","+getPrecoCarro()+","+getPrecoCaminhonete()+","+getPrecoMoto()+'\n';


        try {
            writer = new BufferedWriter(new FileWriter(csvVagas,false));
        } catch (IOException e) {
            throw new WriteFileException();
        }


        try {
            writer = new BufferedWriter(new FileWriter(csvVagas,true));
            writer.append(price);
            writer.flush();
        }catch (IOException e){
            throw new WriteFileException();
        }

        for (Vaga v : estacionamento.getPiso1()){

            try {
                writer = new BufferedWriter(new FileWriter(csvVagas,true));
                if(v.getVeiculo() == null){
                     info = v.getVagaID()+","+null+","+null+","+null+'\n';
                }else {
                     info = v.getVagaID() + "," + v.getVeiculo().getPlaca() + "," + v.getTipoVeiculo() +","+v.getData()+'\n';
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

                    info = v.getVagaID()+","+null+","+null+","+null+'\n';
                }else {
                    info = v.getVagaID() + "," + v.getVeiculo().getPlaca() + "," + v.getTipoVeiculo() +","+v.getData()+'\n';
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

                    info = v.getVagaID()+","+null+","+null+","+null+'\n';
                }else {
                    info = v.getVagaID() + "," + v.getVeiculo().getPlaca() + "," + v.getTipoVeiculo() +","+v.getData()+'\n';
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

                    info = v.getVagaID()+","+null+","+null+","+null+'\n';
                }else {
                    info = v.getVagaID() + "," + v.getVeiculo().getPlaca() + "," + v.getTipoVeiculo() +","+v.getData()+'\n';
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

    String contabile(String range) {

        String[] datas = range.split(";");
        String inicio = datas[0];
        String fim = datas[1];

        double lucro=0;
        int saidas=0;

        //todo analizar todas as transaçoes baseado na data passada
        for (Transacao t : historico) {
            lucro += t.getValor();
            if (t.getTipoVeiculo() == -1) {
                saidas++;
            }
        }


        return lucro +","+ saidas;


    }

    int sizePiso1(){return estacionamento.sizeP1();}

    int sizeTerreoCarros(){return estacionamento.sizeTCarros();}

    int sizeTerreoCaminhonetes(){return estacionamento.sizeTCaminhonete();}

    int sizeTerreoMotos(){return estacionamento.sizeTMoto();}

    double getPrecoCaminhonete() {
        return precoCaminhonete;
    }

    void setPrecoCaminhonete(double precoCaminhonete) throws WriteFileException {
        this.precoCaminhonete = precoCaminhonete;
        updateVagasFile();
    }

    double getPrecoCarro() {
        return precoCarro;
    }

    void setPrecoCarro(double precoCarro) throws WriteFileException {

        this.precoCarro = precoCarro;
        updateVagasFile();

    }

    double getPrecoMoto() {
        return precoMoto;
    }

    void setPrecoMoto(double precoMoto) throws WriteFileException {

        this.precoMoto = precoMoto;
        updateVagasFile();
    }
}
