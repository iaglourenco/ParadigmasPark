package com.iaglourenco;



import com.iaglourenco.exceptions.EstacionamentoCheioException;


import java.util.ArrayList;

class Estacionamento {

    private final int MAX_CARROS_PISO_1 = 100;
    private final int MAX_CARROS_TERREO = 60;
    private final int MAX_MOTOS = 20;
    private final int MAX_CAMINHONETES = 20;

    private int qtdPiso1=0;
    private int qtdTerreoCarros=0;
    private int qtdMotos=0;
    private int qtdCaminhonetes=0;

    private final ArrayList<Vaga> piso1 = new ArrayList<>();
    private final ArrayList<Vaga> terreoCarro = new ArrayList<>();
    private final ArrayList<Vaga> terreoMoto = new ArrayList<>();
    private final ArrayList<Vaga> terreoCaminhonete = new ArrayList<>();

    private static Estacionamento instance;

    private Estacionamento(){
        populateEmpty();
    }


    private boolean placaCadastrada(String placa){


        for (Vaga vaga : terreoCarro) {
            if(vaga.getVeiculo() != null)
                if (vaga.getVeiculo().getPlaca().equalsIgnoreCase(placa)) {
                    return true;
                }
        }

        for (Vaga vaga : piso1) {
            if(vaga.getVeiculo() != null)
                if (vaga.getVeiculo().getPlaca().equalsIgnoreCase(placa)) {
                return true;
            }
        }

        for (Vaga vaga : terreoMoto) {
            if(vaga.getVeiculo() != null)
                if (vaga.getVeiculo().getPlaca().equalsIgnoreCase(placa)) {
                return true;
            }
        }

        for (Vaga vaga : terreoCaminhonete) {
            if(vaga.getVeiculo() != null)
                if (vaga.getVeiculo().getPlaca().equalsIgnoreCase(placa)) {
                return true;
            }
        }

        return false;

    }

    void populateEmpty(){

        piso1.clear();
        terreoMoto.clear();
        terreoCaminhonete.clear();
        terreoCarro.clear();

        for(int i = 1 ; i <= MAX_CARROS_PISO_1;i++){
            piso1.add(new Vaga(Integer.toString(i)));
        }
        for(int i = MAX_CARROS_PISO_1+1 ; i <= MAX_CARROS_TERREO+MAX_CARROS_PISO_1;i++){
            terreoCarro.add(new Vaga(Integer.toString(i)));
        }
        for(int i =  MAX_CARROS_TERREO+MAX_CARROS_PISO_1+1 ; i <= MAX_MOTOS + MAX_CARROS_TERREO+MAX_CARROS_PISO_1;i++){
            terreoMoto.add(new Vaga(Integer.toString(i)));
        }

        for(int i =  MAX_MOTOS + MAX_CARROS_TERREO+MAX_CARROS_PISO_1  +1; i <= MAX_MOTOS + MAX_CARROS_TERREO+MAX_CARROS_PISO_1+MAX_CAMINHONETES;i++){
            terreoCaminhonete.add(new Vaga(Integer.toString(i)));
        }

        qtdPiso1=0;
        qtdTerreoCarros=0;
        qtdMotos=0;
        qtdCaminhonetes=0;

    }

    String entra(Vaga vaga) throws EstacionamentoCheioException {

        switch (vaga.getTipoVeiculo()){

            case Automovel.CAMINHONETE:
                if(qtdCaminhonetes < MAX_CAMINHONETES) {
                    for(int i =0 ; i< terreoCaminhonete.size();i++){

                        if(placaCadastrada(vaga.getVeiculo().getPlaca())){
                            return null;
                        }
                        if(terreoCaminhonete.get(i).getVeiculo() == null){
                            String id = terreoCaminhonete.get(i).getVagaID();
                            vaga.setVagaID(id);
                            terreoCaminhonete.set(i,vaga);
                            qtdCaminhonetes++;
                            break;
                        }
                    }
                    return vaga.getVagaID();
                }else{
                    throw new EstacionamentoCheioException();
                }
            case Automovel.CARRO:
                if(qtdPiso1<MAX_CARROS_PISO_1) {
                    for(int i =0 ;i<piso1.size();i++){

                        if(placaCadastrada(vaga.getVeiculo().getPlaca())){
                            return null;
                        }
                        if(piso1.get(i).getVeiculo() == null){
                            String id = piso1.get(i).getVagaID();
                            vaga.setVagaID(id);
                            piso1.set(i,vaga);
                            qtdPiso1++;
                            break;
                        }
                    }
                    return vaga.getVagaID();
                } else if(qtdTerreoCarros< MAX_CARROS_TERREO) {
                    for(int i =0 ;i<terreoCarro.size();i++){

                        if(placaCadastrada(vaga.getVeiculo().getPlaca())){
                            return null;
                        }
                        if(terreoCarro.get(i).getVeiculo() == null){
                            String id = terreoCarro.get(i).getVagaID();
                            vaga.setVagaID(id);
                            terreoCarro.set(i,vaga);
                            qtdTerreoCarros++;
                            break;
                        }
                    }
                    return vaga.getVagaID();
                }
                else{
                    throw new EstacionamentoCheioException();
                }
            case Automovel.MOTO:
                if(qtdMotos< MAX_MOTOS) {
                    for(int i =0 ;i<terreoMoto.size();i++){

                        if(placaCadastrada(vaga.getVeiculo().getPlaca())){
                            return null;
                        }
                        if(terreoMoto.get(i).getVeiculo() == null){
                            String id = terreoMoto.get(i).getVagaID();
                            vaga.setVagaID(id);
                            terreoMoto.set(i,vaga);
                            qtdMotos++;
                            break;
                        }
                    }
                    return vaga.getVagaID();
                }
                else{
                    throw new EstacionamentoCheioException();
                }
        }

        return null;
    }

