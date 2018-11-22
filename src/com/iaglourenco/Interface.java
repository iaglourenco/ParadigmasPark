package com.iaglourenco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;


class Interface  {

    private static Interface instance;


    private JFrame saidaVeiculos = new JFrame();
    private JFrame entradaVeiculos = new JFrame();
    private JFrame setupEstacionamento = new JFrame();
    private JFrame contabilidade = new JFrame();
    private JFrame status = new JFrame();
    private JFrame pagamento = new JFrame();
    private Dimension frameDimension = new Dimension(800,600);
    private Dimension popupDimension = new Dimension(300,260);

    private JButton buttonEntrada=new JButton("Registrar entrada");//registrar entrada
    private JButton buttonSaida=new JButton("Registrar saida");//registrar saida
    private JButton buttonContabilidade = new JButton("Contabilidade");
    private JButton buttonExit=new JButton("Sair");//sair do programa
    private JPanel panel1Status = new JPanel(new FlowLayout());


    private JPanel panel1Setup = new JPanel(new GridLayout(4,0,10,10));//TODO encontrar um layout melhor
    private JTextField precoCaminhonete = new JTextField();
    private JTextField precoCarro = new JTextField();
    private JTextField precoMotocicleta = new JTextField();
    private JButton buttonOKSetup = new JButton("OK");
    private JButton buttonClearSetup = new JButton("Limpar");

    private JComboBox<String> categoria; //Para painel Entrada e Saida
    private final String[] names = {"Carro","Caminhonete","Motocicleta"};

    private JPanel panelSaida = new JPanel(new GridLayout(7,0,10,10));
    private JTextField placaSaida = new JTextField();
    private JTextField horaSaida = new JTextField();
    private JButton buttonOKSaida = new JButton("OK");
    private JButton buttonBackSaida = new JButton("Voltar");


    private JPanel panelEntrada = new JPanel(new GridLayout(7,0,10,10));
    private JTextField placaEntrada = new JTextField();
    private JTextField horaEntrada = new JTextField();
    private JButton buttonOKEntrada = new JButton("OK");
    private JButton buttonBackEntrada = new JButton("Voltar");


    private JPanel panelContabilidade = new JPanel(new FlowLayout());
    private JTextArea contabArea = new JTextArea();
    private JButton buttonOKContabilidade = new JButton("OK");

    private JPanel panelPagamento = new JPanel();
    private JTextArea infoPlaca = new JTextArea();
    private JTextArea infoPreco = new JTextArea();
    private JTextArea infoTipo = new JTextArea();
    private JButton buttonOKPagamento = new JButton("OK");



    private  Interface(){
        initialize();
        initSetup();
        initEntrada();
        initSaida();
        initPagamento();
        initContabilidade();
        addAllHandlers();
    }

