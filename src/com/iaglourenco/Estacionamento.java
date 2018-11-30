package com.iaglourenco;



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

    private ArrayList<Vaga> piso1 = new ArrayList<>();
    private ArrayList<Vaga> terreoCarro = new ArrayList<>();
    private ArrayList<Vaga> terreoMoto = new ArrayList<>();
    private ArrayList<Vaga> terreoCaminhonete = new ArrayList<>();

    private static Estacionamento instance;

    private Estacionamento(){


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



    }

    String entra(Vaga vaga){

        switch (vaga.getTipoVeiculo()){

            case Automovel.CAMINHONETE:
                if(qtdCaminhonetes < MAX_CAMINHONETES) {
                    for(int i =0 ; i< terreoCaminhonete.size();i++){

                        if(terreoCaminhonete.get(i).getVeiculo() == null){
                            String id = terreoCaminhonete.get(i).getVagaID();
                            vaga.setVagaID(id);
                            terreoCaminhonete.set(i,vaga);
                            qtdCaminhonetes++;
                            break;
                        }else if(terreoCaminhonete.get(i).getVeiculo().getPlaca().equals(vaga.getVeiculo().getPlaca())){
                            return null;
                        }
                    }
                    return vaga.getVagaID();
                }
                break;
            case Automovel.CARRO:
                if(qtdPiso1<MAX_CARROS_PISO_1) {
                    for(int i =0 ;i<piso1.size();i++){

                        if(piso1.get(i).getVeiculo() == null){
                            String id = piso1.get(i).getVagaID();
                            vaga.setVagaID(id);
                            piso1.set(i,vaga);
                            qtdPiso1++;
                            break;
                        }else if(piso1.get(i).getVeiculo().getPlaca().equals(vaga.getVeiculo().getPlaca())){
                            return null;
                        }
                    }
                    return vaga.getVagaID();
                } else if(qtdTerreoCarros< MAX_CARROS_TERREO) {
                    for(int i =0 ;i<terreoCarro.size();i++){

                        if(terreoCarro.get(i).getVeiculo() == null){
                            String id = terreoCarro.get(i).getVagaID();
                            vaga.setVagaID(id);
                            terreoCarro.set(i,vaga);
                            qtdTerreoCarros++;
                            break;
                        }else if(terreoCarro.get(i).getVeiculo().getPlaca().equals(vaga.getVeiculo().getPlaca())){
                            return null;
                        }
                    }
                    return vaga.getVagaID();
                }
                break;
            case Automovel.MOTO:
                if(qtdMotos< MAX_MOTOS) {
                    for(int i =0 ;i<terreoMoto.size();i++){

                        if(terreoMoto.get(i).getVeiculo() == null){
                            String id = terreoMoto.get(i).getVagaID();
                            vaga.setVagaID(id);
                            terreoMoto.set(i,vaga);
                            qtdMotos++;
                            break;
                        }else if(terreoMoto.get(i).getVeiculo().getPlaca().equals(vaga.getVeiculo().getPlaca())){
                            return null;
                        }
                    }
                    return vaga.getVagaID();
                }
                break;
        }

        return null;
    }

    String sai(Automovel automovel){

        switch (automovel.getTipo()){

            case Automovel.CAMINHONETE:
                for(int i=0;i<terreoCaminhonete.size();i++){
                    if(terreoCaminhonete.get(i).getVeiculo().getPlaca().equals(automovel.getPlaca())) {
                        String id = terreoCaminhonete.get(i).getVagaID();
                        terreoCaminhonete.set(i,new Vaga(id));
                        qtdCaminhonetes--;
                        return id;
                    }
                }
                break;
            case Automovel.CARRO:
                for(int i=0;i<piso1.size();i++){
                    if(piso1.get(i).getVeiculo().getPlaca().equals(automovel.getPlaca())) {
                        String id = piso1.get(i).getVagaID();
                        piso1.set(i,new Vaga(id));
                        qtdPiso1--;
                        return id;
                    }
                }
                for(int i=0;i<terreoCarro.size();i++){
                    if(terreoCarro.get(i).getVeiculo().getPlaca().equals(automovel.getPlaca())) {
                        String id = terreoCarro.get(i).getVagaID();
                        terreoCarro.set(i,new Vaga(id));
                        qtdTerreoCarros--;
                        return id;
                    }
                }
                break;
            case Automovel.MOTO:
                for(int i=0;i<terreoMoto.size();i++){
                    if(terreoMoto.get(i).getVeiculo().getPlaca().equals(automovel.getPlaca())){
                        String id = terreoMoto.get(i).getVagaID();
                        terreoMoto.set(i,new Vaga(id));
                        qtdMotos--;
                        return id;
                    }
                }
                break;
        }



        return null;
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


}
