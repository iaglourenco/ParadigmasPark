package com.iaglourenco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class Interface  {

    private static Interface instance;


    private JFrame saidaVeiculos = new JFrame();
    private JFrame entradaVeiculos = new JFrame();
    private JFrame setupEstacionamento = new JFrame();
    private JFrame contabilidade = new JFrame();
    private JFrame status = new JFrame();
    private Dimension frameDimension = new Dimension(800,600);
    private Dimension popupDimension = new Dimension(300,400);

    private JButton buttonEntrada=new JButton("Registrar entrada");//registrar entrada
    private JButton buttonSaida=new JButton("Registrar saida");//registrar saida
    private JButton buttonContabilidade = new JButton("Contabilidade");
    private JButton buttonExit=new JButton("Sair");//sair do programa
    private JPanel panel1Status = new JPanel(new FlowLayout());

    private JLabel labelPPH = new JLabel("Preço por hora ");
    private JLabel labelPrecoCaminhonete = new JLabel("Caminhonetes");
    private JLabel labelPrecoCarro = new JLabel("Carros");
    private JLabel labelPrecoMotocicleta= new JLabel("Motos");
    private JPanel panel1Setup = new JPanel(new GridLayout(10,10));
    private JTextField precoCaminhonete = new JTextField();
    private JTextField precoCarro = new JTextField();
    private JTextField precoMotocicleta = new JTextField();
    private JButton buttonOKSetup = new JButton("OK");
    private JButton buttonBackSetup = new JButton("Voltar");



    private  Interface(){

        initialize();
        initSetup();

        addAllHandlers();
    }

    private void addAllHandlers(){

        status.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        status.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(JOptionPane.showConfirmDialog
                        (null,"Tem certeza?",
                                "Sair",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }

            }
        });
    }

    private void initialize(){
        //inicia o layout de status
        status.setLayout(new BorderLayout(10,10));
        status.setSize(frameDimension);
        status.setResizable(false);
        status.setLocationRelativeTo(null);
        status.setTitle("Paradigmas Park");

        panel1Status.add(buttonEntrada);
        panel1Status.add(buttonSaida);
        panel1Status.add(buttonContabilidade);
        panel1Status.add(buttonExit);
        status.add(panel1Status,BorderLayout.SOUTH);
        //TODO inicializar a visualizacao de vagas disponiveis


        status.setVisible(true);
    }

    private void initSetup(){
        setupEstacionamento.setLayout(new BorderLayout());
        setupEstacionamento.setSize(popupDimension);
        setupEstacionamento.setResizable(false);
        setupEstacionamento.setLocationRelativeTo(null);
        setupEstacionamento.setTitle("Configuraçao de preços");

        panel1Setup.add(labelPrecoCaminhonete);
        panel1Setup.add(precoCaminhonete);

        panel1Setup.add(labelPrecoCarro);
        panel1Setup.add(precoCarro);

        panel1Setup.add(labelPrecoMotocicleta);
        panel1Setup.add(precoMotocicleta);

        setupEstacionamento.add(panel1Setup,BorderLayout.CENTER);

        setupEstacionamento.setVisible(true);

    }
    private void initSaida(){

    }
    private void initEntrada(){

    }
    private void initContabilidade(){

    }


    //singleton
    synchronized static Interface getInstance(){

        if(instance == null){
            instance = new Interface();
        }
        return instance;
    }
}