    private void addAllHandlers(){

        buttonEntrada.addActionListener(new StatusHandler());
        buttonSaida.addActionListener(new StatusHandler());
        buttonContabilidade.addActionListener(new StatusHandler());
        buttonExit.addActionListener(new StatusHandler());
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

        buttonOKSetup.addActionListener(new SetupHandler());
        buttonClearSetup.addActionListener(new SetupHandler());
        setupEstacionamento.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setupEstacionamento.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(precoCaminhonete.getText().isEmpty() || precoCarro.getText().isEmpty() || precoMotocicleta.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Preencha todos os campos!","ERRO",JOptionPane.ERROR_MESSAGE);
                }else{

                    JOptionPane.showMessageDialog(null,"Os preços digitados foram salvos!","INFO",JOptionPane.INFORMATION_MESSAGE);
                    //TODO salvar os preços
                    setupEstacionamento.setVisible(false);
                }
            }
        });
        setupEstacionamento.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                setupEstacionamento.requestFocus();
            }
        });

        buttonOKEntrada.addActionListener(new EntradaHandler());
        buttonBackEntrada.addActionListener(new EntradaHandler());
        buttonOKSaida.addActionListener(new SaidaHandler());
        buttonBackSaida.addActionListener(new SaidaHandler());
        categoria.addItemListener(e -> {
            if(e.getItem().toString().equals(names[0])){
                //Carro

            }else if(e.getItem().toString().equals(names[1])){
                //Caminhonete

            }else{
                //Moto

            }
        });
        buttonOKContabilidade.addActionListener(new ContabileHandler());

    }

    private void initialize(){
        //LAYOUT PRINCIPAL
        status.setLayout(new BorderLayout());
        status.setSize(frameDimension);
        status.setResizable(false);
        status.setLocationRelativeTo(null);
        status.setTitle("Paradigmas-B [Controle de Estacionamento]");

        panel1Status.add(buttonEntrada);
        panel1Status.add(buttonSaida);
        panel1Status.add(buttonContabilidade);
        panel1Status.add(buttonExit);
        //TODO inicializar a visualizacao de vagas disponiveis


        status.add(panel1Status,BorderLayout.SOUTH);
        status.setVisible(true);
    }



    private void initSetup(){
        //LAYOUT DE CONFIGURAR PREÇOS
        setupEstacionamento.setLayout(new BorderLayout());
        setupEstacionamento.setSize(popupDimension);
        setupEstacionamento.setResizable(false);
        setupEstacionamento.setLocationRelativeTo(null);
        setupEstacionamento.setTitle("Configurar Preços");

        panel1Setup.add(new JLabel("Carros / R$:"));
        panel1Setup.add(precoCarro);

        panel1Setup.add(new JLabel("Caminhonetes / R$:"));
        panel1Setup.add(precoCaminhonete);

        panel1Setup.add(new JLabel("Motocicletas / R$:"));
        panel1Setup.add(precoMotocicleta);

        panel1Setup.add(buttonOKSetup);
        panel1Setup.add(buttonClearSetup);

        setupEstacionamento.add(panel1Setup,BorderLayout.CENTER);

        setupEstacionamento.setVisible(true);

    }

    private void initSaida(){
        //LAYOUT REGISTRO DE SAIDA
        saidaVeiculos.setLayout(new BorderLayout());
        saidaVeiculos.setSize(popupDimension);
        saidaVeiculos.setResizable(false);
        saidaVeiculos.setLocationRelativeTo(null);
        saidaVeiculos.setTitle("Registrar saida");

        panelSaida.add(new JLabel("Placa do Veiculo"));
        panelSaida.add(placaSaida);
        panelSaida.add(new JLabel("Horario de Saida"));
        panelSaida.add(horaSaida);
        categoria = new JComboBox<>(names);
        categoria.setMaximumRowCount(3);
        panelSaida.add(categoria);
        panelSaida.add(buttonOKSaida);
        panelSaida.add(buttonBackSaida);

        saidaVeiculos.add(panelSaida);

    }

    private void initEntrada(){
        //LAYOUT REGISTRO DE ENTRADA
        entradaVeiculos.setLayout(new BorderLayout());
        entradaVeiculos.setSize(popupDimension);
        entradaVeiculos.setResizable(false);
        entradaVeiculos.setLocationRelativeTo(null);
        entradaVeiculos.setTitle("Registrar entrada");

        panelEntrada.add(new JLabel("Placa do Veiculo"));
        panelEntrada.add(placaEntrada);
        panelEntrada.add(new JLabel("Horario de Entrada"));
        panelEntrada.add(horaEntrada);
        categoria = new JComboBox<>(names);
        categoria.setMaximumRowCount(3);
        panelEntrada.add(categoria);
        panelEntrada.add(buttonOKEntrada);
        panelEntrada.add(buttonBackEntrada);

        entradaVeiculos.add(panelEntrada);

    }

    private void initContabilidade(){
        //LAYOUT CONTROLE DA CONTABILIDADE
        contabilidade.setLayout(new BorderLayout());
        contabilidade.setSize(frameDimension);
        contabilidade.setResizable(false);
        contabilidade.setLocationRelativeTo(null);
        contabilidade.setTitle("Contabilidade");
        contabArea.setEditable(false);
        contabArea.append("String de teste");


        panelContabilidade.add(buttonOKContabilidade);
        contabilidade.add(contabArea);
        contabilidade.add(panelContabilidade,BorderLayout.SOUTH);

    }

    private void initPagamento(){

        pagamento.setLayout(new BorderLayout());
        pagamento.setSize(popupDimension);
        pagamento.setResizable(false);
        pagamento.setLocationRelativeTo(null);
        pagamento.setTitle("Pagamento");
        infoPlaca.setEditable(false);
        infoTipo.setEditable(false);
        infoPreco.setEditable(false);
        infoPlaca.setText("JIW-1698");
        infoPreco.setText("2000");
        infoTipo.setText("Carro");
        //TODO pegar do arquivo e calcular o valor a pagar
        panelPagamento.setLayout(new GridLayout(7,0,10,10));
        panelPagamento.add(new JLabel("Placa"));
        panelPagamento.add(infoPlaca);
        panelPagamento.add(new JLabel("Tipo"));
        panelPagamento.add(infoTipo);
        panelPagamento.add(new JLabel("Valor"));
        panelPagamento.add(infoPreco);
        panelPagamento.add(buttonOKPagamento);



        pagamento.add(panelPagamento,BorderLayout.CENTER);

    }


    //singleton
    synchronized static Interface getInstance(){

        if(instance == null){
            instance = new Interface();
        }
        return instance;
    }



    private class StatusHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {



            if(e.getSource() == buttonEntrada){
                horaEntrada.setText(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));
                entradaVeiculos.setVisible(true);
            }else if(e.getSource() == buttonSaida){
                horaSaida.setText(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));
                saidaVeiculos.setVisible(true);
            }else if(e.getSource() == buttonContabilidade){
                contabilidade.setVisible(true);
            }else if(e.getSource() == buttonExit){
                if(JOptionPane.showConfirmDialog
                        (null,"Tem certeza?",
                                "Sair",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    System.exit(0);

                }

            }

        }
    }

    private class SetupHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == buttonOKSetup){
                //TODO salvar os preços
                JOptionPane.showMessageDialog(null,"Os preços digitados foram salvos!","INFO",JOptionPane.INFORMATION_MESSAGE);
                setupEstacionamento.setVisible(false);

            } else if (e.getSource() == buttonClearSetup) {
                //limpa os campos
                precoCaminhonete.setText("");
                precoCarro.setText("");
                precoMotocicleta.setText("");
            }

        }
    }

    private class EntradaHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == buttonOKEntrada){
                //TODO perform entrada
            }else if(e.getSource() == buttonBackEntrada){
                entradaVeiculos.setVisible(false);
            }

        }
    }

    private class SaidaHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == buttonOKSaida){
                //TODO perform saida
                pagamento.setVisible(true);
            }else if(e.getSource() == buttonBackSaida){
                saidaVeiculos.setVisible(false);
            }
        }
    }

    private class ContabileHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == buttonOKContabilidade){
                contabilidade.setVisible(false);
            }
        }
    }
}