    String sai(Automovel automovel){

        for (int i = 0; i < terreoCaminhonete.size(); i++) {
            if (terreoCaminhonete.get(i).getVeiculo() != null && terreoCaminhonete.get(i).getVeiculo().getPlaca().equals(automovel.getPlaca())) {
                String id = terreoCaminhonete.get(i).getVagaID();
                terreoCaminhonete.set(i, new Vaga(id));
                qtdCaminhonetes--;
                return id;
            }
        }

        for (int i = 0; i < piso1.size(); i++) {
            if (piso1.get(i).getVeiculo() != null && piso1.get(i).getVeiculo().getPlaca().equals(automovel.getPlaca())) {
                String id = piso1.get(i).getVagaID();
                piso1.set(i, new Vaga(id));
                qtdPiso1--;
                return id;
            }
        }
        for (int i = 0; i < terreoCarro.size(); i++) {
            if (terreoCarro.get(i).getVeiculo()!= null && terreoCarro.get(i).getVeiculo().getPlaca().equals(automovel.getPlaca())) {
                String id = terreoCarro.get(i).getVagaID();
                terreoCarro.set(i, new Vaga(id));
                qtdTerreoCarros--;
                return id;
            }
        }

        for (int i = 0; i < terreoMoto.size(); i++) {
            if (terreoMoto.get(i).getVeiculo()!= null && terreoMoto.get(i).getVeiculo().getPlaca().equals(automovel.getPlaca())) {
                String id = terreoMoto.get(i).getVagaID();
                terreoMoto.set(i, new Vaga(id));
                qtdMotos--;
                return id;
            }
        }


        return null;
    }

    void put(String id,Vaga v){

        v.setVagaID(id);

        switch (v.getTipoVeiculo()){

            case Automovel.CARRO:
                for( int i =0; i< piso1.size();i++){
                    if(piso1.get(i).getVagaID().equals(id)){
                        piso1.set(i,v);
                        if(Integer.parseInt(id)>=1 && Integer.parseInt(id) <=100){
                            qtdPiso1++;
                        }else {
                            qtdTerreoCarros++;
                        }
                        return;
                    }
                }
                break;
            case Automovel.CAMINHONETE:
                for( int i =0; i< terreoCaminhonete.size();i++){
                    if(terreoCaminhonete.get(i).getVagaID().equals(id)){
                        terreoCaminhonete.set(i,v);
                        qtdCaminhonetes++;
                        return;
                    }
                }
                break;
            case Automovel.MOTO:
                for( int i =0; i< terreoMoto.size();i++){
                    if(terreoMoto.get(i).getVagaID().equals(id)){
                        terreoMoto.set(i,v);
                        qtdMotos++;
                        return;
                    }
                }
                break;

        }

    }

    static synchronized Estacionamento getInstance(){
        if(instance == null){
            instance = new Estacionamento();
        }
        return instance;
    }

    ArrayList<Vaga> getPiso1(){return piso1;}
    ArrayList<Vaga> getTerreoCarro(){return terreoCarro;}
    ArrayList<Vaga> getTerreoMoto(){return terreoMoto;}
    ArrayList<Vaga> getTerreoCaminhonete(){return terreoCaminhonete;}

    int sizeP1(){return qtdPiso1;}
    int sizeTCarros(){return qtdTerreoCarros;}
    int sizeTMoto(){return qtdMotos;}
    int sizeTCaminhonete(){return qtdCaminhonetes;}


}
