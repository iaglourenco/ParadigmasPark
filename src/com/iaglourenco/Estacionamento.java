package com.iaglourenco;


import sun.util.resources.cldr.hy.CalendarData_hy_AM;

import java.awt.*;
import java.util.ArrayList;

class Estacionamento {

    private final int MAX_CARROS_PISO_1 = 100;
    private final int MAX_CARROS_TERREO = 60;
    private final int MAX_MOTOS = 20;
    private final int MAX_CAMINHONETES = 20;

    private ArrayList<Vaga> piso1 = new ArrayList<>();
    private ArrayList<Vaga> terreoCarro = new ArrayList<>();
    private ArrayList<Vaga> terreoMoto = new ArrayList<>();
    private ArrayList<Vaga> terreoCaminhonete = new ArrayList<>();
    private static Estacionamento instance;


    boolean entra(Vaga vaga){
        switch (vaga.getTipo()){

            case Automovel.CAMINHONETE:
                if(terreoCaminhonete.size() < MAX_CAMINHONETES) return terreoCaminhonete.add(vaga);
                break;
            case Automovel.CARRO:
                if(piso1.size()<MAX_CARROS_PISO_1) return piso1.add(vaga);
                else if(terreoCarro.size()< MAX_CARROS_TERREO) return terreoCarro.add(vaga);
                break;
            case Automovel.MOTO:
                if(terreoMoto.size()< MAX_MOTOS) return terreoMoto.add(vaga);
                break;
        }

        return false;
    }
    boolean sai(Automovel automovel){

        switch (automovel.getTipo()){

            case Automovel.CAMINHONETE:
                for(int i=0;i<MAX_CAMINHONETES;i++){
                    if(terreoCaminhonete.get(i).getVeiculo().getPlaca().equals(automovel.getPlaca())) {
                        terreoCaminhonete.set(i,new Vaga());
                        return true;
                    }
                }
                break;
            case Automovel.CARRO:
                for(int i=0;i<MAX_CARROS_PISO_1;i++){
                    if(piso1.get(i).getVeiculo().getPlaca().equals(automovel.getPlaca())) {
                        piso1.set(i,new Vaga());
                        return true;
                    }
                }
                for(int i=0;i<MAX_CARROS_TERREO;i++){
                    if(terreoCarro.get(i).getVeiculo().getPlaca().equals(automovel.getPlaca())) {
                        terreoCarro.set(i,new Vaga());
                        return true;
                    }
                }
                break;
            case Automovel.MOTO:
                for(int i=0;i<MAX_MOTOS;i++){
                    if(terreoMoto.get(i).getVeiculo().getPlaca().equals(automovel.getPlaca())){
                        terreoMoto.set(i,new Vaga());
                        return true;
                    }
                }
                break;
        }



        return false;
    }

    static synchronized Estacionamento getInstance(){
        if(instance == null){
            instance = new Estacionamento();
        }
        return instance;
    }

    public int getSizePiso1() {
        return piso1.size();
    }

    public int getSizeTerreoCarro() {
        return terreoCarro.size();
    }

    public int getSizeMotos() {
        return terreoMoto.size();
    }

    public int getSizeCaminhonetes() {
        return terreoCaminhonete.size();
    }
}
